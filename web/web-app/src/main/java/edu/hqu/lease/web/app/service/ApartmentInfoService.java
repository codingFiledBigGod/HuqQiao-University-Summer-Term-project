package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.ApartmentInfo;
import edu.hqu.lease.web.app.vo.apartment.ApartmentDetailVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.concurrent.ExecutionException;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
 * @createDate 2023-07-26 11:12:39
 */
public interface ApartmentInfoService extends IService<ApartmentInfo> {
    ApartmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException;
}
