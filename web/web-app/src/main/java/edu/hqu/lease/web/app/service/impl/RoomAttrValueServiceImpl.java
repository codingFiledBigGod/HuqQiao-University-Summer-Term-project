package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.RoomAttrValue;
import edu.hqu.lease.web.app.service.RoomAttrValueService;
import edu.hqu.lease.web.app.mapper.RoomAttrValueMapper;
import org.springframework.stereotype.Service;


@Service
public class RoomAttrValueServiceImpl extends ServiceImpl<RoomAttrValueMapper, RoomAttrValue>
    implements RoomAttrValueService{

}




