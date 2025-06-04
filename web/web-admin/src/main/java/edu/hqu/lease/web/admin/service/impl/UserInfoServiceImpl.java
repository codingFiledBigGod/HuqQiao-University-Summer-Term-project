package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.UserInfo;
import edu.hqu.lease.web.admin.service.UserInfoService;
import edu.hqu.lease.web.admin.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




