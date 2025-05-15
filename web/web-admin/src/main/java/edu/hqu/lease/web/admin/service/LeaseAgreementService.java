package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.model.entity.LeaseAgreement;
import edu.hqu.lease.web.admin.vo.agreement.AgreementQueryVo;
import edu.hqu.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liubo
* @description 针对表【lease_agreement(租约信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface LeaseAgreementService extends IService<LeaseAgreement> {

    AgreementVo getAgreementById(Long id);

    IPage<AgreementVo> pageItem(Page<AgreementVo> page, AgreementQueryVo queryVo);
}
