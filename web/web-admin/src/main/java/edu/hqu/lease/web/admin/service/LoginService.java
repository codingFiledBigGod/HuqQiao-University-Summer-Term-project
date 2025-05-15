package edu.hqu.lease.web.admin.service;

import edu.hqu.lease.web.admin.vo.login.CaptchaVo;
import edu.hqu.lease.web.admin.vo.login.LoginVo;
import edu.hqu.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo getLoginUserInfoById(Long userId);
}
