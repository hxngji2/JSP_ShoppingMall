package com.nhnacademy.shoppingmall.common.filter;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import java.util.Objects;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter (urlPatterns = "/adminpage.do/*")
public class AdminCheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#11 /admin/ 하위 요청은 관리자 권한의 사용자만 접근할 수 있습니다. ROLE_USER가 접근하면 403 Forbidden 에러처리
        HttpSession session = req.getSession();

        User user  = (User) session.getAttribute("login");

        if (user != null){
            if (Objects.nonNull(user) && user.getUserAuth().equals(User.Auth.ROLE_ADMIN)) {
                chain.doFilter(req, res);
            } else if (user.getUserAuth().equals(User.Auth.ROLE_USER)) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Forbidden");
            }
        } else {
            res.sendRedirect("/login.do");
        }

    }
}
