package edu.hqu.lease.web.app.service;

import edu.hqu.lease.web.app.vo.user.LoginVo;
import edu.hqu.lease.web.app.vo.user.UserInfoVo;

public interface LoginService {
    void getCode(String phone);

    String login(LoginVo loginVo);

    UserInfoVo getLoginUserById(Long userID);
}
