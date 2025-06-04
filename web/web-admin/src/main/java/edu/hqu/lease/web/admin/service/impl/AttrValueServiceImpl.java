package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.AttrValue;
import edu.hqu.lease.web.admin.service.AttrValueService;
import edu.hqu.lease.web.admin.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;


@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue>
    implements AttrValueService{

}




