package edu.hqu.lease.web.admin.service.impl;

import edu.hqu.lease.model.entity.FeeKey;
import edu.hqu.lease.web.admin.mapper.FeeKeyMapper;
import edu.hqu.lease.web.admin.service.FeeKeyService;
import edu.hqu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FeeKeyServiceImpl extends ServiceImpl<FeeKeyMapper, FeeKey>
    implements FeeKeyService{

    @Autowired
    private FeeKeyMapper feeKeyMapper;
    @Override
    public List<FeeKeyVo> feeInfoList() {

        return feeKeyMapper.feeInfoList();
    }
}




