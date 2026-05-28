<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageProduct.css">
</head>
<body>
<div class="ManagerUser">
    <jsp:include page="/Share/admin_leftbar.jsp" />
    <div class="manageUserUi">
        <div class="userHeader">
            <div id="mangeUserHeader">Quản lý Người Dùng / Tất Cả</div>
            <div class="userHeaderActions">
                <form action="searchAccount" method="get">
                    <input type="text" name="keyword" placeholder="Tìm tên / id user..." id="adminSearch">
                    <button class="TabButton" type="submit">
                        <i class="fa fa-search"></i>
                    </button>
                    <a href="manageUser_AddUser.jsp" class="TabButton">
                        Thêm người dùng
                    </a>
                </form>
            </div>

            <div class="typeUser">
                <div id="userTx">User</div>
                <div id="userId">ID</div>
                <div id="userMember">Thẻ thành viên</div>
                <div id="userPay">Tổng chi tiêu</div>
            </div>
            <div class="userInfo">
                <c:forEach var="acc" items="${accounts}">
                    <a class="userBox" href = "getAccountDetail?id=${acc.account.id}">
                        <div class="userGroup">

                            <!-- Username -->
                            <div class="userTxt">
                                    ${acc.account.username}
                            </div>

                            <!-- ID -->
                            <div class="userTxt1">
                                ${acc.account.id}
                            </div>

                            <!-- Account status -->
                            <div class="userTxt2">
                                <c:choose>
                                    <c:when test="${acc.account.accountStatus == 1}">
                                        Actived
                                    </c:when>
                                    <c:otherwise>
                                        Unactive
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <!-- Total spending -->
                            <div class="userTxt3">
                                    ${acc.totalSpent}
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>


            <div class="choosePage">
                <div class="pageInfo"><</div>
                <div class="pageInfoNumber">1/23</div>
                <div class="pageInfo">></div>
            </div>
        </div>
    </div>
</div>

</body>
</html>
