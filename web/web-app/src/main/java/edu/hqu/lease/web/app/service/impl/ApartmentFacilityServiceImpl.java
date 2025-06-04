package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.ApartmentFacility;
import edu.hqu.lease.web.app.service.ApartmentFacilityService;
import edu.hqu.lease.web.app.mapper.ApartmentFacilityMapper;
import org.springframework.stereotype.Service;


@Service
public class ApartmentFacilityServiceImpl extends ServiceImpl<ApartmentFacilityMapper, ApartmentFacility>
    implements ApartmentFacilityService{
}




