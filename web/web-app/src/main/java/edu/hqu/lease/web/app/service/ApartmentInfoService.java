package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.ApartmentInfo;
import edu.hqu.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;


public interface ApartmentInfoService extends IService<ApartmentInfo> {
    ApartmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException;
}
