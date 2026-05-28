<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Quản lý ${groupTypeName}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageProductGroup.css">
</head>
<body>
<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />
    <div class="ProductGroupManagerUI">

        <!-- TOP ROW - HEADER SECTION -->
        <div class="topRow">
            <div class="topBox">
                <div class="leftBox">
                    <p class="BoxTitle">Quản lý ${groupTypeName}</p>
                    <div class="NumberContainer">
                        <span class="TotalDisplay">${groups.size()}</span>
                        <span class="UnitValue">${groupTypeName}</span>
                    </div>
                </div>
                <a href="addProductGroup?type=${groupType}" class="btn-add-top">
                    <i class="fas fa-plus"></i> Thêm ${groupTypeName}
                </a>
            </div>
        </div>

        <!-- BOTTOM BOX - LIST SECTION -->
        <div class="bottomBox">
            <h2 class="BoxTitle">Danh sách ${groupTypeName}</h2>
            <a href="addProductGroup?type=${groupType}" class="DetailPageLink">
                Thêm ${groupTypeName} mới
            </a>

            <c:choose>
            <c:when test="${empty groups}">
                <div class="no-data">
                    <i class="fas fa-folder-open"></i>
                    <p>Chưa có ${groupTypeName} nào</p>
                </div>
            </c:when>
            <c:otherwise>
            <div class="GroupsDisplay">
                <c:forEach var="group" items="${groups}">
                <div class="GroupItem">
                    <form action="updateProductGroup" method="post" class="group-form" enctype="multipart/form-data">
                        <input type="hidden" name="groupType" value="${groupType}">
                        <input type="hidden" name="id" value="${group.id}">

                        <!-- Group Header -->
                        <div class="GroupHeader">
                            <span class="GroupID">#${group.id}</span>
                            <span class="GroupOrder">
                                <i class="fas fa-sort"></i> Thứ tự:
                                <input type="number" name="displayOrder" class="order-input"
                                       value="${group.displayOrder}" min="0">
                            </span>
                        </div>

                        <!-- Group Images -->
                        <div class="GroupImages">
                            <div class="image-box">
                                <c:choose>
                                    <c:when test="${not empty group.thumbnailUrl}">
                                        <input type="hidden" name="oldThumbnailUrl" value="${group.thumbnailUrl}">
                                        <img src="getImage?path=${group.thumbnailUrl}" alt="Thumbnail" class="preview-img">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">
                                            <i class="fas fa-image"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <span class="image-label">Thumbnail</span>
                            </div>
                            <div class="image-box">
                                <c:choose>
                                    <c:when test="${not empty group.imageUrl}">
                                        <input type="hidden" name="oldImageUrl" value="${group.imageUrl}">
                                        <img src="getImage?path=${group.imageUrl}" alt="Main" class="preview-img">
                                    </c:when>
                                    <c:otherwise>
                                        <div class="no-image">
                                            <i class="fas fa-image"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                                <span class="image-label">Ảnh chính</span>
                            </div>
                        </div>

                        <!-- Group Details -->
                        <div class="GroupDetails">
                            <div class="detail-row">
                                <label>Tên ${groupTypeName}</label>
                                <input type="text" name="name" class="form-input"
                                       value="${group.name}" required>
                            </div>
                            <div class="detail-row">
                                <label>Thumbnail</label>
                                <input type="file"
                                       name="thumbnailImage"
                                       class="form-input"
                                       accept="image/*"
                                       onchange="previewImage(this, 'thumbnailPreview')">
                            </div>

                            <div class="detail-row">
                                <label>Ảnh chính</label>
                                <input type="file"
                                       name="mainImage"
                                       class="form-input"
                                       accept="image/*"
                                       onchange="previewImage(this, 'mainImagePreview')">
                            </div>

                        </div>

                        <!-- Group Actions -->
                        <div class="GroupActions">
                            <button type="submit" class="btn-action btn-update">
                                <i class="fas fa-save"></i> Lưu
                            </button>

                    </form>

                    <form action="deleteProductGroup" method="post" class="delete-form"
                          onsubmit="return confirm('Bạn có chắc muốn xóa ${groupTypeName} này?');">
                        <input type="hidden" name="groupType" value="${groupType}">
                        <input type="hidden" name="id" value="${group.id}">
                        <button type="submit" class="btn-action btn-delete">
                            <i class="fas fa-trash"></i> Xóa
                        </button>
                    </form>
                </div>
            </div>
            </c:forEach>
        </div>
        </c:otherwise>
        </c:choose>
    </div>

</div>
</div>
</body>
</html>