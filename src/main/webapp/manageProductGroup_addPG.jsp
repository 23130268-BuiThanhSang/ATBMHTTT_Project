<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm ${groupTypeName}</title>
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
                    <p class="BoxTitle">Thêm ${groupTypeName} mới</p>
                    <div class="NumberContainer">
                        <span class="TotalDisplay"><i class="fas fa-plus-circle"></i></span>
                        <span class="UnitValue">Tạo mới</span>
                    </div>
                </div>
                <a href="${groupType == 'BRAND' ? 'manageBrand' : (groupType == 'COLLECTION' ? 'manageCollection' : 'manageSport')}" class="btn-back-top">
                    <i class="fas fa-arrow-left"></i> Quay lại
                </a>
            </div>
        </div>

        <!-- BOTTOM BOX - ADD FORM -->
        <div class="bottomBox">
            <h2 class="BoxTitle">Thông tin ${groupTypeName}</h2>
            <a href="${groupType == 'BRAND' ? 'manageBrand' : (groupType == 'COLLECTION' ? 'manageCollection' : 'manageSport')}" class="DetailPageLink">
                Xem danh sách ${groupTypeName}
            </a>

            <div class="AddFormContainer">
                <form action="addProductGroup" method="post" class="add-form" enctype="multipart/form-data">
                    <input type="hidden" name="groupType" value="${groupType}">

                    <div class="form-grid">
                        <!-- Left Column - Form Fields -->
                        <div class="form-column">
                            <div class="form-group">
                                <label for="name">
                                    <i class="fas fa-tag"></i> Tên ${groupTypeName}
                                    <span class="required">*</span>
                                </label>
                                <input type="text" id="name" name="name" class="form-input"
                                       placeholder="Nhập tên ${groupTypeName}" required>
                            </div>

                            <div class="form-group">
                                <label for="thumbnailUrl">
                                    <i class="fas fa-image"></i>Thumbnail
                                </label>
                                <input type="file"
                                       name="thumbnailImage"
                                       class="form-input"
                                       accept="image/*"
                                       onchange="previewImage(this, 'thumbnailPreview')">
                            </div>

                            <div class="form-group">
                                <label for="imageUrl">
                                    <i class="fas fa-photo-video"></i>Ảnh Chính
                                </label>
                                <input type="file"
                                       name="mainImage"
                                       class="form-input"
                                       accept="image/*"
                                       onchange="previewImage(this, 'mainImagePreview')">
                            </div>

                            <div class="form-group">
                                <label for="displayOrder">
                                    <i class="fas fa-sort-numeric-up"></i> Thứ tự hiển thị
                                </label>
                                <input type="number" id="displayOrder" name="displayOrder" class="form-input"
                                       placeholder="1" min="0" value="1">
                            </div>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn-submit">
                            <i class="fas fa-plus"></i> Thêm ${groupTypeName}
                        </button>
                        <button type="reset" class="btn-reset" onclick="resetPreviews()">
                            <i class="fas fa-redo"></i> Đặt lại
                        </button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>
</body>
</html>