<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/AccountInfo.css?v=<%=System.currentTimeMillis()%>">

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
            <div class="NameOptionBold active">
                <i class="fa-solid fa-clipboard-user"></i>
                <div class="NameOption">Cập nhật hồ sơ</div>
            </div>
            <a href="${pageContext.request.contextPath}/change-password" class="OptionIcon">
                <i class="fa-solid fa-lock"></i>
                <div class="NameOption">Đổi mật khẩu</div>
            </a>
            <a href="${pageContext.request.contextPath}/order-history" class="OptionIcon">
                <i class="fa-solid fa-cart-arrow-down"></i>
                <div class="NameOptionBoldText">Lịch sử mua hàng</div>
            </a>
            <div class="SignOut">
                <a href="${pageContext.request.contextPath}/Logout" class="OptionSignOut">Đăng xuất</a>
            </div>
        </div>
    </aside>
    <div class="addressInfo">
        <div id="addressTitle">Cập nhật hồ sơ</div>
            <div class="addressList">
                <form action="${pageContext.request.contextPath}/AccountUpdateProfile" method="post">
                    <div class="adr">
                        <div>Tên tài khoản</div>
                        <input type="text" name="accountName" value="${sessionScope.auth.accountName}">
                    </div>
                    <div class="adr">
                        <div>Avatar</div>
                        <input type="text" name="avatarUrl" value="${sessionScope.auth.avatarUrl}">
                    </div>
                    <div class="adr">
                        <div>Số điện thoại</div>
                        <input type="text" name="phone" value="${sessionScope.auth.phoneNumber}">
                    </div>
                    <div class="adr">
                        <div class="public-key-group">
                            <div class="public-key-label">Public Key</div>
                            <div class="public-key-row">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.auth.publicKey}">
                                        <c:set var="rawKey" value="${sessionScope.auth.publicKey}"/>
                                        <c:set var="keyLen" value="${rawKey.length()}"/>

                                        <input type="text" id="publicKeyPath" name="publicKey" readonly
                                               value="${rawKey.substring(0, 3)}***${rawKey.substring(keyLen - 3, keyLen)}">
                                    </c:when>
                                    <c:otherwise>
                                        <input type="text" id="publicKeyPath" name="publicKey" placeholder="Chưa tải lên Public Key" readonly value="">
                                    </c:otherwise>
                                </c:choose>
                                <input type="file" id="filePublicKey" accept=".key,.pub,.pem" style="display: none;">
                                <button type="button" class="btn-upload-key" onclick="document.getElementById('filePublicKey').click()">
                                    <i class="fa-solid fa-upload"></i> Tải Public Key
                                </button>
                                <button type="button" id="btnReportLostKey" class="btn-lost-key">Báo mất key</button>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="margin-top: 15px;">
                        <div class="download-tool-container">
                            <span class="download-label">Nếu bạn chưa có bất cứ Key nào, hãy tải Sign Tool để tạo cặp khóa:</span>

                            <a href="${pageContext.request.contextPath}/Downloads/SignTool.zip" download="SignTool.zip" style="text-decoration: none;">
                                <button type="button" class="btn-download-tool">
                                    <i class="fa fa-download"></i> Tải về SignTool (.exe)
                                </button>
                            </a>
                        </div>
                    </div>
                    <div id="jsSuccessMsg" class="msg-success" style="display: none;"></div>
                    <div id="jsErrorMsg" class="msg-error" style="display: none;"></div>
                    <c:if test="${not empty success}">
                        <div class="msg-success">${success}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="msg-error">${error}</div>
                    </c:if>

                    <button type="submit" class="addressButton">Lưu thông tin</button>
                </form>
            </div>
    </div>
    <div id="otpModal" class="otp-modal-container" style="display: none;">
        <div class="otp-modal-content">
            <h2>Nhập OTP Xác nhận</h2>
            <p>Mã OTP 6 chữ số đã được gửi qua email của bạn</p>

            <input type="text" id="modalOtpInput" maxlength="6" placeholder="nhập otp 6 chữ số gửi qua mail">

            <div class="otp-modal-actions">
                <button type="button" id="btnSubmitOtp">Tiếp tục</button>
                <button type="button" id="btnCancelOtp" onclick="document.getElementById('otpModal').style.display='none'">Hủy bỏ</button>
            </div>
        </div>
    </div>

    <div id="lostKeyConfirmModal" class="otp-modal-container" style="display: none;">
        <div class="otp-modal-content">
            <h2>Xác nhận báo mất Public Key</h2>
            <p>Xác nhận báo mất khóa? Hệ thống sẽ gửi mã OTP xác nhận về Email của bạn.</p>

            <div class="otp-modal-actions" style="margin-top: 20px;">
                <button type="button" id="btnLostKeyConfirmYes" style="background-color: #007bff;">Xác nhận</button>
                <button type="button" id="btnLostKeyConfirmNo" style="background-color: #555;">Hủy bỏ</button>
            </div>
        </div>
    </div>
</main>

<jsp:include page="/Share/footer.jsp" />
<script>
    window.contextPath = '${pageContext.request.contextPath}';
</script>
<script src="JS/Notification.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
