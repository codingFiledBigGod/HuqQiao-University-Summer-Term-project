package edu.hqu.lease.web.app.mapper;

import edu.hqu.lease.model.entity.LeaseAgreement;
import edu.hqu.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    List<AgreementItemVo> listTtemByPhone(String phone);
}




