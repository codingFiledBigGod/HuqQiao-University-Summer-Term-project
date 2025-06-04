package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.FacilityInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    List<FacilityInfo> listFacilityByRoomId(Long RoomId);
}




