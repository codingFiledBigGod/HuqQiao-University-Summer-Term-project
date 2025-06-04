package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.FacilityInfo;
import edu.hqu.lease.web.app.service.FacilityInfoService;
import edu.hqu.lease.web.app.mapper.FacilityInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class FacilityInfoServiceImpl extends ServiceImpl<FacilityInfoMapper, FacilityInfo>
    implements FacilityInfoService{

}




