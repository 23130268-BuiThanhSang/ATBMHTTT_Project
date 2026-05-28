<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="CSS/StyleForPagePayment.css">
    <link rel="stylesheet" href="CSS/Style.css">
</head>
<body>
<div class="headerForPagePayment">
    <div class="TitleForPagePayment"> THANH TOÁN</div>
</div>
<div class="BodyForPagePayment">
    <div class="InforPayment">
        <div class="InforCustomer">
            <div class="TitleInforCustomer">Thông Tin Khách Hàng :</div>
            <div class="IforUser">
                <label>Họ Và Tên: <span class="InforCustomerDetail">${sessionScope.auth.accountName}</span></label>

            </div>
            <div class="IforUser">
                <label>Số điện thoại: <span class="InforCustomerDetail">${sessionScope.auth.phoneNumber}</span></label>

            </div>

            <div class="IforUser">
                <label>Email: <span class="InforCustomerDetail">${sessionScope.auth.accountEmail}</span> </label>
            </div>
            <form action="ConfirmPayment" method="post">
            <div class="AddAddess">
                <label>Địa chỉ nhận hàng : </label>
                <div class="DefaultAddress">
                        <input type="text" name="address"
                               class="DefaultAddressText"
                               placeholder="Hãy thêm địa chỉ nhận hàng ...">
                </div>
            </div>
                <button type="submit" class="ConfirmPaymentButton">
                    Xác Nhận
                </button>
    </form>
        </div>

    </div>
    <div class="InforProduct">
        <div class="InforProductTitle"> Tóm Tắt Đơn Hàng</div>
        <div class="InforItemProduct">

            <c:forEach var="item" items="${sessionScope.cart.items}">
                <c:if test="${item.selected}">
                    <div class="ProductItemInPayment">
                        <img src="${item.image}">
                        <div>
                            <div>${item.productName}</div>
                            <div>Màu: ${item.color}</div>
                            <div>Size: ${item.size}</div>
                            <fmt:formatNumber value="${item.price}" pattern="#,###"/>VNĐ
                            <div>SL: ${item.quantity}</div>
                        </div>
                    </div>
                </c:if>
            </c:forEach>

        </div>
        <div class="BottomOfPayment">
            <div class="TotalPaymentProduct">

                <div class="TotalPrice">
                    <span class="ToltalOTP">Thành Tiền : </span>
                    <fmt:formatNumber value="${sessionScope.cart.selectedTotalPrice}" pattern="#,###"/>VNĐ
                </div>
            </div>
        </div>


    </div>
</div>

<jsp:include page="/Share/footer.jsp" />

</body>
</html>