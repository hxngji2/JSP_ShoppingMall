package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.controller.auth.LoginController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        DbConnectionThreadLocal.initialize();
        ServletContext servletContext = sce.getServletContext();


        //todo 다음은 13번까지 다하면 15번에 있는 more 구현하면 됩니다~~
        // todo 회원가입 또는 로그인을 먼저 해보는게 쉬울거야~


        User admin = new User("admin", "이태희", "xogml12", "20000915", User.Auth.ROLE_ADMIN, 1_000_000, LocalDateTime.now(), null);
        User user = new User("user", "홍지원", "wldnjs12", "20000210", User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);

        if (Objects.isNull(userService.getUser(admin.getUserId()))){
            log.debug("admin등록 ");
            userService.saveUser(admin);
            log.debug("admin 등록 후");
        }
        if (Objects.isNull(userService.getUser(user.getUserId()))){
            userService.saveUser(user);
        }
        servletContext.setAttribute("userService", userService);
        DbConnectionThreadLocal.reset();

    }
}
