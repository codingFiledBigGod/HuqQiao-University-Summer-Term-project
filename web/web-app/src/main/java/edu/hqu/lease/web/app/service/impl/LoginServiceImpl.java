package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.common.constant.RedisConstant;
import edu.hqu.lease.common.exception.LeaseException;
import edu.hqu.lease.common.result.ResultCodeEnum;
import edu.hqu.lease.common.utils.CodeUtil;
import edu.hqu.lease.common.utils.JwtUtil;
import edu.hqu.lease.model.entity.UserInfo;
import edu.hqu.lease.model.enums.BaseStatus;
import edu.hqu.lease.web.app.mapper.UserInfoMapper;
import edu.hqu.lease.web.app.service.LoginService;
import edu.hqu.lease.web.app.service.SmsService;
import edu.hqu.lease.web.app.vo.user.LoginVo;
import edu.hqu.lease.web.app.vo.user.UserInfoVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SmsService smsService;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void getCode(String phone) {

        String randomCode = CodeUtil.getRandomCode(6);
        String key= RedisConstant.APP_LOGIN_PREFIX+phone;

        Boolean b = stringRedisTemplate.hasKey(key);
        if (b){
            Long ttl = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC -ttl<60){
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }

        smsService.sendCode(phone, randomCode);
        stringRedisTemplate.opsForValue().set(key,randomCode,RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);

    }

    @Override
    public String login(LoginVo loginVo) {
        if (loginVo.getPhone()==null){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        if (loginVo.getCode()==null){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        String key=RedisConstant.APP_LOGIN_PREFIX+loginVo.getPhone();
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s == null){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }
        if (!s.equals(loginVo.getCode())){
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }

        LambdaUpdateWrapper<UserInfo> userInfoLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userInfoLambdaUpdateWrapper.eq(UserInfo::getPhone,loginVo.getPhone());
        UserInfo userInfo = userInfoMapper.selectOne(userInfoLambdaUpdateWrapper);

        if (userInfo==null){
            //注册
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(7));
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfoMapper.insert(userInfo);
        }else {
            //判断是否禁用
            if (userInfo.getStatus()==BaseStatus.DISABLE){
                throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
            }
        }
        return JwtUtil.createToken(userInfo.getId(),userInfo.getPhone());
    }

    @Override
    public UserInfoVo getLoginUserById(Long userID) {
        UserInfo userInfo = userInfoMapper.selectById(userID);
        UserInfoVo userInfoVo=new UserInfoVo(userInfo.getNickname(),userInfo.getAvatarUrl());
        return userInfoVo;
    }
}
