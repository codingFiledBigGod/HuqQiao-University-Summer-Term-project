package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.LabelInfo;
import edu.hqu.lease.web.admin.service.LabelInfoService;
import edu.hqu.lease.web.admin.mapper.LabelInfoMapper;
import org.springframework.stereotype.Service;


@Service
public class LabelInfoServiceImpl extends ServiceImpl<LabelInfoMapper, LabelInfo>
    implements LabelInfoService{

}




