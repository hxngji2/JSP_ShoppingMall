<%--
  Created by IntelliJ IDEA.
  User: hongjiwon
  Date: 12/8/23
  Time: 1:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<h1>회원 정보 수정</h1>
<form action="/mypage.do/updateUserPost.do" method="post">
    <div>
        <b class="name">아이디</b><br>
        <input type="text" name="userid" value = "${sessionScope.login.userId}" class="form-control" style="width:250px;" disabled><br>
    </div>
    <div>
        <b class="name">이름</b><br>
        <input type="text" name="username" value="${sessionScope.login.userName}" class = "form-control" style="width:250px;"><br>

    </div>
    <div>
        <b class="name">비밀번호</b><br>
        <input class="text" name="userpassword" type="password" name="UserPassword" placeholder="패스워드"><br><br>
    </div>
    <div>
        <b class="name">생년월일</b><br>
        <input type="text" name="userbirth" value="${sessionScope.login.userBirth}" class = "form-control" style="width:250px;" disabled><br>
    </div>
    <div>
        <button class="update" type="submit">저장하기</button>
    </div>
</form>
