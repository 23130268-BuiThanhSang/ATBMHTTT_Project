<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Lịch sử mua hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/AccountInfo.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/StyleForHistorySale.css">
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
            <a href="${pageContext.request.contextPath}/account" class="OptionIcon">
                <i class="fa-solid fa-user"></i>
                <div class="NameOption">Thông tin tài khoản</div>
            </a>
            <a href="${pageContext.request.contextPath}/profile" class="OptionIcon">
                <i class="fa-solid fa-clipboard-user"></i>
                <div class="NameOption">Cập nhật hồ sơ</div>
            </a>
            <a href="${pageContext.request.contextPath}/change-password" class="OptionIcon">
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
        <c:if test="${empty listOrderDTOs}">
            <div class="NoOrder" style="text-align: center; padding: 40px; font-size: 16px; color: #666;">
                <i class="fa-solid fa-box-open" style="font-size: 48px; margin-bottom: 10px; color: #ccc;"></i>
                <p>Bạn chưa có đơn hàng nào.</p>
            </div>
        </c:if>

        <c:forEach var="orderDTO" items="${listOrderDTOs}">
            <a href="DigitalSignature?orderId=${orderDTO.order.id}" class="OrderBlock">

                <header class="section-header">
                    <div class="order-code-block">
                        <p class="eyebrow">Mã đơn hàng</p>
                        <span class="order-code">#${orderDTO.order.id}</span>
                    </div>

                    <div class="status-group-container">
                        <div class="status-item">
                            <span class="status-label">Trạng thái ký nhận:</span>
                            <span class="VerifyStatus status-${orderDTO.verifyStatus.toString().toLowerCase()}">
                                    ${orderDTO.verifyStatus}
                            </span>
                        </div>
                        <div class="status-item">
                            <span class="status-label">Trạng thái giao hàng:</span>
                            <span class="OrderStatus status-${orderDTO.order.order_status.toLowerCase()}">
                                    ${orderDTO.order.order_status}
                            </span>
                        </div>
                    </div>

                    <div class="order-meta-right">
                        <div class="OrderDate">
                            Ngày đặt hàng:
                            <span>
                                <fmt:parseDate value="${orderDTO.order.order_date}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" type="both" />
                                <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm" />
                            </span>
                        </div>
                        <div class="OrderTotal">
                            Tổng tiền:
                            <span class="price">
                                <fmt:formatNumber value="${orderDTO.order.price}" type="number" maxFractionDigits="0"/>đ
                            </span>
                        </div>
                    </div>
                </header>

                <div class="ListItemProductInHistorySale">
                    <c:forEach var="item" items="${orderDTO.order.items}">
                        <div class="ItemProductInHistorySale">
                            <img src="getImage?path=${item.variant.topImage}" alt="${item.product.name}">
                            <div class="InforProduct">
                                <div>
                                    <div class="NameProduct">${item.product.name}</div>
                                    <div class="VariationProduct">
                                        Màu: ${item.variant.color} | Size: ${item.variant.sizeString}
                                    </div>
                                </div>
                                <div class="PriceAndQuantity">
                                    <div class="PriceProduct">
                                        <fmt:formatNumber value="${item.price}" type="number" maxFractionDigits="0"/> đ
                                    </div>
                                    <div class="QuantityProduct">SL: ${item.quantity}</div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </a>
        </c:forEach>
    </section>
</main>
<jsp:include page="/Share/footer.jsp" />
<script src="JS/Notification.js"></script>
</body>
</html>