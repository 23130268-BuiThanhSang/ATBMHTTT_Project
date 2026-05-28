<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thêm sản phẩm mới</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageProduct.css">
</head>

<body>
<div class="MainUI">

    <jsp:include page="/Share/admin_leftbar.jsp"/>

    <div class="MainManagerForProduct">

        <div class="AddProductSection" style="display: block;">
            <div class="HeaderForManageProduct">Thêm Sản Phẩm Mới</div>

            <form class="AddProductForm"
                  action="addProduct"
                  method="post"
                  enctype="multipart/form-data">

                <div class="FormRow">
                    <label>Tên sản phẩm:</label>
                    <input type="text" name="product_name" required>
                </div>

                <div class="FormRow">
                    <label>Phân loại:</label>
                    <select name="category" required>
                        <option value="1">Dụng cụ gym</option>
                        <option value="2">Phụ kiện</option>
                        <option value="3">Đồ</option>
                        <option value="4" selected>Giày</option>
                    </select>
                </div>

                <div class="FormRow">
                    <label>Thông tin sản phẩm:</label>
                    <textarea name="product_info" rows="4"></textarea>
                </div>

                <div class="FormRow">
                    <label>Hướng dẫn bảo quản:</label>
                    <textarea name="product_care_instruction" rows="4"></textarea>
                </div>

                <div class="FormRow">
                    <label>Thương hiệu <span class="required">*</span>:</label>
                    <div class="RadioContainer">
                        <c:forEach var="brand" items="${brandList}">
                            <label>
                                <input type="radio" name="brand_id" value="${brand.id}" required>
                                    ${brand.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <div class="FormRow">
                    <label>Bộ sưu tập:</label>
                    <div class="CheckboxContainer">
                        <c:forEach var="collection" items="${collectionList}">
                            <label>
                                <input type="radio" name="collection_ids" value="${collection.id}">
                                    ${collection.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <div class="FormRow">
                    <label>Giới tính:</label>
                    <div class="CheckboxContainer">
                        <label><input type="radio" name="genders" value="Nam"> Nam</label>
                        <label><input type="radio" name="genders" value="Nữ"> Nữ</label>
                        <label><input type="radio" name="genders" value="Khác"> Khác</label>
                    </div>
                </div>

                <div class="FormRow">
                    <label>Thông tin trả hàng:</label>
                    <textarea name="product_return_info" rows="4"></textarea>
                </div>

                <div class="VariantSection">
                    <div class="VariantHeader">
                        <span>Biến thể sản phẩm</span>
                    </div>

                    <div id="VariantContainer">
                    </div>

                    <button type="button" id="btnAddVariant" class="AddVariantButton">
                        + Thêm biến thể
                    </button>
                </div>

                <button type="submit" class="SubmitAddProduct">
                    Thêm sản phẩm
                </button>

            </form>
        </div>
    </div>
</div>

<script src="JS/manageProductTabSw.js"></script>
</body>
</html>