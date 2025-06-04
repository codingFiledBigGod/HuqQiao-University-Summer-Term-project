package edu.hqu.lease.web.app.service;

import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.web.app.vo.appointment.AppointmentDetailVo;
import edu.hqu.lease.web.app.vo.appointment.AppointmentItemVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface ViewAppointmentService extends IService<ViewAppointment> {
    List<AppointmentItemVo> listItem(Long userId);

    AppointmentDetailVo getDetailById(Long id) throws ExecutionException, InterruptedException;
}
