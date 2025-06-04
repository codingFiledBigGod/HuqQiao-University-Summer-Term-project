package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.FeeKey;
import edu.hqu.lease.web.app.service.FeeKeyService;
import edu.hqu.lease.web.app.mapper.FeeKeyMapper;
import org.springframework.stereotype.Service;


@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

}




