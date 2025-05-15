package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.model.entity.PaymentType;
import edu.hqu.lease.model.entity.RoomPaymentType;
import edu.hqu.lease.web.app.mapper.PaymentTypeMapper;
import edu.hqu.lease.web.app.mapper.RoomPaymentTypeMapper;
import edu.hqu.lease.web.app.service.PaymentTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author liubo
* @description 针对表【payment_type(支付方式表)】的数据库操作Service实现
* @createDate 2023-07-26 11:12:39
*/
@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService{

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private RoomPaymentTypeMapper roomPaymentTypeMapper;
    @Override
    public List<PaymentType> getPatmentTypeById(Long id) {
        List<PaymentType> res=new ArrayList<>();
        LambdaQueryWrapper<RoomPaymentType> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(RoomPaymentType::getRoomId, id);
        List<RoomPaymentType> roomPaymentTypes = roomPaymentTypeMapper.selectList(queryWrapper);
        for (RoomPaymentType roomPaymentType : roomPaymentTypes) {
            PaymentType paymentType = paymentTypeMapper.selectById(roomPaymentType.getPaymentTypeId());
            res.add(paymentType);

        }
        return res;
    }
}




