package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.CityInfo;
import edu.hqu.lease.web.admin.service.CityInfoService;
import edu.hqu.lease.web.admin.mapper.CityInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
    implements CityInfoService{

}




