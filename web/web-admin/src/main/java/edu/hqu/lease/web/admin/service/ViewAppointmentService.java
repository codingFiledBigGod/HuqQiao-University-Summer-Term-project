package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


public interface ViewAppointmentService extends IService<ViewAppointment> {

    IPage<AppointmentVo> pageItem(Page<AppointmentVo> page, AppointmentQueryVo queryVo);
}
