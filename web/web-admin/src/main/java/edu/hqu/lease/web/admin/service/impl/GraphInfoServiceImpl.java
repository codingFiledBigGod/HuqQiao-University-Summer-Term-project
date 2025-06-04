package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.GraphInfo;
import edu.hqu.lease.web.admin.service.GraphInfoService;
import edu.hqu.lease.web.admin.mapper.GraphInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService{

}




