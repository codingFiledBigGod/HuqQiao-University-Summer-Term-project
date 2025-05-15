package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.common.constant.RedisConstant;
import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.*;
import com.atguigu.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.app.vo.apartment.ApartmentItemVo;
import com.atguigu.lease.web.app.vo.attr.AttrValueVo;
import com.atguigu.lease.web.app.vo.fee.FeeValueVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.atguigu.lease.web.app.vo.room.RoomDetailVo;
import com.atguigu.lease.web.app.vo.room.RoomItemVo;
import com.atguigu.lease.web.app.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;
    @Autowired
    private GraphInfoMapper graphInfoMapper;
    @Autowired
    private AttrValueMapper attrValueMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private PaymentTypeService paymentTypeService;
    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private LeaseTermService leaseTermService;
    @Autowired
    private BrowsingHistoryService browsingHistoryService;

    @Override
    public IPage<RoomItemVo> pageItem(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public IPage<RoomItemVo> pageItemById(Page<RoomItemVo> page, Long id) {
        return roomInfoMapper.pageItemById(page,id);
    }

    @Override
    public RoomDetailVo getDetailByid(Long id) throws ExecutionException, InterruptedException {

        String key= RedisConstant.APP_ROOM_PREFIX+id;
        RoomDetailVo res = (RoomDetailVo) redisTemplate.opsForValue().get(key);

        if (res==null){
            //房间基本信息
            RoomInfo roomInfo=roomInfoMapper.selectById(id);
            BeanUtils.copyProperties(roomInfo,res);
            //所属公寓信息
            ApartmentItemVo apartmentItemVo=new ApartmentItemVo();
            ApartmentInfo apartmentInfo = apartmentInfoService.getById(roomInfo.getApartmentId());
            BeanUtils.copyProperties(apartmentInfo,apartmentItemVo);
            ApartmentDetailVo detailById = apartmentInfoService.getDetailById(apartmentInfo.getId());
            apartmentItemVo.setGraphVoList(detailById.getGraphVoList());
            apartmentItemVo.setLabelInfoList(detailById.getLabelInfoList());
            apartmentItemVo.setMinRent(detailById.getMinRent());
            res.setApartmentItemVo(apartmentItemVo);
            //图片列表
            LambdaQueryWrapper<GraphInfo> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(GraphInfo::getItemId,id).eq(GraphInfo::getItemType, ItemType.ROOM);
            List<GraphInfo> graphInfos = graphInfoMapper.selectList(wrapper);
            List<GraphVo> graphVoList=new ArrayList<>();
            for (GraphInfo graphInfo : graphInfos) {
                GraphVo graphVo=new GraphVo();
                graphVo.setName(graphInfo.getName());
                graphVo.setUrl(graphInfo.getUrl());
                graphVoList.add(graphVo);
            }
            res.setGraphVoList(graphVoList);
            //属性信息列表
            List<AttrValueVo> AttrValueVos=attrValueMapper.listRoomAttrById(id);
            res.setAttrValueVoList(AttrValueVos);
            //配套信息列表
            List<FacilityInfo> facilityInfos=facilityInfoMapper.listFacilityByRoomId(id);
            res.setFacilityInfoList(facilityInfos);
            //标签信息列表
            List<LabelInfo> labelInfos=labelInfoMapper.listLabelByRoomId(id);
            res.setLabelInfoList(labelInfos);
            //支付方式列表
            res.setPaymentTypeList(paymentTypeService.getPatmentTypeById(id));
            //杂费列表
            List<FeeValueVo> feeValueVos=feeValueMapper.listFeeByRoomId(id);
            res.setFeeValueVoList(feeValueVos);
            //租期列表
            List<LeaseTerm> listById = leaseTermService.getListById(id);
            res.setLeaseTermList(listById);
            redisTemplate.opsForValue().set(key,res);
        }

        //保存浏览历史

        return res;
    }
}




