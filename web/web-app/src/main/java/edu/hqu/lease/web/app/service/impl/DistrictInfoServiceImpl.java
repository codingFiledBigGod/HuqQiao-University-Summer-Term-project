package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.DistrictInfo;
import edu.hqu.lease.web.app.service.DistrictInfoService;
import edu.hqu.lease.web.app.mapper.DistrictInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class DistrictInfoServiceImpl extends ServiceImpl<DistrictInfoMapper, DistrictInfo>
    implements DistrictInfoService{

}




