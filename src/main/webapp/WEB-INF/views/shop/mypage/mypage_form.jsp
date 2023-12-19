<%--
  Created by IntelliJ IDEA.
  User: hongjiwon
  Date: 12/7/23
  Time: 9:38 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" session="true" %>
<h1>마이페이지</h1>

<jsp:include page="${layout_mypage_navbar}" />


<div class = "sign-form">
    <div class="form-group">
        <lavel th:for="userid">아이디</lavel>
        <input type="text" value = "${sessionScope.login.userId}" class="form-control" style="width:250px;" disabled><br>
    </div>
    <div class="form-group">
        <label th:for="username">이름</label>
        <input type="text" value="${sessionScope.login.userName}" class = "form-control" style="width:250px;" disabled><br>
    </div>

    <div class="form-group">
        <input type="hidden" value="${sessionScope.login.userPassword}" class = "form-control" style="width:250px;">
    </div>
    <div class="form-group">
        <label th:for="userbirth">생년월일</label>
        <input type="text" value="${sessionScope.login.userBirth}" class = "form-control" style="width:250px;" disabled>
    </div>


</div>
