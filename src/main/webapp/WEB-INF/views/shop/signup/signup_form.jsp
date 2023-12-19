<%--
  Created by IntelliJ IDEA.
  User: hongjiwon
  Date: 12/7/23
  Time: 2:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>회원가입</h1>
<hr>
<form method="post" action="/signupPost.do">
    <div>
        <b class="name">아이디</b><br>
        <input class="text" type="text" name="UserID" placeholder="아이디"><br><br>
    </div>
    <div>
        <b class="name">이름</b><br>
        <input class="text" type="username" name="UserName" placeholder="이름"><br><br>
    </div>
    <div>
        <b class="name">비밀번호</b><br>
        <input class="text" type="password" name="UserPassword" placeholder="패스워드"><br><br>
    </div>
    <div>
        <b class="name">생년월일</b><br>
        <input class="text" type="userbirth" name="UserBirth" placeholder="생년월일"><br><br><br>
    </div>
    <div>
        <button class="sign_up" type="submit">Sign up</button>
    </div>
</form>
