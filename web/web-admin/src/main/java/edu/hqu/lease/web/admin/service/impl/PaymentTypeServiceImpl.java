package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.PaymentType;
import edu.hqu.lease.web.admin.service.PaymentTypeService;
import edu.hqu.lease.web.admin.mapper.PaymentTypeMapper;
import org.springframework.stereotype.Service;


@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

}




