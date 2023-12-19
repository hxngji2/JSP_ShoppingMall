package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.naming.ldap.BasicControl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/signupPost.do")
public class SignupPostController implements BaseController{

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    //TODO: 아이디 중복 검사
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String userId = req.getParameter("UserID");
            String userName = req.getParameter("UserName");
            String userPassword = req.getParameter("UserPassword");
            String userBirth = req.getParameter("UserBirth");

            User user = new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);


            userService.saveUser(user);
            return "shop/login/login_form";

        } catch(UserAlreadyExistsException e){

            return "redirect:/signup.do";
        }
    }
}
