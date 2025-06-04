package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.LeaseTerm;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface LeaseTermService extends IService<LeaseTerm> {
    List<LeaseTerm> getListById(Long id);
}
