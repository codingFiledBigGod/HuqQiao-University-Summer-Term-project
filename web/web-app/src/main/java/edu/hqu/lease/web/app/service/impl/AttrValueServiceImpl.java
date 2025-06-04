package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.AttrValue;
import edu.hqu.lease.web.app.service.AttrValueService;
import edu.hqu.lease.web.app.mapper.AttrValueMapper;
import org.springframework.stereotype.Service;


@Service
public class AttrValueServiceImpl extends ServiceImpl<AttrValueMapper, AttrValue>
    implements AttrValueService{

}




