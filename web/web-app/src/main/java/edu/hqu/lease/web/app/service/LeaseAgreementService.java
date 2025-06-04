package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.LeaseAgreement;
import edu.hqu.lease.web.app.vo.agreement.AgreementDetailVo;
import edu.hqu.lease.web.app.vo.agreement.AgreementItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface LeaseAgreementService extends IService<LeaseAgreement> {
    List<AgreementItemVo> listItemByPhone(String phone);

    AgreementDetailVo getDetailById(Long id);
}
