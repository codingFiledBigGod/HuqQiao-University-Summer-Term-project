package edu.hqu.lease.web.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.hqu.lease.model.entity.ApartmentLabel;
import edu.hqu.lease.web.app.service.ApartmentLabelService;
import edu.hqu.lease.web.app.mapper.ApartmentLabelMapper;
import org.springframework.stereotype.Service;


@Service
public class ApartmentLabelServiceImpl extends ServiceImpl<ApartmentLabelMapper, ApartmentLabel>
    implements ApartmentLabelService{
}
