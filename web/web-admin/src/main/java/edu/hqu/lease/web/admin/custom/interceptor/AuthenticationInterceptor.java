package edu.hqu.lease.web.admin.custom.interceptor;

import edu.hqu.lease.common.login.LoginUser;
import edu.hqu.lease.common.login.LoginUserHolder;
import edu.hqu.lease.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getHeader("access-token");

        Claims claims = JwtUtil.parseToken(token);

        Long l = claims.get("userId", Long.class);
        String userName = claims.get("username",String.class);
        LoginUserHolder.setLoginUser(new LoginUser(l,userName));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clear();
    }
}
