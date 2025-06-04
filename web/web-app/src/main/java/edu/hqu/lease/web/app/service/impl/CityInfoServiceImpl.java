package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.CityInfo;
import edu.hqu.lease.web.app.service.CityInfoService;
import edu.hqu.lease.web.app.mapper.CityInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
    implements CityInfoService{

}




