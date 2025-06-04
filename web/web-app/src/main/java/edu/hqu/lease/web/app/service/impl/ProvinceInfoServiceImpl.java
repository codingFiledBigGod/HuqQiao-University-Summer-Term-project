package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.ProvinceInfo;
import edu.hqu.lease.web.app.service.ProvinceInfoService;
import edu.hqu.lease.web.app.mapper.ProvinceInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class ProvinceInfoServiceImpl extends ServiceImpl<ProvinceInfoMapper, ProvinceInfo>
    implements ProvinceInfoService{

}




