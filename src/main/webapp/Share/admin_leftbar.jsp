<%@ page import="vn.edu.hcmuaf.fit.laptrinhweb2.BreadcrumbItem" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="AccountLeft">
    <div class="AvatarAccount">
        <img src="lgImg/avt.png" class="avtr">
        <div class="AccountName">
            <div class="nameAdmin">Admin</div>
            <div class="stk">Quản trị viên</div>
        </div>
    </div>
    <div class="AccountLeftOption">
        <a href="page_manageOrder?action=today" class="OptionIcon">
            <i class="fa-solid fa-box"></i>
            <div class="NameOption">Quản lý đơn hàng</div>
        </a>
        <a href="getAllAccount" class="OptionIcon">
            <i class="fa-solid fa-user"></i>
            <div class="NameOption">Quản lý người dùng</div>
        </a>
        <a href="page_allProduct" class="OptionIcon">
            <i class="fa-solid fa-shirt"></i>
            <div class="NameOption">Quản lý sản phẩm</div>
        </a>

        <a href="page_manageHotdeal" class="OptionIcon">
            <i class="fa-solid fa-percent"></i>
            <div class="NameOption">Quản lý ưu đãi</div>
        </a>
        <a href="page_manageBanner" class="OptionIcon">
            <i class="fa-solid fa-eye"></i>
            <div class="NameOption">Quản lý banner</div>
        </a>

        <a href="page_manageAnoun" class="OptionIcon">
            <i class="fa-solid fa-bullhorn"></i>
            <div class="NameOption">Quản lý thông báo</div>
        </a>

        <a href="manageBrand" class="OptionIcon">
            <i class="fa-solid fa-hurricane"></i>
            <div class="NameOption">Quản lý thương hiệu </div>
        </a>
        <a href="manageCollection" class="OptionIcon">
            <i class="fa-solid fa-box-open"></i>
            <div class="NameOption">Quản lý bộ sưu tập</div>
        </a>
<%--        <a href="manageSport" class="OptionIcon">--%>
<%--            <i class="fa-solid fa-futbol"></i>--%>
<%--            <div class="NameOption">Quản lý môn thể thao</div>--%>
<%--        </a>--%>
        <div class = "button_container">
            <a href="Home" class="sgoutAdmin">Quay về web</a>
        </div>
    </div>
</div>