package edu.hqu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.*;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import edu.hqu.lease.web.app.mapper.*;
import edu.hqu.lease.web.app.service.LeaseAgreementService;
import edu.hqu.lease.web.app.vo.agreement.AgreementDetailVo;
import edu.hqu.lease.web.app.vo.agreement.AgreementItemVo;
import edu.hqu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private PaymentTypeMapper paymentTypeMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Override
    public List<AgreementItemVo> listItemByPhone(String phone) {
            return leaseAgreementMapper.listTtemByPhone(phone);
    }

    @Override
    public AgreementDetailVo getDetailById(Long id) {
        AgreementDetailVo res= new AgreementDetailVo();
        LeaseAgreement leaseAgreement=leaseAgreementMapper.selectById(id);
        //租约基础信息
        BeanUtils.copyProperties(leaseAgreement,res);
        //公寓名称和图片
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(leaseAgreement.getApartmentId());
        res.setApartmentName(apartmentInfo.getName());
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemId,apartmentInfo.getId()).eq(GraphInfo::getItemType, ItemType.APARTMENT);
        List<GraphInfo> graphInfos = graphInfoMapper.selectList(graphQueryWrapper);
        List<GraphVo> graphVoList=new ArrayList<>();
        for (GraphInfo graphInfo : graphInfos) {
            GraphVo graphVo = new GraphVo();
            BeanUtils.copyProperties(graphInfo, graphVo);
            graphVoList.add(graphVo);
        }
        res.setApartmentGraphVoList(graphVoList);
        graphVoList.clear();
        //房间相关信息
        RoomInfo roomInfo = roomInfoMapper.selectById(leaseAgreement.getRoomId());
        res.setRoomNumber(roomInfo.getRoomNumber());
        graphQueryWrapper.clear();
        graphQueryWrapper.eq(GraphInfo::getItemType,roomInfo.getId()).eq(GraphInfo::getItemType,ItemType.ROOM);
        graphInfos = graphInfoMapper.selectList(graphQueryWrapper);
        for (GraphInfo graphInfo : graphInfos) {
            GraphVo graphVo=new GraphVo();
            BeanUtils.copyProperties(graphInfo,graphVo);
            graphVoList.add(graphVo);
        }
        res.setRoomGraphVoList(graphVoList);
        //支付方式
        PaymentType paymentType = paymentTypeMapper.selectById(leaseAgreement.getPaymentTypeId());
        res.setPaymentTypeName(paymentType.getName());

        //租期月数
        LeaseTerm leaseTerm = leaseTermMapper.selectById(leaseAgreement.getLeaseTermId());
        res.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        res.setLeaseTermUnit(leaseTerm.getUnit());
        return res;
    }
}




