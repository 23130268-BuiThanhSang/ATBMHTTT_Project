<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageOrder_AllOrders.css">
</head>
<body>
<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />
    <div class="OrderManagerUI">
        <div class="topRow">
            <a class = "turnBack" href = "page_manageOrder?action=today">
                Quay lại
            </a>
        </div>
        <div class="bottomBox">
            <p class = "BoxTitle">Lọc Đơn Hàng: </p>
            <form class="FilterBar"
                  action="page_manageOrder"
                  method="get">
                <input type="hidden" name="action" value="filter">
                <input class="day_input"
                       name="day"
                       value="${day != null ? day : ''}"
                       placeholder="Nhập ngày">

                <label class="FilterLabel">Tháng:</label>
                <select name="month" class="FilterSelect">
                    <option value="">Tất cả</option>
                    <option value="1" ${month == 1 ? "selected" : ""}>1</option>
                    <option value="2" ${month == 2 ? "selected" : ""}>2</option>
                    <option value="3" ${month == 3 ? "selected" : ""}>3</option>
                    <option value="4" ${month == 4 ? "selected" : ""}>4</option>
                    <option value="5" ${month == 5 ? "selected" : ""}>5</option>
                    <option value="6" ${month == 6 ? "selected" : ""}>6</option>
                    <option value="7" ${month == 7 ? "selected" : ""}>7</option>
                    <option value="8" ${month == 8 ? "selected" : ""}>8</option>
                    <option value="9" ${month == 9 ? "selected" : ""}>9</option>
                    <option value="10" ${month == 10 ? "selected" : ""}>10</option>
                    <option value="11" ${month == 11 ? "selected" : ""}>11</option>
                    <option value="12" ${month == 12 ? "selected" : ""}>12</option>
                </select>

                <label class="FilterLabel">Năm:</label>
                <select name="year" class="FilterSelect">
                    <option value="">Tất cả</option>
                    <option value="2025" ${year == 2025 ? "selected" : ""}>2025</option>
                </select>


                <label class="FilterLabel">Trạng thái:</label>
                <select name="status" class="FilterSelect">
                    <option value="all" ${status == null || status == 'all' ? "selected" : ""}>Tất cả</option>
                    <option value="PENDING" ${status == 'PENDING' ? "selected" : ""}>Tạm ngưng</option>
                    <option value="SHIPPED" ${status == 'SHIPPED' ? "selected" : ""}>Đã giao</option>
                    <option value="CANCELLED" ${status == 'CANCELLED' ? "selected" : ""}>Đã hủy</option>
                    <option value="PROCESSING" ${status == 'PROCESSING' ? "selected" : ""}>Đang xử lý</option>
                </select>

                <button type="submit" class="FilterBtn">Filter</button>
            </form>


            <div class="OrdersDetailDisplay">
                <c:forEach var="dto" items="${orderDTOs}">
                    <a href="page_orderDetail?orderId=${dto.order.id}" class="OrderItem">

                        <div class="OrderHeader">
                            <span class="OrderID">ID: ${dto.order.id}</span>
                            <span class="OrderDate">${dto.order.order_date}</span>

                            <span class="OrderStatus
                    <c:choose>
                        <c:when test="${dto.order.order_status == 'PENDING'}">status-pending</c:when>
                        <c:when test="${dto.order.order_status == 'PROCESSING'}">status-processing</c:when>
                        <c:when test="${dto.order.order_status == 'SHIPPED'}">status-shipped</c:when>
                        <c:when test="${dto.order.order_status == 'CANCELLED'}">status-cancelled</c:when>
                    </c:choose>
                ">
                                    ${dto.order.order_status}
                            </span>
                        </div>

                        <div class="SignStatusRow">
                            <span class="SignLabel">Sign Status:</span>
                            <span class="SecurityBadge security-${dto.verifyStatus}">
                                    ${dto.verifyStatus}
                            </span>
                        </div>

                        <div class="OrderSummary">
                            <p class="CustomerName">
                                User ID: <span>${dto.order.user_id}</span>
                            </p>

                            <p class="OrderTotal">
                                Total: <span>${dto.order.price} VND</span>
                            </p>

                            <p class="ItemsCount">
                                Items:
                                <span><c:out value="${dto.order.items.size()}" /></span>
                            </p>
                        </div>
                    </a>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>

