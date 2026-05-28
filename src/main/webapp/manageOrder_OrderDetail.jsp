<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chi Tiết Đơn Hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageOrder_OrderDetail.css">
</head>
<body>

<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="OrderManagerUI">
        <div class="topRow">
            <a class="turnBack" href="page_manageOrder?action=all">Quay lại</a>
        </div>

        <div class="OrderManageContainer">
            <form action="updateOrder" method="post">
                <input type="hidden" name="orderId" value="${order.id}" />
                <!-- ================= ORDER HEADER ================= -->
                <div class="OrderHeader">
                    <h2 class="OrderTitle">Chi Tiết Đơn Hàng</h2>

                    <p class="OrderID">
                        Mã đơn: <span>ID: ${order.id}</span>
                    </p>

                    <p class="OrderDate">
                        Ngày tạo: <span>${order.order_date}</span>
                    </p>

                    <div class="OrderStatusBox">
                        <label>Trạng thái:</label>
                        <select id="orderStatus" class="StatusSelect" name="status">
                            <option value="PROCESSING" ${order.order_status == 'PROCESSING' ? 'selected' : ''}>Đang Xử Lý</option>
                            <option value="SHIPPED"  ${order.order_status == 'SHIPPED'  ? 'selected' : ''}>Đã Giao</option>
                            <option value="PENDING"    ${order.order_status == 'PENDING'    ? 'selected' : ''}>Tạm Ngưng</option>
                            <option value="CANCELLED"  ${order.order_status == 'CANCELLED'  ? 'selected' : ''}>Đã Hủy</option>
                        </select>
                    </div>
                </div>

                <!-- ================= ORDER ITEMS ================= -->
                <div class="OrderItemsContainer">
                    <h3>Sản phẩm</h3>
<%--                    <button class="StoredCheckButton">Kiểm hàng</button>--%>

                    <div class="AllItemsContainer">
                        <c:forEach items="${order.items}" var="item">
                            <div class="OrderDetailItem">
                                <span class="ItemName">
                                        ${item.product.name}<br>
                                            Kích cỡ: ${item.variant.getSizeString()}<br>
                                            Màu sắc: ${item.variant.color}
                                </span>

                                <span class="ItemQty">
                                    x${item.quantity}
                                </span>

                                <span class="ItemPrice">
                                    <fmt:formatNumber value="${item.price}" type="number" groupingUsed="true"/> đ
                                </span>

<%--                                <span class="StoredStatus">--%>
<%--                                    Còn Hàng--%>
<%--                                </span>--%>
                            </div>
                        </c:forEach>
                    </div>
                </div>

                <!-- ================= TOTAL ================= -->
                <div class="OrderTotalBox">
                    <p class="FinalTotal">
                        <strong>Tổng cộng:</strong>
                        <fmt:formatNumber value="${order.price}" type="number" groupingUsed="true"/> đ
                    </p>
                </div>

                <!-- ================= ACTION BUTTONS ================= -->
                <div class="ActionButtons">
                    <button type = "submit" class="SaveBtn">Lưu Thay Đổi</button>
                </div>
            </form>
        </div>
    </div>
</div>

</body>
</html>
