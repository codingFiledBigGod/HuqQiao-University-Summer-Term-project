package com.atguigu.lease.web.app.service.impl;

import com.atguigu.lease.model.entity.*;
import com.atguigu.lease.model.enums.ItemType;
import com.atguigu.lease.web.app.mapper.*;
import com.atguigu.lease.web.app.service.ApartmentInfoService;
import com.atguigu.lease.web.app.service.FacilityInfoService;
import com.atguigu.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.atguigu.lease.web.app.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-26 11:12:39
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private LabelInfoMapper labelInfoMapper;
    @Autowired
    private FacilityInfoMapper facilityInfoMapper;
    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private ApartmentLabelMapper apartmentLabelMapper;
    @Autowired
    private ApartmentFacilityMapper apartmentFacilityMapper;
    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Override
    public ApartmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException {
        ApartmentDetailVo res=new ApartmentDetailVo();

        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        BeanUtils.copyProperties(apartmentInfo, res);

        CompletableFuture<List<GraphVo>> getGraphVoList=CompletableFuture.supplyAsync(()->{
            //获取图片列表
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemId, id).eq(GraphInfo::getItemType, ItemType.APARTMENT);
            List<GraphInfo> graphInfos = graphInfoMapper.selectList(graphQueryWrapper);
            List<GraphVo> graphVos = new ArrayList<>();
            for (GraphInfo graphInfo : graphInfos) {
                GraphVo graphVo = new GraphVo();
                graphVo.setName(graphInfo.getName());
                graphVo.setUrl(graphInfo.getUrl());
                graphVos.add(graphVo);
            }
            return graphVos;
        });

        CompletableFuture<List<LabelInfo>> getLabelInfoList=CompletableFuture.supplyAsync(new Supplier<List<LabelInfo>>() {
            @Override
            public List<LabelInfo> get() {
                //获取标签列表
                LambdaQueryWrapper<ApartmentLabel> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(ApartmentLabel::getApartmentId,id);
                List<ApartmentLabel> apartmentLabels = apartmentLabelMapper.selectList(wrapper);
                List<LabelInfo> labelInfos = new ArrayList<>();
                for (ApartmentLabel apartmentLabel : apartmentLabels) {
                    labelInfos.add(labelInfoMapper.selectById(apartmentLabel.getLabelId()));
                }
                return labelInfos;
            }
        });
        CompletableFuture<List<FacilityInfo>> getFacilityInfo=CompletableFuture.supplyAsync(new Supplier<List<FacilityInfo>>() {
            @Override
            public List<FacilityInfo> get() {
                //获取配套列表
                LambdaQueryWrapper<ApartmentFacility> wrapper2 = new LambdaQueryWrapper<>();
                wrapper2.eq(ApartmentFacility::getApartmentId,id);
                List<ApartmentFacility> apartmentFacilities = apartmentFacilityMapper.selectList(wrapper2);
                List<FacilityInfo> facilityInfos = new ArrayList<>();
                for (ApartmentFacility apartmentFacility : apartmentFacilities) {
                    facilityInfos.add(facilityInfoMapper.selectById(apartmentFacility.getFacilityId()));
                }
                return facilityInfos;
            }
        });
        //获取租金最小值
        LambdaQueryWrapper<RoomInfo> wrapper3 = new LambdaQueryWrapper<>();
        wrapper3.eq(RoomInfo::getApartmentId,id);
        List<RoomInfo> roomInfos = roomInfoMapper.selectList(wrapper3);
        roomInfos.sort(new Comparator<RoomInfo>() {
            @Override
            public int compare(RoomInfo o1, RoomInfo o2) {
                return o1.getRent().compareTo(o2.getRent());
            }
        });

        res.setGraphVoList(getGraphVoList.get());
        res.setLabelInfoList(getLabelInfoList.get());
        res.setFacilityInfoList(getFacilityInfo.get());
        res.setMinRent(roomInfos.get(0).getRent());
        return res;
    }
}




