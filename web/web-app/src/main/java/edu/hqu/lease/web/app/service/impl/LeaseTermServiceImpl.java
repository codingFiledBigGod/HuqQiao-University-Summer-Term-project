package edu.hqu.lease.web.app.service.impl;

import edu.hqu.lease.model.entity.LeaseTerm;
import edu.hqu.lease.model.entity.RoomLeaseTerm;
import edu.hqu.lease.web.app.mapper.LeaseTermMapper;
import edu.hqu.lease.web.app.mapper.RoomLeaseTermMapper;
import edu.hqu.lease.web.app.service.LeaseTermService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
        implements LeaseTermService {

    @Autowired
    private RoomLeaseTermMapper roomLeaseTermMapper;
    @Autowired
    private LeaseTermMapper leaseTermMapper;
    @Override
    public List<LeaseTerm> getListById(Long id) {
        LambdaQueryWrapper<RoomLeaseTerm> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RoomLeaseTerm::getRoomId, id);
        List<RoomLeaseTerm> roomLeaseTerms=roomLeaseTermMapper.selectList(queryWrapper);
        List<Long>ids=new ArrayList<>();
        for (RoomLeaseTerm roomLeaseTerm : roomLeaseTerms) {
            ids.add(roomLeaseTerm.getLeaseTermId());
        }
        List<LeaseTerm> leaseTerms = leaseTermMapper.selectBatchIds(ids);
        return leaseTerms;
    }
}




