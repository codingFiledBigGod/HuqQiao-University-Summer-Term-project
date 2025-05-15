package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.web.app.vo.appointment.AppointmentDetailVo;
import edu.hqu.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
* @author liubo
* @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service
* @createDate 2023-07-26 11:12:39
*/
public interface ViewAppointmentService extends IService<ViewAppointment> {
    List<AppointmentItemVo> listItem(Long userId);

    AppointmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException;
}
