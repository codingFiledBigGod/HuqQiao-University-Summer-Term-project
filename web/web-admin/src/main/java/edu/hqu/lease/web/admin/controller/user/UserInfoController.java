package edu.hqu.lease.web.admin.controller.user;


import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.model.entity.UserInfo;
import edu.hqu.lease.model.enums.BaseStatus;
import edu.hqu.lease.web.admin.service.UserInfoService;
import edu.hqu.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {
        Page<UserInfo> page = new Page<>(current, size);
        LambdaQueryWrapper<UserInfo> userInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userInfoLambdaQueryWrapper.like(queryVo.getPhone() !=null,UserInfo::getPhone,queryVo.getPhone());
        userInfoLambdaQueryWrapper.eq(queryVo.getStatus() !=null,UserInfo::getStatus,queryVo.getStatus());
        Page<UserInfo> page1 = userInfoService.page(page, userInfoLambdaQueryWrapper);
        return Result.ok(page1);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,id);
        updateWrapper.set(UserInfo::getStatus,status);
        userInfoService.update(updateWrapper);
        return Result.ok();
    }
}
