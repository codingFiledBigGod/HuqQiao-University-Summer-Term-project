package edu.hqu.lease.web.admin.service.impl;

import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.web.admin.mapper.ViewAppointmentMapper;
import edu.hqu.lease.web.admin.service.ViewAppointmentService;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {
    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;
    @Override
    public IPage<AppointmentVo> pageItem(Page<AppointmentVo> page, AppointmentQueryVo queryVo) {

        return viewAppointmentMapper.pageItem(page,queryVo);
    }
}




