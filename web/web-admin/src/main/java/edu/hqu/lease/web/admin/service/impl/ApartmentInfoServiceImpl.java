package edu.hqu.lease.web.admin.service.impl;

import edu.hqu.lease.common.exception.LeaseException;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.model.entity.*;
import edu.hqu.lease.model.enums.ItemType;
import edu.hqu.lease.web.admin.mapper.ApartmentInfoMapper;
import edu.hqu.lease.web.admin.mapper.RoomInfoMapper;
import edu.hqu.lease.web.admin.service.*;
import edu.hqu.lease.web.admin.service.*;
import edu.hqu.lease.web.admin.vo.apartment.ApartmentDetailVo;
import edu.hqu.lease.web.admin.vo.apartment.ApartmentItemVo;
import edu.hqu.lease.web.admin.vo.apartment.ApartmentQueryVo;
import edu.hqu.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import edu.hqu.lease.web.admin.vo.fee.FeeValueVo;
import edu.hqu.lease.web.admin.vo.graph.GraphVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {
    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;
    @Autowired
    private GraphInfoService graphInfoService;
    @Autowired
    private ApartmentLabelService apartmentLabelService;
    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;
    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private LabelInfoService labelInfoService;
    @Autowired
    private FacilityInfoService facilityInfoService;
    @Autowired
    FeeValueService feeValueService;
    @Autowired
    private FeeKeyService feeKeyService;
    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Override
    public void saveOrUpdateAPT(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate=apartmentSubmitVo.getId()!=null;
        super.saveOrUpdate(apartmentSubmitVo);
        if(isUpdate){
            //删除图片列表
            LambdaQueryWrapper<GraphInfo> graphInfoQueryWrapper=new LambdaQueryWrapper<>();
            graphInfoQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
            graphInfoQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(graphInfoQueryWrapper);
            //删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelQueryWrapper=new LambdaQueryWrapper<>();
            apartmentLabelQueryWrapper.eq(ApartmentLabel::getApartmentId, apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelQueryWrapper);
            //删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFeeValueQueryWrapper.eq(ApartmentFeeValue::getApartmentId, apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueQueryWrapper);
            //删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
            apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId, apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);
        }
        //插入图片
        List<GraphInfo> list1= new ArrayList<GraphInfo>();
        if (!CollectionUtils.isEmpty(apartmentSubmitVo.getGraphVoList())) {
            for (GraphVo graphVo : apartmentSubmitVo.getGraphVoList()) {
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setName(graphVo.getName());
                graphInfo.setUrl(graphVo.getUrl());
                list1.add(graphInfo);
            }
            graphInfoService.saveBatch(list1);
        }
        //插入标签
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if (!CollectionUtils.isEmpty(labelIds)) {
            List<ApartmentLabel> apartmentLabelList = new ArrayList<>();
            for (Long labelId : labelIds) {
                ApartmentLabel apartmentLabel = ApartmentLabel.builder().build();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabelList.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabelList);
        }
        //插入杂费
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if (!CollectionUtils.isEmpty(feeValueIds)) {
            ArrayList<ApartmentFeeValue> apartmentFeeValueList = new ArrayList<>();
            for (Long feeValueId : feeValueIds) {
                ApartmentFeeValue apartmentFeeValue =ApartmentFeeValue.builder().build();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValueList.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValueList);
        }
        //插入配套
        List<Long> facilityInfoIdList = apartmentSubmitVo.getFacilityInfoIds();
        if (!CollectionUtils.isEmpty(facilityInfoIdList)){
            ArrayList<ApartmentFacility> facilityList = new ArrayList<>();
            for (Long facilityId : facilityInfoIdList) {
                ApartmentFacility apartmentFacility = ApartmentFacility.builder().build();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityId);
                facilityList.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(facilityList);
        }

    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> page, ApartmentQueryVo queryVo) {

        return apartmentInfoMapper.pageItem(page,queryVo);
    }

    @Override
    public ApartmentDetailVo getDetailById(Long id) {
        //1.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);
        //2.查询标签列表
        LambdaQueryWrapper<ApartmentLabel> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.eq(ApartmentLabel::getApartmentId,id);
        List<ApartmentLabel> labelList = apartmentLabelService.list(queryWrapper1);
        List<LabelInfo>ans1=new ArrayList<>();
        for (ApartmentLabel apartmentLabel : labelList) {
            ans1.add(labelInfoService.getById(apartmentLabel.getLabelId()));
        }
        //3.查询图片列表
        LambdaQueryWrapper<GraphInfo> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(GraphInfo::getItemType,ItemType.APARTMENT).eq(GraphInfo::getItemId,id);
        List<GraphInfo> grathList = graphInfoService.list(queryWrapper2);
        List<GraphVo> ans2=new ArrayList<>();
        for (GraphInfo graphInfo : grathList) {
            GraphVo graphVo = new GraphVo();
            graphVo.setName(graphInfo.getName());
            graphVo.setUrl(graphInfo.getUrl());
            ans2.add(graphVo);
        }
        //4查询配套列表
        LambdaQueryWrapper<ApartmentFacility> queryWrapper3=new LambdaQueryWrapper<>();
        queryWrapper3.eq(ApartmentFacility::getApartmentId,id);
        List<ApartmentFacility> facilityList = apartmentFacilityService.list(queryWrapper3);
        List<FacilityInfo> ans3=new ArrayList<>();
        for (ApartmentFacility apartmentFacility : facilityList) {
            ans3.add(facilityInfoService.getById(apartmentFacility.getFacilityId()));
        }
        //5查询杂费列表
        LambdaQueryWrapper<ApartmentFeeValue> queryWrapper4=new LambdaQueryWrapper<>();
        queryWrapper4.eq(ApartmentFeeValue::getApartmentId,id);
        List<ApartmentFeeValue> feeValueList = apartmentFeeValueService.list(queryWrapper4);
        List<FeeValueVo> ans4=new ArrayList<>();
        for (ApartmentFeeValue apartmentFeeValue : feeValueList) {
            FeeValueVo feeValueVo = new FeeValueVo();
            FeeValue feeValue = feeValueService.getById(apartmentFeeValue.getFeeValueId());
            String feeKeyName=feeKeyService.getById(feeValue.getFeeKeyId()).getName();
            feeValueVo.setFeeKeyName(feeKeyName);
            feeValueVo.setName(feeValue.getName());
            feeValueVo.setUnit(feeValue.getUnit());
            feeValueVo.setFeeKeyId(feeValue.getFeeKeyId());
            ans4.add(feeValueVo);
        }
        //6.组装结果
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        apartmentDetailVo.setGraphVoList(ans2);
        apartmentDetailVo.setLabelInfoList(ans1);
        apartmentDetailVo.setFacilityInfoList(ans3);
        apartmentDetailVo.setFeeValueVoList(ans4);
        apartmentDetailVo.setId(id);
        apartmentDetailVo.setName(apartmentInfo.getName());
        apartmentDetailVo.setIntroduction(apartmentInfo.getIntroduction());
        apartmentDetailVo.setDistrictId(apartmentInfo.getDistrictId());
        apartmentDetailVo.setDistrictName(apartmentInfo.getDistrictName());
        apartmentDetailVo.setCityId(apartmentInfo.getCityId());
        apartmentDetailVo.setCityName(apartmentInfo.getCityName());
        apartmentDetailVo.setProvinceId(apartmentInfo.getProvinceId());
        apartmentDetailVo.setProvinceName(apartmentInfo.getProvinceName());
        apartmentDetailVo.setAddressDetail(apartmentInfo.getAddressDetail());
        apartmentDetailVo.setLatitude(apartmentInfo.getLatitude());
        apartmentDetailVo.setLongitude(apartmentInfo.getLongitude());
        apartmentDetailVo.setPhone(apartmentInfo.getPhone());
        apartmentDetailVo.setIsRelease(apartmentInfo.getIsRelease());
        return apartmentDetailVo;
    }

    @Override
    public void removeApartmentById(Long id) {
        LambdaQueryWrapper<RoomInfo> roomQueryWrapper = new LambdaQueryWrapper<>();
        roomQueryWrapper.eq(RoomInfo::getApartmentId,id);
        Long l = roomInfoMapper.selectCount(roomQueryWrapper);
        if (l > 0) {
            //终止删除
            throw new LeaseException(310,"请先删除房间");
        }
        super.removeById(id);

        //删除图片列表
        LambdaQueryWrapper<GraphInfo> graphInfoQueryWrapper=new LambdaQueryWrapper<>();
        graphInfoQueryWrapper.eq(GraphInfo::getItemType, ItemType.APARTMENT);
        graphInfoQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphInfoQueryWrapper);
        //删除标签列表
        LambdaQueryWrapper<ApartmentLabel> apartmentLabelQueryWrapper=new LambdaQueryWrapper<>();
        apartmentLabelQueryWrapper.eq(ApartmentLabel::getApartmentId, id);
        apartmentLabelService.remove(apartmentLabelQueryWrapper);
        //删除杂费列表
        LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueQueryWrapper=new LambdaQueryWrapper<>();
        apartmentFeeValueQueryWrapper.eq(ApartmentFeeValue::getApartmentId, id);
        apartmentFeeValueService.remove(apartmentFeeValueQueryWrapper);
        //删除配套列表
        LambdaQueryWrapper<ApartmentFacility> apartmentFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
        apartmentFacilityLambdaQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(apartmentFacilityLambdaQueryWrapper);


    }
}




