package edu.hqu.lease.web.admin.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.web.admin.mapper.*;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.web.admin.mapper.*;
import edu.hqu.lease.web.admin.service.LeaseAgreementService;
import edu.hqu.lease.web.admin.vo.agreement.AgreementQueryVo;
import edu.hqu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    LeaseAgreementMapper leaseAgreementMapper;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;


    @Override
    public AgreementVo getAgreementById(Long id) {
        AgreementVo res=new AgreementVo();
        //租期信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        BeanUtils.copyProperties(leaseAgreement,res);
        //签约公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        res.setApartmentInfo(apartmentInfo);
        //签约房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());
        res.setRoomInfo(roomInfo);
        //支付方式
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        res.setPaymentType(paymentType);
        //租期
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        res.setLeaseTerm(leaseTerm);
        return res;
    }

    @Override
    public IPage<AgreementVo> pageItem(Page<AgreementVo> page, AgreementQueryVo queryVo) {

        return leaseAgreementMapper.pageItem(page,queryVo);
    }
}




