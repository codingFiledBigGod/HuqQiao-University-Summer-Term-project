package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.LeaseTerm;
import edu.hqu.lease.web.admin.service.LeaseTermService;
import edu.hqu.lease.web.admin.mapper.LeaseTermMapper;
import org.springframework.stereotype.Service;


@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
    implements LeaseTermService{

}




