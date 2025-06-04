package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.ApartmentFeeValue;
import edu.hqu.lease.web.app.service.ApartmentFeeValueService;
import edu.hqu.lease.web.app.mapper.ApartmentFeeValueMapper;
import org.springframework.stereotype.Service;


@Service
public class ApartmentFeeValueServiceImpl extends ServiceImpl<ApartmentFeeValueMapper, ApartmentFeeValue>
    implements ApartmentFeeValueService{
}




