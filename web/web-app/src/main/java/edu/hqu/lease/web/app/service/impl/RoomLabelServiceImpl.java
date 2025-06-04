package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.RoomLabel;
import edu.hqu.lease.web.app.service.RoomLabelService;
import edu.hqu.lease.web.app.mapper.RoomLabelMapper;
import org.springframework.stereotype.Service;


@Service
public class RoomLabelServiceImpl extends ServiceImpl<RoomLabelMapper, RoomLabel>
    implements RoomLabelService{

}




