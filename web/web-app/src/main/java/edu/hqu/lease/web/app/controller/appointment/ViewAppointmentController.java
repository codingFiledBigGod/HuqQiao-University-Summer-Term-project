package edu.hqu.lease.web.app.controller.appointment;


import edu.hqu.lease.common.login.LoginUserHolder;
import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.web.app.service.ViewAppointmentService;
import edu.hqu.lease.web.app.vo.appointment.AppointmentDetailVo;
import edu.hqu.lease.web.app.vo.appointment.AppointmentItemVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Tag(name = "看房预约信息")
@RestController
@RequestMapping("/app/appointment")
public class ViewAppointmentController {
    @Autowired
    private ViewAppointmentService viewAppointmentService;
    @Operation(summary = "保存或更新看房预约")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment) {
        viewAppointmentService.saveOrUpdate(viewAppointment);
        return Result.ok();
    }

    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("listItem")
    public Result<List<AppointmentItemVo>> listItem() {
        Long userId = LoginUserHolder.getLoginUser().getUserId();
        List<AppointmentItemVo> res=viewAppointmentService.listItem(userId);
        return Result.ok(res);
    }

    @GetMapping("getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) throws ExecutionException, InterruptedException {
        AppointmentDetailVo res=viewAppointmentService.getDetailById(id);
        return Result.ok(res);
    }

}

