package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface PaymentTypeService extends IService<PaymentType> {
    List<PaymentType> getPatmentTypeById(Long id);
}
