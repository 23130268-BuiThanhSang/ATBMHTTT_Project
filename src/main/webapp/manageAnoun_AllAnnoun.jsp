<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thông báo</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageAnoun.css">
</head>
<body>

<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="HighlightBox">
        <div class="manageAnoun">
            <div class="header-section">
                <h1>Thông báo</h1>
                <a href="manageAnoun.jsp" class="btn-add">
                    <i class="fas fa-plus"></i> Thêm thông báo mới
                </a>
            </div>

            <!-- ANNOUNCEMENT LIST START -->
            <div class="announcement-list">
                <c:choose>
                    <c:when test="${empty announs}">
                        <div class="no-data">
                            <i class="fas fa-bell-slash"></i>
                            <p>Chưa có thông báo nào</p>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <table class="announcement-table">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Đối tượng</th>
                                <th>Nội dung</th>
                                <th>URL đích</th>
<%--                                <th>Ngày tạo</th>--%>
                                <th>Ngày hết hạn</th>
                                <th>Trạng thái</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="announ" items="${announs}">
                                <tr class="${announ.active ? '' : 'inactive-row'}">
                                    <form action="updateAnnoun" method="post" class="inline-form">
                                        <!-- Hidden ID field -->
                                        <input type="hidden" name="id" value="${announ.id}">

                                        <td>${announ.id}</td>

                                        <td>
                                            <select name="accountStatus" class="form-select">
                                                <option value="ALL" ${announ.accountStatus.name() == 'ALL' ? 'selected' : ''}>
                                                    Tất cả
                                                </option>
                                                <option value="NOT_MEMBER" ${announ.accountStatus.name() == 'NOT_MEMBER' ? 'selected' : ''}>
                                                    Thường
                                                </option>
                                                <option value="MEMBER" ${announ.accountStatus.name() == 'MEMBER' ? 'selected' : ''}>
                                                    Thành viên
                                                </option>
                                            </select>
                                        </td>

                                        <td>
                                            <textarea name="content" class="form-textarea" rows="2">${announ.content}</textarea>
                                        </td>

                                        <td>
                                            <input type="text" name="targetUrl" class="form-input"
                                                   value="${announ.targetUrl}" placeholder="URL (tùy chọn)">
                                        </td>

<%--                                        <td>--%>
<%--                                            <span class="date-display">${announ.createdAt}</span>--%>
<%--                                        </td>--%>

                                        <td>
                                            <input type="datetime-local" name="expiresAt" class="form-input"
                                                   value="${announ.expiresAt}">
                                        </td>

                                        <td>
                                            <label class="switch">
                                                <input type="checkbox" name="isActive" ${announ.active ? 'checked' : ''}>
                                                <span class="slider"></span>
                                            </label>
                                        </td>

                                        <td class="action-cell">
                                            <button type="submit" class="btn-action btn-update" title="Cập nhật">
                                                <i class="fas fa-save"></i>
                                            </button>
                                    </form>

                                    <!-- Separate Delete Form -->
                                    <form action="deleteAnnoun" method="post" class="inline-form"
                                          onsubmit="return confirm('Bạn có chắc muốn xóa thông báo này?');">
                                        <input type="hidden" name="id" value="${announ.id}">
                                        <button type="submit" class="btn-action btn-delete" title="Xóa">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
            <!-- ANNOUNCEMENT LIST END -->

        </div>
    </div>
</div>

</body>
</html>