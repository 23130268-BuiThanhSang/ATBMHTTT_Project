<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" href="CSS/Style.css?v=<%=System.currentTimeMillis()%>">
</head>
<body id = "LoginBody" data-step="${activeStep}">
<%--Đăng nhập--%>
<form id="loginPage" class="login page"
      style="<c:if test='${activeStep ne "loginPage"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/Login" method="post">
    <h2 class="loginTxt">Đăng nhập</h2>
    <input type="text" name="username" placeholder="nhập tên tài khoản" class="loginip">
    <input type="password" name="pass" placeholder="nhập mật khẩu" class="loginip"><br>

    <div class="message-login">
        <span class="error">${error}</span>
    </div>
    <div class="links">
        <div class="left">
            <a href="#" onclick="showPage('forgot-step1')">Đổi mật khẩu</a>
        </div>
        <div class="right">
            <a href="#" onclick="showPage('register-step1')">Đăng kí tài khoản</a>
        </div>
    </div>
    <button type="submit" class="loginBt">Đăng nhập</button><br>
    <div class="homeLink">
        <a href="HomePage.jsp">Trang chủ</a>
    </div>
</form>

<%--Đăng ký--%>
<form id="register-step1" class="register page"
      style="<c:if test='${activeStep ne "register-step1"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/Register" method="post">
    <input type="hidden" name="step" value="info">
    <h2 class="loginTxt">Đăng kí tài khoản</h2>
    <input type="text" name="reg_username" placeholder="nhập tên tài khoản"
           class="loginip"><br>

    <input type="text" name="reg_phone" placeholder="nhập số điện thoại"
           class="loginip"><br>

    <input type="email" name="reg_email" placeholder="nhập email"
           class="loginip"><br>

    <div class="message-register">
        <span class="error">${regError}</span>
        <span class="success">${success}</span>
        <span class="loading" style="display:none;">Đang xác thực email, vui lòng chờ...</span>
    </div>
    <button type="submit" class="loginBt">Tiếp tục</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>
<form id="register-step2" class="register1 page"
      style="<c:if test='${activeStep ne "register-step2"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/Register" method="post">
    <input type="hidden" name="step" value="otp">
    <h2 class="loginTxt">Nhập OTP Xác nhận</h2>
    <input type="text" name="otp" placeholder="nhập otp 6 chữ số gửi qua mail"
           class="loginip"><br>

    <div class="message-register">
        <span class="error">${regError}</span>
        <span class="success">${success}</span>
    </div>
    <button type="submit" class="loginBt">Tiếp tục</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>
<form id="register-step3" class="register3 page"
      style="<c:if test='${activeStep ne "register-step3"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/Register" method="post">
    <input type="hidden" name="step" value="password">
    <h2 class="loginTxt">Đặt mật khẩu</h2>
        <input type="password" name="reg_pass" placeholder="nhập mật khẩu" class="loginip"><br>
        <input type="password" name="reg_confirm" placeholder="xác nhận mật khẩu" class="loginip"><br>
    <div class="message-register">
        <span class="error">${regError}</span>
        <span class="success">${success}</span>
    </div>
    <button type="submit" class="loginBt">Xác nhận</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>

<%--Quên mật khẩu--%>
<form id="forgot-step1" class="forgotpw page"
      style="<c:if test='${activeStep ne "forgot-step1"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/ChangePassword" method="post">
    <input type="hidden" name="step" value="email">
    <h2 class="loginTxt">Quên mật khẩu</h2>

    <input type="email" name="cp_email" placeholder="nhập email"
           class="loginip"><br>

    <div class="message-forgot">
        <span class="error">${cpError}</span>
        <span class="success">${cpSuccess}</span>
        <span class="loading" style="display:none;">Đang xác thực email, vui lòng chờ...</span>
    </div>
    <button class="loginBt" type="submit">Tiếp tục</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>
<form id="forgot-step2" class="forgotpw page"
      style="<c:if test='${activeStep ne "forgot-step2"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/ChangePassword" method="post">
    <input type="hidden" name="step" value="otp">
    <h2 class="loginTxt">Nhập OTP Xác nhận</h2>

    <input type="text" name="otp" placeholder="nhập otp 6 chữ số đã gửi qua mail"
           class="loginip"><br>

    <div class="message-forgot">
        <span class="error">${cpError}</span>
        <span class="success">${cpSuccess}</span>
    </div>
    <button class="loginBt" type="submit">Tiếp tục</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>
<form id="forgot-step3" class="forgotpw3 page"
      style="<c:if test='${activeStep ne "forgot-step3"}'>display:none;</c:if>"
      action="${pageContext.request.contextPath}/ChangePassword" method="post">
    <input type="hidden" name="step" value="password">
    <h2 class="loginTxt">Nhập mật khẩu mới</h2>

        <input type="password" name="cp_pass" placeholder="mật khẩu mới" class="loginip"><br>
        <input type="password" name="cp_confirm" placeholder="xác nhận mật khẩu mới" class="loginip"><br>

    <div class="message-forgot">
        <span class="error">${cpError}</span>
        <span class="success">${cpSuccess}</span>
    </div>
    <button class="loginBt" type="submit">Xác nhận</button><br>
    <div class="homeLink">
        <a href="#" onclick="showPage('loginPage')">Quay lại đăng nhập</a>
    </div>
</form>
<script src="JS/login.js"></script>
</body>
</html>
