package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.model.entity.FeeKey;
import edu.hqu.lease.web.admin.vo.fee.FeeKeyVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface FeeKeyService extends IService<FeeKey> {

    List<FeeKeyVo> feeInfoList();
}
