package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.RoomPaymentType;
import edu.hqu.lease.web.app.service.RoomPaymentTypeService;
import edu.hqu.lease.web.app.mapper.RoomPaymentTypeMapper;
import org.springframework.stereotype.Service;


@Service
public class RoomPaymentTypeServiceImpl extends ServiceImpl<RoomPaymentTypeMapper, RoomPaymentType>
    implements RoomPaymentTypeService{

}




