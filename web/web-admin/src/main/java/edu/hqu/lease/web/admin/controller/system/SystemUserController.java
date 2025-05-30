package edu.hqu.lease.web.admin.controller.system;


import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.model.entity.SystemUser;
import edu.hqu.lease.model.enums.BaseStatus;
import edu.hqu.lease.web.admin.service.SystemUserService;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserItemVo;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {
    @Autowired
    private SystemUserService systemUserService;

    @Operation(summary = "根据条件分页查询后台用户列表")
    @GetMapping("page")
    public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
        Page<SystemUser> page=new Page<>(current,size);
        IPage <SystemUserItemVo> res=systemUserService.pageItem(page,queryVo);
        return Result.ok(res);
    }

    @Operation(summary = "根据ID查询后台用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
       SystemUserItemVo res= systemUserService.getVoById(id);
        return Result.ok(res);
    }

    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemUser systemUser) {
        if (systemUser.getPassword()!=null){
            String s = DigestUtils.md5Hex(systemUser.getPassword());
            systemUser.setPassword(s);
        }
        systemUserService.saveOrUpdate(systemUser);
        return Result.ok();
    }

    @Operation(summary = "判断后台用户名是否可用")
    @GetMapping("isUserNameAvailable")
    public Result<Boolean> isUsernameExists(@RequestParam String username) {
        LambdaUpdateWrapper<SystemUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SystemUser::getUsername,username);
        long count = systemUserService.count(wrapper);
        return Result.ok(count==0);
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除后台用户信息")
    public Result removeById(@RequestParam Long id) {
        systemUserService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据ID修改后台用户状态")
    @PostMapping("updateStatusByUserId")
    public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<SystemUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(SystemUser::getId,id);
        wrapper.set(SystemUser::getStatus,status);
        systemUserService.update(wrapper);
        return Result.ok();
    }
}
