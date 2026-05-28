<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:set var="root" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${root}/CSS/style.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

<nav class="MainMenuBar">
    <div class="GroupLeft">
        <a href="${root}/" class="LogoWrap">
            <img src="${root}/lgImg/logo.png" alt="SPGYM Logo" class="Logo">
        </a>
        <span class="NameWebsite">SPGYM</span>
    </div>
    <ul class="GroupCenter">
        <c:forEach var="main" items="${listMenu}">
            <li class="MenuItem">
                        <c:choose>
                            <c:when test="${empty main.link}">
                                <a href="javascript:void(0)" style="cursor: default;">${main.name}</a>
                            </c:when>

                            <c:otherwise>
                                <a href="${root}/${main.link}">${main.name}</a>
                            </c:otherwise>
                        </c:choose>

                <c:if test="${not empty main.subMenus}">
                    <div class="MegaBox">
                        <c:forEach var="sub" items="${main.subMenus}">
                            <div class="Column">
                                <h4>${sub.name}</h4>
                                <ul>
                                    <c:forEach var="item" items="${sub.items}">
                                        <li>

                                            <a href="${root}/${item.link}">${item.name}</a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </li>
        </c:forEach>
    </ul>

    <div class="GroupRight">
        <c:if test="${sessionScope.auth_role == 'ADMIN'}">
            <a href="page_manageOrder" class="admin-gradient-btn">
                <i class="fa-solid fa-users-gear"></i> ADMIN
            </a>
        </c:if>
        <form class="SearchBox" role="search" action="${root}/ListAllProduct" method="GET" id="searchForm">
            <input type="search" name="keyword" id="searchInput" placeholder="Tìm sản phẩm..." aria-label="Tìm kiếm" autocomplete="off">
            <button type="submit" aria-label="Tìm kiếm">
            <i class="fa fa-search"></i>
        </button>
        </form>
        <div class="UserWrap">
            <!-- Chưa Login -->
            <c:if test="${empty sessionScope.auth}">
                <a href="${root}/Login" class="IconBtn User">
                    <i class="fa-regular fa-user"></i>
                </a>
                <div class="UserHoverBox">
                    <div class="HoverItem">Đăng nhập / Đăng ký</div>
                </div>
            </c:if>
            <!-- Đã Login -->
            <c:if test="${not empty sessionScope.auth}">
                <a href="${root}/AccountInfo.jsp" class="IconBtn User">
                    <img src="${sessionScope.auth.avatarUrl}" alt="avatar" class="UserAvatar">
                </a>
                <div class="UserHoverBox">
                    <div class="HoverUsername">Tên tài khoản: ${sessionScope.auth.username}</div>
                    <a href="${root}/Logout" class="HoverLogout">Đăng xuất</a>
                </div>
            </c:if>
        </div>

        <a href="${root}/Cart.jsp" class="IconBtn ShoppingCart" aria-label="Giỏ hàng">
            <i class="fa-solid fa-cart-shopping"></i>
        </a>

        <button class="IconBtn Announcement" aria-label="Thông báo">
            <i class="fa-regular fa-bell"></i>
            <div class="MegaBoxAnnouncement">
                <div class="TitleAnnouncement">Thông Báo</div>
                <div class="ContentAnnounment">
                    <a href="#" class="ItemAnnouncement">
                        <div class="TitleAnnounce">Đặt hàng thành công</div>
                        <div class="DescriptionItemAnnounce">
                            chi tiết:
                            <span class="subDescriptionAnnounce">Tạ Lục Giác 15Kg Body Sculpture - Đen ...</span>
                        </div>
                    </a>
                </div>
            </div>
        </button>
    </div>
</nav>
<script src="${root}/JS/MenuBrightItem.js"></script>