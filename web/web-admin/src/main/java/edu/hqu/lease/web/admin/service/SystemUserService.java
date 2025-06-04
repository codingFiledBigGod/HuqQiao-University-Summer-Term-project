package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.model.entity.SystemUser;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserItemVo;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface SystemUserService extends IService<SystemUser> {


    IPage<SystemUserItemVo> pageItem(Page<SystemUser> page, SystemUserQueryVo queryVo);

    SystemUserItemVo getVoById(Long id);
}
