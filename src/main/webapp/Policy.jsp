
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chính Sách Cửa Hàng</title>
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<jsp:include page="/Share/header.jsp" />
<div class="SupportCustomer">
    <h2 id="SupportHeader">Chính Sách Cửa Hàng</h2>

    <div class="SupportGroup">
        <!-- Duyệt danh sách chính sách -->
        <c:forEach var="policy" items="${policies}">
            <div class="SupportBox">
                <div class="SupportTitle">${policy.name}</div>
                <div class="SupportDiv">
                    <div class="SupportGuide">
                        <c:out value="${policy.content}" escapeXml="false" />
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="SupportConnect">
        <div class="spSocial">Liên hệ hỗ trợ: 23130268@st.hcmuaf.edu.vn</div>
        <div class="spSocial">Số điện thoại: 0829811312</div>
        <div class="spSocial">Zalo: 0829811312</div>
    </div>
</div>
<jsp:include page="/Share/footer.jsp" />
<script src="JS/Notification.js"></script>
</body>
</html>