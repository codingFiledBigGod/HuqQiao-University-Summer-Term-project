package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.SystemPost;
import edu.hqu.lease.web.admin.service.SystemPostService;
import edu.hqu.lease.web.admin.mapper.SystemPostMapper;
import org.springframework.stereotype.Service;

@Service
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost>
    implements SystemPostService{

}




