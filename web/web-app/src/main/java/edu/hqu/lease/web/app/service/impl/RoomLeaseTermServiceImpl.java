package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.RoomLeaseTerm;
import edu.hqu.lease.web.app.service.RoomLeaseTermService;
import edu.hqu.lease.web.app.mapper.RoomLeaseTermMapper;
import org.springframework.stereotype.Service;


@Service
public class RoomLeaseTermServiceImpl extends ServiceImpl<RoomLeaseTermMapper, RoomLeaseTerm>
    implements RoomLeaseTermService{

}




