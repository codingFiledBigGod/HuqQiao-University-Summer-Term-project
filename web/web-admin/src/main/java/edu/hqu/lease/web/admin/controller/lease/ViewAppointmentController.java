package edu.hqu.lease.web.admin.controller.lease;


import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.model.entity.ViewAppointment;
import edu.hqu.lease.model.enums.AppointmentStatus;
import edu.hqu.lease.web.admin.service.ViewAppointmentService;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentQueryVo;
import edu.hqu.lease.web.admin.vo.appointment.AppointmentVo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "预约看房管理")
@RequestMapping("/admin/appointment")
@RestController
public class ViewAppointmentController {
    @Autowired
    private ViewAppointmentService viewAppointmentService;
    @Operation(summary = "分页查询预约信息")
    @GetMapping("page")
    public Result<IPage<AppointmentVo>> page(@RequestParam long current, @RequestParam long size, AppointmentQueryVo queryVo) {
        Page<AppointmentVo> page = new Page<>(current, size);
        IPage<AppointmentVo> iPage=viewAppointmentService.pageItem(page,queryVo);
        return Result.ok(iPage);
    }

    @Operation(summary = "根据id更新预约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam AppointmentStatus status) {
        UpdateWrapper<ViewAppointment> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("appointment_status", status);
        viewAppointmentService.update(updateWrapper);
        return Result.ok();
    }

}
