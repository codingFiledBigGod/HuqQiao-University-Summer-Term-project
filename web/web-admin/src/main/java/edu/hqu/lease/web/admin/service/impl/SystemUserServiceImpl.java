package edu.hqu.lease.web.admin.service.impl;

import edu.hqu.lease.model.entity.SystemPost;
import edu.hqu.lease.model.entity.SystemUser;
import edu.hqu.lease.web.admin.mapper.SystemPostMapper;
import edu.hqu.lease.web.admin.mapper.SystemUserMapper;
import edu.hqu.lease.web.admin.service.SystemUserService;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserItemVo;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
        implements SystemUserService {


    @Autowired
    private SystemUserMapper systemUserMapper;
    @Autowired
    private SystemPostMapper systemPostMapper;

    @Override
    public IPage<SystemUserItemVo> pageItem(Page<SystemUser> page, SystemUserQueryVo queryVo) {
        return systemUserMapper.pageItem(page,queryVo);
    }

    @Override
    public SystemUserItemVo getVoById(Long id) {
        SystemUserItemVo systemUserItemVo=new SystemUserItemVo();
        SystemUser systemUser = systemUserMapper.selectById(id);
        SystemPost systemPost = systemPostMapper.selectById(systemUser.getPostId());
        systemUserItemVo.setPostName(systemPost.getName());
        BeanUtils.copyProperties(systemUser,systemUserItemVo);
        return systemUserItemVo;
    }
}




