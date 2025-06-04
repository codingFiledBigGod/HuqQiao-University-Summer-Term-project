package edu.hqu.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.BrowsingHistory;
import edu.hqu.lease.web.admin.service.BrowsingHistoryService;
import edu.hqu.lease.web.admin.mapper.BrowsingHistoryMapper;
import org.springframework.stereotype.Service;


@Service
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
    implements BrowsingHistoryService{

}




