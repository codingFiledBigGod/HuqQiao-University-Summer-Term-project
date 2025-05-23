package edu.hqu.lease.web.app.controller.login;


import edu.hqu.lease.common.login.LoginUserHolder;
import edu.hqu.lease.common.result.Result;
import edu.hqu.lease.web.app.service.LoginService;
import edu.hqu.lease.web.app.vo.user.LoginVo;
import edu.hqu.lease.web.app.vo.user.UserInfoVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Tag(name = "登录管理")
@RestController
@RequestMapping("/app/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("login/getCode")
    @Operation(summary = "获取短信验证码")
    public Result getCode(@RequestParam String phone) {
        loginService.getCode(phone);
        return Result.ok();
    }

    @PostMapping("login")
    @Operation(summary = "登录")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        String jwt=loginService.login(loginVo);
        return Result.ok(jwt);
    }

    @GetMapping("info")
    @Operation(summary = "获取登录用户信息")
    public Result<UserInfoVo> info() {
        Long userID= LoginUserHolder.getLoginUser().getUserId();
        UserInfoVo info=loginService.getLoginUserById(userID);
        return Result.ok(info);
    }
}

