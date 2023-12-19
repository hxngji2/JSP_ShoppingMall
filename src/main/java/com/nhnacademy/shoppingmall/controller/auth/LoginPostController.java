package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.

        String id = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        log.debug(id);
        try {
            User user = userService.doLogin(id, password);

            if (user != null) {
                log.debug("if");
                if (user.getUserAuth().equals(User.Auth.ROLE_ADMIN)){
                    HttpSession httpSession = req.getSession(true);
                    httpSession.setMaxInactiveInterval(-1);
                    httpSession.setAttribute("admin",user);

                }
                HttpSession httpSession = req.getSession(true);
                httpSession.setMaxInactiveInterval(60 * 60);
                httpSession.setAttribute("login", user);
                return "shop/main/index";
            }

        } catch (UserNotFoundException e) {

        }

        return "shop/login/login_form";
    }
}
