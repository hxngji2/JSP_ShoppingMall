# 피드백

## ERD 설계

- 현 ERD 설계로는 요구사항인 상품에 3개의 카테고리가 선택이 불가능한 상태입니다.
- 회원, 비회원의 장바구니를 식별할 수가 없습니다.


## UserRepositoryImpl

- `PrepareStatement`는 `try-with-resources`로 자원을 반납하고 있지만 ResultSet 자원은 반납하지 않고 있습니다. ResultSet도 `close()`를 호출해 주세요.


## 로그인

- 로그인, 로그아웃시 브라우저의 url을 확인해 보세요.
  - `redirect`가 뭔지 공부해 보세요.
- 로그아웃시 `GET`으로 요청을 보내고 있습니다. HTTP Method의 `GET` vs `POST` 차이점을 확인해 보세요.

## FrontServlet

- FrontServlet에서 Exceptino 처리를 상태를 항상 404로 처리하고 있습니다. 모든 Exception을 HTTP Status 404로 처리하는 것이 올바른 상황일까요??
  - 발생하는 예외마다 반환해야하는 Status가 다를 것입니다.
  - HTTP Status 값들을 한 번 공부해 보세요.
  - FrontServlet의 역할과 의미도 공부해 보세요.

```java
@Override
protected void service(HttpServletRequest req, HttpServletResponse resp){
    try{
        // ...
    }catch (Exception e){
        log.error("error:{}",e);
        // ...
        resp.setStatus(404);
        
        // ...
    }finally {
        // ...
    }
}
```


## ApplicationListener

- ServletContext에 `UserService` 객체를 넣어주고 있습니다. 넣어준 이유가 무엇인가요??
  - ServletContext의 역할과 의미에 대해 한 번 공부해 보시기 바랍니다. 

```java
@WebListener
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        
        // ...

        servletContext.setAttribute("userService", userService);
    }
}
```


## 공통

- 메인(index) 페이지에 상단 헤더에 관리자 페이지 링크가 왜 있을까요?
  - 관리자 페이지 링크는 관리자가 로그인 한 경우에 보여줘야 올바를 것 같습니다.
- Session에 넣는 키 값들은 상수로 관리하면 좋습니다. 
- Mypage 경로를 숨겨놓으면 회원이 로그인하고 들어갈 수가 없습니다.
- 로그 레벨을 `debug`로 설정하고 값을 확인하는 것은 좋은것 같습니다.


수고하셨습니다. 👏👏