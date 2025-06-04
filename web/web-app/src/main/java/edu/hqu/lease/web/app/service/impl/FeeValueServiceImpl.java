package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.FeeValue;
import edu.hqu.lease.web.app.service.FeeValueService;
import edu.hqu.lease.web.app.mapper.FeeValueMapper;
import org.springframework.stereotype.Service;


@Service
public class FeeValueServiceImpl extends ServiceImpl<FeeValueMapper, FeeValue>
    implements FeeValueService{

}




