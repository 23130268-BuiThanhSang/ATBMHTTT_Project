<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="CSS/StyleForPageShopping.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/Style.css">
</head>

<body>
<!--menu cho trang web-->
<jsp:include page="/Share/header.jsp" />
<div class="ShoppingCartForProduct">
    <div class="headerShoppingCart">
        GIỎ HÀNG
    </div>
    <form class="bodyShoppingCart" action="CheckOut" method="post">
        <div class="ListProductInShoppingCart">
            <div class="ControlForAllItemsProductInShoppingCart">
                <span class="ListTitle">Danh sách sản phẩm</span>
                <label class="SelectAllItem">
                    Chọn tất cả
                    <input type="checkbox" id="selectAllCheckbox" onclick="toggleAllProducts()">
                </label>
            </div>

            <div class="ListProductInnerScroll">
                <c:if test="${empty sessionScope.cart || sessionScope.cart.items.size() == 0}">
                    <div id="emptyCartMessage" style="margin-left: 10px">
                        Hiện chưa có sản phẩm nào trong giỏ hàng.
                    </div>
                </c:if>
                <c:if test="${not empty sessionScope.cart && sessionScope.cart.items.size() > 0}">
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <div class="ItemProductInshoppingCart" data-price="${item.price}"
                         data-quantity="${item.quantity}">
                        <label class="CheckboxItem">
                            <input type="checkbox"
                                   class="item-checkbox"
                                   name="selectedIds"
                                   value="${item.variantId}"
                                   <c:if test="${item.selected}">checked</c:if>>

                        </label>

                        <img src="${item.image}" class="ProductItemImage">

                        <div class="ProductItemInfor">
                            <div class="ProductItemName">${item.productName}</div>
                            <div class="ProductItemBrand">Màu sắc: ${item.color}</div>
                            <div class="ProductItemBrand">Kích cỡ: ${item.size}</div>
                            <div class="ProductItemBrand">
                                Giá: <fmt:formatNumber value="${item.price}" type="number"/> đ
                            </div>
                        </div>

                        <div class="ProductItemControl">
                            <div class="QuantityBoxForProduct">
                                <button type="button">-</button>
                                <span class="quantity">${item.quantity}</span>
                                <button type="button">+</button>
                                <a href="remove-from-cart?variantId=${item.variantId}" class="delProduct" onclick="return confirm('Bạn có chắc chắn muốn xóa sản phẩm này?');">Xóa</a>
<%--                                <a href="remove-from-cart?variantId=${item.variantId}" class="delProduct">Xóa </a>--%>
                            </div>
                        </div>
                    </div>
                </c:forEach>
                </c:if>
            </div>

            <div class="CartActionsFooter">
                <button type="button" class="DeleteCartButton" id="deleteCartBtn">
                <i class="fa-solid fa-trash-can"></i>
                    <span> Xóa giỏ hàng</span>
                </button>
            </div>
        </div>

        <div class="OrderSumary">
            <div class="OrderSumaryTitle">Tóm tắt đơn hàng</div>
            <div class="OrderSumaryDetail">
                <div class="SummaryRow">
                    <span id="selectedCount">Đã chọn: 0 sản phẩm</span>
                </div>
                <div class="SummaryRow">
                    <span>Người nhận: ${sessionScope.auth.accountName}</span>
                </div>
                <div class="SummaryRow">
                    <span>Số điện thoại: ${sessionScope.auth.phoneNumber}</span>
                </div>
                <div class="SummaryRow">
                    <span>Email: ${sessionScope.auth.accountEmail}</span>
                </div>
<%--                <div class="SummaryRow">--%>
<%--                    <span>Mô tả thêm (nếu có): </span>--%>
<%--                </div>--%>
<%--                <div class="SummaryRow">--%>
<%--                    <textarea name="note" rows="7"--%>
<%--                              placeholder="Thời gian giao, địa điểm cụ thể..."--%>
<%--                              class="order-note"></textarea>--%>
<%--                </div>--%>
                <div class="SummaryRow">Tổng thành tiền:<span id="totalPrice">0 đ</span>
                </div>
            </div>
            <div class="OrderButtons">
                <button type="submit" class="ContinueButton">
                    Đặt hàng</button>
<%--                <button type="button" class="ContinueButton" id="placeOrderBtn">Đặt hàng</button>--%>
            </div>
        </div>
    </form>
</div>
<!-- Xoa san pham -->
<div id="deleteProductModal" class="modal-overlay" style="display: none;">
    <div class="modal-confirm-box">
        <div class="modal-title" id="modalTitle">Xác nhận xóa sản phẩm?</div>
        <div class="modal-message" id="modalMessage">Bạn có chắc muốn xóa sản phẩm này khỏi giỏ hàng không?</div>
        <div class="modal-actions">
            <button id="modalDeleteCancel" class="modal-btn cancel">Hủy</button>
            <button id="modalDeleteConfirm" class="modal-btn confirm">Xóa</button>
        </div>
    </div>
</div>
<!-- Dat hang -->
<%--<div id="orderCompleteModal" class="modal-overlay" style="display:none;">--%>
<%--    <div class="modal-confirm-box">--%>
<%--        <div class="modal-title" id="orderModalTitle">Đặt hàng hoàn tất</div>--%>
<%--        <div class="modal-message" id="orderModalMessage">Bạn đã đặt hàng hoàn tất, bạn muốn xem tiến độ đơn hàng không?</div>--%>
<%--        <div class="modal-actions">--%>
<%--            <button id="orderBackBtn" class="modal-btn cancel">Hủy</button>--%>
<%--            <button id="orderViewBtn" class="modal-btn confirm" style="background: #1D539C ">Xác nhận</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>

<jsp:include page="/Share/footer.jsp" />
<script src="${pageContext.request.contextPath}/JS/Notification.js"></script>
<script src="${pageContext.request.contextPath}/JS/PageShopping.js?v=<%=System.currentTimeMillis()%>"></script>
<script src="${pageContext.request.contextPath}/JS/ProductMainPage.js?v=<%=System.currentTimeMillis()%>"></script>
<script>
    const historyUrl = '<%= request.getContextPath() %>/HistorySale';
</script>
</body>
</html>