<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/AccountInfo.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="CSS/StyleForHistorySale.css">
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
            <a href="UpdateAccountProfile.jsp" class="OptionIcon">
                <i class="fa-solid fa-clipboard-user"></i>
                <div class="NameOption">Cập nhật hồ sơ</div>
            </a>
            <a href="ChangePassword.jsp" class="OptionIcon">
                <i class="fa-solid fa-lock"></i>
                <div class="NameOption">Đổi mật khẩu</div>
            </a>
            <div class="NameOptionBold active">
                <i class="fa-solid fa-cart-arrow-down"></i>
                <div class="NameOptionBoldText">Lịch sử mua hàng</div>
            </div>
            <div class="SignOut">
                <a href="${pageContext.request.contextPath}/Logout" class="OptionSignOut">Đăng xuất</a>
            </div>
        </div>
    </aside>
    <section class="BodyHistorySale">
        <!-- Đơn hàng 1 -->
        <div class="OrderBlock">
            <header class="section-header">
                <div>
                    <p class="eyebrow">Mã đơn hàng</p>
                    <h2 class="section-title">
                        <span class="order-code">G09010</span>
                    </h2>
                </div>
                <div class="status-badges">
                    <span class="OrderStatus status-completed">Đã hoàn thành</span>
                    <div class="OrderDate">Ngày đặt hàng: <span>12/3/2024</span></div>
                </div>
            </header>
            <div class="ListItemProductInHistorySale">
                <div class="ItemProductInHistorySale">
                    <img src="https://supersports.com.vn/cdn/shop/products/BW-460-15KG-1.jpg?v=1703644838&width=1600" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Tạ Lục Giác 15Kg Body Sculpture - Đen</div>
                        <div class="BrandProduct">SCULPTURE</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
                <div class="ItemProductInHistorySale">
                    <img src="https://supersports.com.vn/cdn/shop/products/BW-460-15KG-1.jpg?v=1703644838&width=1600" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Tạ Lục Giác 15Kg Body Sculpture - Đen</div>
                        <div class="BrandProduct">SCULPTURE</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
                <div class="ItemProductInHistorySale">
                    <img src="https://supersports.com.vn/cdn/shop/products/BW-460-15KG-1.jpg?v=1703644838&width=1600" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Tạ Lục Giác 15Kg Body Sculpture - Đen</div>
                        <div class="BrandProduct">SCULPTURE</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
            </div>
        </div>

        <!-- Đơn hàng 2 -->
        <div class="OrderBlock">
            <header class="section-header">
                <div>
                    <p class="eyebrow">Mã đơn hàng</p>
                    <h2 class="section-title">
                        <span class="order-code">G09011</span>
                    </h2>
                </div>
                <div class="status-badges">
                    <span class="OrderStatus status-completed">Đã hoàn thành</span>
                    <div class="OrderDate">Ngày đặt hàng: <span>18/3/2024</span></div>
                </div>
            </header>
            <div class="ListItemProductInHistorySale">
                <div class="ItemProductInHistorySale">
                    <img src="https://via.placeholder.com/160" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Dây kháng lực tập gym</div>
                        <div class="BrandProduct">GYM PRO</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
                <div class="ItemProductInHistorySale">
                    <img src="https://via.placeholder.com/160" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Găng tay tập gym ProGrip</div>
                        <div class="BrandProduct">PROGRIP</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
            </div>
        </div>

        <!-- Đơn hàng 3 -->
        <div class="OrderBlock">
            <header class="section-header">
                <div>
                    <p class="eyebrow">Mã đơn hàng</p>
                    <h2 class="section-title">
                        <span class="order-code">G09012</span>
                    </h2>
                </div>
                <div class="status-badges">
                    <span class="OrderStatus status-completed">Đã hoàn thành</span>
                    <div class="OrderDate">Ngày đặt hàng: <span>25/3/2024</span></div>
                </div>
            </header>
            <div class="ListItemProductInHistorySale">
                <div class="ItemProductInHistorySale">
                    <img src="https://via.placeholder.com/160" alt="Product">
                    <div class="InforProduct">
                        <div class="NameProduct">Kettlebell 12Kg</div>
                        <div class="BrandProduct">STRONGFIT</div>
                    </div>
                    <div class="ButtonControl"><button class="Review">Đánh Giá</button></div>
                </div>
            </div>
        </div>
    </section>
</main>
<jsp:include page="/Share/footer.jsp" />
<script src="JS/Notification.js"></script>
</body>
</html>

