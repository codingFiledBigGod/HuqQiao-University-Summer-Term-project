package edu.hqu.lease.web.admin.service.impl;

import edu.hqu.lease.common.constant.RedisConstant;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.model.enums.ItemType;
import edu.hqu.lease.web.admin.mapper.RoomInfoMapper;
import edu.hqu.lease.web.admin.service.*;
import edu.hqu.lease.web.admin.service.*;
import edu.hqu.lease.web.admin.vo.attr.AttrValueVo;
import edu.hqu.lease.web.admin.vo.graph.GraphVo;
import edu.hqu.lease.web.admin.vo.room.RoomDetailVo;
import edu.hqu.lease.web.admin.vo.room.RoomItemVo;
import edu.hqu.lease.web.admin.vo.room.RoomQueryVo;
import edu.hqu.lease.web.admin.vo.room.RoomSubmitVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private RoomAttrValueService roomAttrValueService;
    @Autowired
    private RoomFacilityService roomFacilityService;
    @Autowired
    private RoomLabelService roomLabelService;
    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;
    @Autowired
    private  RoomLeaseTermService roomLeaseTermService;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private AttrValueService attrValueService;
    @Autowired
    private AttrKeyServiceImpl attrKeyServiceImpl;
    @Autowired
    private AttrKeyService attrKeyService;
    @Autowired
    private FeeValueService feeValueService;
    @Autowired
    private FacilityInfoService facilityInfoService;
    @Autowired
    private LabelInfoService labelInfoService;
    @Autowired
    private PaymentTypeService paymentTypeService;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;



    @Override
    public void updateRoomInfo(RoomSubmitVo roomSubmitVo) {
        boolean isUpdate=roomInfoMapper.selectById(roomSubmitVo.getId())!=null;
        super.saveOrUpdate(roomSubmitVo);
        if(isUpdate){
            //删除原有信息
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM).eq(GraphInfo::getItemId,roomSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);

            LambdaQueryWrapper<RoomAttrValue> attrValueQueryWrapper=new LambdaQueryWrapper<>();
            attrValueQueryWrapper.eq(RoomAttrValue::getRoomId, roomSubmitVo.getId());
            roomAttrValueService.remove(attrValueQueryWrapper);

            LambdaQueryWrapper<RoomFacility> roomFacilityQueryWrapper=new LambdaQueryWrapper<>();
            roomFacilityQueryWrapper.eq(RoomFacility::getRoomId, roomSubmitVo.getId());
            roomFacilityService.remove(roomFacilityQueryWrapper);

            LambdaQueryWrapper<RoomLabel> roomLabelQueryWrapper=new LambdaQueryWrapper<>();
            roomLabelQueryWrapper.eq(RoomLabel::getRoomId, roomSubmitVo.getId());
            roomLabelService.remove(roomLabelQueryWrapper);

            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeQueryWrapper=new LambdaQueryWrapper<>();
            roomPaymentTypeQueryWrapper.eq(RoomPaymentType::getRoomId, roomSubmitVo.getId());
            roomPaymentTypeService.remove(roomPaymentTypeQueryWrapper);

            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermQueryWrapper=new LambdaQueryWrapper<>();
            roomLeaseTermQueryWrapper.eq(RoomLeaseTerm::getRoomId, roomSubmitVo.getId());
            roomLeaseTermService.remove(roomLeaseTermQueryWrapper);
            //删除缓存
            String key= RedisConstant.APP_ROOM_PREFIX+roomSubmitVo.getId();
            redisTemplate.delete(key);

        }
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        List<GraphInfo> graphInfoList = new ArrayList<>();
        for (GraphVo graphVo : graphVoList) {
            GraphInfo graphInfo = new GraphInfo();
            graphInfo.setItemType(ItemType.ROOM);
            graphInfo.setItemId(roomSubmitVo.getId());
            graphInfo.setUrl(graphVo.getUrl());
            graphInfo.setName(graphVo.getName());
            graphInfoList.add(graphInfo);
        }
        graphInfoService.saveBatch(graphInfoList);
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        List<RoomAttrValue> roomAttrValues = new ArrayList<>();
        for (Long attrValueId : attrValueIds) {
            RoomAttrValue roomAttrValue=RoomAttrValue.builder().attrValueId(attrValueId).build();
            roomAttrValue.setRoomId(roomSubmitVo.getId());
            roomAttrValues.add(roomAttrValue);
        }
        roomAttrValueService.saveBatch(roomAttrValues);
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        List<RoomFacility> roomFacilities = new ArrayList<>();
        for (Long facilityInfoId : facilityInfoIds) {
            RoomFacility roomFacility = RoomFacility.builder().facilityId(facilityInfoId).build();
            roomFacility.setRoomId(roomSubmitVo.getId());
            roomFacilities.add(roomFacility);
        }
        roomFacilityService.saveBatch(roomFacilities);
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        List<RoomPaymentType> roomPaymentTypes = new ArrayList<>();
        for (Long paymentTypeId : paymentTypeIds) {
            RoomPaymentType roomPaymentType = RoomPaymentType.builder().paymentTypeId(paymentTypeId).build();
            roomPaymentType.setRoomId(roomSubmitVo.getId());
            roomPaymentTypes.add(roomPaymentType);
        }
        roomPaymentTypeService.saveBatch(roomPaymentTypes);
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        List<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
        for (Long leaseTermId : leaseTermIds) {
            RoomLeaseTerm roomLeaseTerm = RoomLeaseTerm.builder().leaseTermId(leaseTermId).build();
            roomLeaseTerm.setRoomId(roomSubmitVo.getId());
            roomLeaseTerms.add(roomLeaseTerm);
        }
        roomLeaseTermService.saveBatch(roomLeaseTerms);
    }

    @Override
    public IPage<RoomItemVo> selectPage(Page<RoomItemVo> page, RoomQueryVo queryVo) {

        return roomInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id) {
        RoomDetailVo roomDetailVo=new RoomDetailVo();
        //获取房间信息
        RoomInfo roomInfo = roomInfoMapper.selectById(id);
        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        //获取公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoService.getById(roomInfo.getApartmentId());
        roomDetailVo.setApartmentInfo(apartmentInfo);
        //获取图片列表
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper=new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.ROOM);
        List<GraphInfo> list = graphInfoService.list(graphQueryWrapper);
        List<GraphVo> graphVoList = new ArrayList<>();
        for (GraphInfo graphInfo : list) {
            GraphVo graphVo = new GraphVo();
            graphVo.setName(graphInfo.getName());
            graphVo.setUrl(graphInfo.getUrl());
            graphVoList.add(graphVo);
        }
        roomDetailVo.setGraphVoList(graphVoList);
        //获取属性信息列表

        List<RoomAttrValue> list1 = roomAttrValueService.list(new LambdaQueryWrapper<RoomAttrValue>().eq(RoomAttrValue::getRoomId, id));
        List<AttrValueVo> attrValueVoList = new ArrayList<>();
        for (RoomAttrValue roomAttrValue : list1) {
            AttrValueVo attrValueVo = new AttrValueVo();
            long attrValueId=roomAttrValue.getAttrValueId();
            AttrValue attrValue = attrValueService.getById(attrValueId);
            BeanUtils.copyProperties(attrValue,attrValueVo);
            AttrKey attrKey = attrKeyService.getById(attrValue.getAttrKeyId());
            attrValueVo.setAttrKeyName(attrKey.getName());
            attrValueVoList.add(attrValueVo);
        }
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        //获取配套信息列表
        List<RoomFacility> list2 = roomFacilityService.list(new LambdaQueryWrapper<RoomFacility>().eq(RoomFacility::getRoomId, id));
        List<FacilityInfo> facilityInfoList = new ArrayList<>();
        for (RoomFacility roomFacility : list2) {
            FacilityInfo facilityInfo= facilityInfoService.getById(roomFacility.getFacilityId());
            if (facilityInfo.getType() ==ItemType.ROOM) {
                facilityInfoList.add(facilityInfo);
            }
        }
        roomDetailVo.setFacilityInfoList(facilityInfoList);

        //获取标签信息列表
        List<RoomLabel> list3 = roomLabelService.list(new LambdaQueryWrapper<RoomLabel>().eq(RoomLabel::getRoomId, id));
        List<LabelInfo> labelInfoList = new ArrayList<>();
        for (RoomLabel roomLabel : list3) {
            LabelInfo labelInfo = labelInfoService.getById(roomLabel.getLabelId());
            if (labelInfo.getType() ==ItemType.ROOM) {
                labelInfoList.add(labelInfo);
            }
        }
        roomDetailVo.setLabelInfoList(labelInfoList);

        //获取支付方式列表
        List<RoomPaymentType> list4 = roomPaymentTypeService.list(new LambdaQueryWrapper<RoomPaymentType>().eq(RoomPaymentType::getRoomId, id));
        List<PaymentType> paymentTypeList = new ArrayList<>();
        for (RoomPaymentType roomPaymentType : list4) {
            paymentTypeList.add(paymentTypeService.getById(roomPaymentType.getPaymentTypeId()));
        }
        roomDetailVo.setPaymentTypeList(paymentTypeList);

        return roomDetailVo;
    }

    @Override
    public void removeRoomById(Long id) {
        roomInfoMapper.deleteById(id);

        //删除房间属性
        LambdaQueryWrapper<RoomAttrValue> roomAttrValueQueryWrapper = new LambdaQueryWrapper<>();
        roomAttrValueQueryWrapper.eq(RoomAttrValue::getRoomId, id);
        roomAttrValueService.remove(roomAttrValueQueryWrapper);

        //删除房间配套设施
        LambdaQueryWrapper<RoomFacility> roomFacilityQueryWrapper = new LambdaQueryWrapper<>();
        roomFacilityQueryWrapper.eq(RoomFacility::getRoomId, id);
        roomFacilityService.remove(roomFacilityQueryWrapper);

        //删除房间标签
        LambdaQueryWrapper<RoomLabel> roomLabelQueryWrapper = new LambdaQueryWrapper<>();
        roomLabelQueryWrapper.eq(RoomLabel::getRoomId, id);
        roomLabelService.remove(roomLabelQueryWrapper);

        //删除房间支付方式
        LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeQueryWrapper = new LambdaQueryWrapper<>();
        roomPaymentTypeQueryWrapper.eq(RoomPaymentType::getRoomId, id);
        roomPaymentTypeService.remove(roomPaymentTypeQueryWrapper);

        //删除房间租期
        LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermQueryWrapper = new LambdaQueryWrapper<>();
        roomLeaseTermQueryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        roomLeaseTermService.remove(roomLeaseTermQueryWrapper);

        //删除房间图片
        LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
        graphQueryWrapper.eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.ROOM);
        graphInfoService.remove(graphQueryWrapper);
        //删除缓存
        String key=RedisConstant.APP_ROOM_PREFIX+id;
        redisTemplate.delete(key);
    }


}




