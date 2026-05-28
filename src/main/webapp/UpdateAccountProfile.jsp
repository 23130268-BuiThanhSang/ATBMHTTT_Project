<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="CSS/AccountInfo.css?v=<%=System.currentTimeMillis()%>">

</head>
<body>
<jsp:include page="/Share/header.jsp" />
<main class="layout layout-scroll">
    <aside class="AccountLeft">
        <div class="AvatarAccount">
            <img src="${sessionScope.auth.avatarUrl}" class="avtr" alt="avatar">
            <div class="AccountName">
                <div class="nameus">${sessionScope.auth.accountName}</div>
                <div class="stk">ID tài khoản: ${sessionScope.auth.id}</div>
            </div>
        </div>
        <div class="AccountLeftOption">
            <a href="AccountInfo.jsp" class="OptionIcon">
                <i class="fa-solid fa-user"></i>
                <div class="NameOption">Thông tin tài khoản</div>
            </a>
            <div class="NameOptionBold active">
                <i class="fa-solid fa-clipboard-user"></i>
                <div class="NameOption">Cập nhật hồ sơ</div>
            </div>
            <a href="ChangePassword.jsp" class="OptionIcon">
                <i class="fa-solid fa-lock"></i>
                <div class="NameOption">Đổi mật khẩu</div>
            </a>
            <a href="HistorySale.jsp" class="OptionIcon">
                <i class="fa-solid fa-cart-arrow-down"></i>
                <div class="NameOptionBoldText">Lịch sử mua hàng</div>
            </a>
            <div class="SignOut">
                <a href="${pageContext.request.contextPath}/Logout" class="OptionSignOut">Đăng xuất</a>
            </div>
        </div>
    </aside>
    <div class="addressInfo">
        <div id="addressTitle">Cập nhật hồ sơ</div>
            <div class="addressList">
                <form action="${pageContext.request.contextPath}/AccountUpdateProfile" method="post">
                    <div class="adr">
                        <div>Tên tài khoản</div>
                        <input type="text" name="accountName" value="${sessionScope.auth.accountName}">
                    </div>
                    <div class="adr">
                        <div>Avatar</div>
                        <input type="text" name="avatarUrl" value="${sessionScope.auth.avatarUrl}">
                    </div>
                    <div class="adr">
                        <div>Số điện thoại</div>
                        <input type="text" name="phone" value="${sessionScope.auth.phoneNumber}">
                    </div>
                    <c:if test="${not empty success}">
                        <div class="msg-success">${success}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="msg-error">${error}</div>
                    </c:if>

                    <button type="submit" class="addressButton">Lưu thông tin</button>
                </form>
            </div>
    </div>
</main>

<jsp:include page="/Share/footer.jsp" />
<script src="JS/Notification.js"></script>
</body>
</html>
