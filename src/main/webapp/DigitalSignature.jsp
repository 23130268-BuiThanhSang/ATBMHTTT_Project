<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Digital Signature</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/StyleForDigitalSignature.css">
</head>
<body>

<jsp:include page="/Share/header.jsp" />

<div class="ds-container">
    <div class="ds-title">XÁC NHẬN CHỮ KÝ ĐIỆN TỬ</div>

    <c:if test="${not empty message}">
        <div style="text-align: center; padding: 12px; margin-bottom: 20px; font-weight: bold; border-radius: 4px;
                background-color: ${isError ? '#f2dede' : '#dff0d8'};
                color: ${isError ? '#a94442' : '#3c763d'};
                border: 1px solid ${isError ? '#ebccd1' : '#d6e9c6'};">
                ${message}
        </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/sign-process" method="POST" enctype="multipart/form-data">
        <input type="hidden" name="orderId" value="${order.id}">
        <div class="ds-layout">
            <%-- QUY TRÌNH KÝ SỐ --%>
            <div class="ds-card ds-left">
                <div class="ds-section-title">Quy trình ký điện tử</div>
                <div>
                    <label class="ds-step-label">Bước 1: Tải và kiểm tra tệp dữ liệu đơn hàng</label>
                    <p class="ds-text-muted">Tải xuống tài liệu PDF/XML của đơn hàng để thực hiện ký số bằng phần mềm ký offline của bạn.</p>
                    <button type="button" class="ds-btn-outline" onclick="window.location.href='${pageContext.request.contextPath}/DownloadOrderController?orderId=${order.id}'">
                        &#11123; Download đơn hàng cần ký
                    </button>
                </div>
                <hr class="ds-divider">

                <div>
                    <label class="ds-step-label">Bước 2: Tải lên Chữ ký / Tệp đã ký</label>
                    <input type="file" class="ds-form-control" name="signatureFile" accept=".sig" required>
                    <p style="font-size: 12px; color: #d9534f; margin-top: 8px; font-weight: 500;">
                        * Chỉ tải lên file chứa chữ ký điện tử (.sig).
                    </p>
                </div>
            </div>

            <%-- BÊN PHẢI: TÓM TẮT ĐƠN HÀNG ĐÃ CHỌN --%>
            <div class="ds-card ds-right">
                <div class="ds-section-title">Tóm tắt đơn hàng</div>
                <div style="max-height: 350px; overflow-y: auto; padding-right: 5px;">
                    <c:choose>
                        <c:when test="${not empty order.items }">
                            <c:forEach var="item" items="${order.items}">
                                <div class="ds-product-item">
                                    <img src="getImage?path=${item.variant.getTopImage()}" alt="${item.product.name}" class="ds-product-img" onerror="this.src='https://via.placeholder.com/70';">
                                    <div class="ds-product-detail">
                                        <span class="ds-product-name">${item.product.name}</span>
                                        <span class="ds-product-meta">Màu: ${item.variant.color} | Size: ${item.variant.size}</span>
                                        <div style="display: flex; justify-content: space-between; margin-top: 8px;">
                                            <span style="font-size: 14px; color: #555;">
                                                <fmt:formatNumber value="${item.price}" type="number" pattern="#,##0"/> đ
                                            </span>
                                            <span style="font-size: 14px; font-weight: bold; color: #d9534f;">SL: ${item.quantity}</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <p style="color: #888; font-size: 14px; text-align: center;">Không có sản phẩm nào được chọn để thanh toán.</p>
                        </c:otherwise>
                    </c:choose>
                </div>

                <hr class="ds-solid-divider">

                <div class="ds-summary-row">
                    <span>Người nhận:</span>
                    <span style="font-weight: 600; color: #333;">${user.accountName}</span>
                </div>
                <div class="ds-summary-row">
                    <span>Số điện thoại:</span>
                    <span style="font-weight: 600; color: #333;">${user.phoneNumber}</span>
                </div>
                <div class="ds-summary-row">
                    <span>Email:</span>
                    <span style="font-weight: 600; color: #333;">${user.accountEmail}</span>
                </div>

                <div class="ds-total-row">
                    <span>Thành Tiền:</span>
                    <span class="ds-total-price">
                        <fmt:formatNumber value="${order.price}" type="number" pattern="#,##0"/> VNĐ
                    </span>
                </div>

                <button type="submit" class="ds-btn-primary">
                    XÁC THỰC KÝ NHẬN
                </button>
            </div>
        </div>
    </form>
</div>

<jsp:include page="/Share/footer.jsp" />
</body>
</html>