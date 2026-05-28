<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Chỉnh sửa sản phẩm #${product.id}</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/manageUser.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/manageProduct.css">
    <style>
        /* Small styling for the delete image button */
        .ExistingImageContainer { position: relative; display: inline-block; margin-right: 10px; margin-bottom: 10px; }
        .ExistingImageContainer img { width: 80px; height: 80px; object-fit: cover; border-radius: 5px; border: 1px solid #ddd; }
        .BtnDeleteImg {
            position: absolute; top: -5px; right: -5px; background: red; color: white;
            border-radius: 50%; width: 20px; height: 20px; text-align: center;
            line-height: 20px; font-size: 12px; cursor: pointer; border: none;
        }
    </style>
</head>

<body>
<div class="MainUI">

    <jsp:include page="/Share/admin_leftbar.jsp"/>

    <div class="MainManagerForProduct">
        <div class="AddProductSection" style="display: block;">

            <div class="HeaderForManageProduct">
                <a href="page_allProduct" style="text-decoration: none; color: #333; margin-right: 10px;">
                    <i class="fa-solid fa-arrow-left"></i>
                </a>
                Chỉnh sửa sản phẩm: <span style="color:#667eea">${product.name}</span>
            </div>

            <form class="AddProductForm" action="updateProduct" method="post" enctype="multipart/form-data">

                <input type="hidden" name="id" value="${product.id}">

                <input type="hidden" name="deletedImageIds" id="deletedImageIds" value="">

                <input type="hidden" name="deletedVariantIds" id="deletedVariantIds" value="">

                <div class="FormRow">
                    <label>Tên sản phẩm:</label>
                    <input type="text" name="product_name" value="${product.name}" required>
                </div>

                <div class="FormRow">
                    <label>Phân loại:</label>
                    <select name="category" required>
                        <option value="1" ${product.productType.id == 1 ? 'selected' : ''}>Dụng cụ gym</option>
                        <option value="2" ${product.productType.id == 2 ? 'selected' : ''}>Phụ kiện</option>
                        <option value="3" ${product.productType.id == 3 ? 'selected' : ''}>Đồ thể thao</option>
                        <option value="4" ${product.productType.id == 4 ? 'selected' : ''}>Giày</option>
                    </select>
                </div>

                <div class="FormRow">
                    <label>Thông tin sản phẩm:</label>
                    <textarea name="product_info" rows="4">${product.productInfor}</textarea>
                </div>

                <div class="FormRow">
                    <label>Hướng dẫn bảo quản:</label>
                    <textarea name="product_care_instruction" rows="4">${product.productCareInstruction}</textarea>
                </div>

                <div class="FormRow">
                    <label>Thương hiệu <span class="required">*</span>:</label>
                    <div class="RadioContainer">
                        <c:forEach var="brand" items="${brandList}">
                            <label>
                                <input type="radio" name="brand_id" value="${brand.id}"
                                    ${product.brand.id == brand.id ? 'checked' : ''} required>
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
                                <input type="radio" name="collection_ids" value="${collection.id}"
                                    ${product.collection.id == collection.id ? 'checked' : ''}>
                                    ${collection.name}
                            </label>
                        </c:forEach>
                    </div>
                </div>

                <div class="FormRow">
                    <label>Giới tính:</label>
                    <div class="CheckboxContainer">
                        <label><input type="radio" name="genders" value="Nam" ${product.productGender == 'Nam' ? 'checked' : ''}> Nam</label>
                        <label><input type="radio" name="genders" value="Nữ" ${product.productGender == 'Nữ' ? 'checked' : ''}> Nữ</label>
                        <label><input type="radio" name="genders" value="Khác" ${product.productGender == 'Khác' ? 'checked' : ''}> Khác</label>
                    </div>
                </div>

                <div class="FormRow">
                    <label>Thông tin trả hàng:</label>
                    <textarea name="product_return_info" rows="4">${product.productReturnInfor}</textarea>
                </div>

                <div class="VariantSection">
                    <div class="VariantHeader">
                        <span>Biến thể sản phẩm</span>
                    </div>

                    <div id="VariantContainer">
                        <c:forEach var="v" items="${product.variants}" varStatus="loop">
                            <div class="VariantCard ExistingVariant" data-id="${v.id}">
                                <input type="hidden" name="variants[${loop.index}].id" value="${v.id}">

                                <div class="VariantHeader">
                                    <span>Biến thể #${loop.index + 1} (ID: ${v.id})</span>
                                </div>

                                <div class="VariantRow VariantRow-Top">
                                    <div class="VariantField">
                                        <label>Màu sắc:</label>
                                        <select name="variants[${loop.index}].color" required>
                                            <c:forTokens items="Tím,Nâu,Đen,Trắng,Bạc,Hồng,Xanh Navy,Xanh Dương,Xanh Lục,Vàng,Đỏ,Cam" delims="," var="c">
                                                <option value="${c}" ${v.color == c ? 'selected' : ''}>${c}</option>
                                            </c:forTokens>
                                        </select>
                                    </div>

                                    <div class="VariantField">
                                        <label>Kích cỡ:</label>
                                        <input type="text" name="variants[${loop.index}].size" value="${v.sizeString}" required>
                                    </div>

                                    <div class="VariantField">
                                        <label>Giá:</label>
                                        <input type="number" name="variants[${loop.index}].price" value="${v.price}" required>
                                    </div>

                                    <div class="VariantField">
                                        <label>Tồn kho:</label>
                                        <input type="number" name="variants[${loop.index}].stock" value="${v.stock}" required>
                                    </div>
                                </div>

                                <div class="VariantRow VariantRow-Bottom">
                                    <div class="VariantField" style="width: 100%;">
                                        <label>Hình ảnh hiện tại:</label>
                                        <div>
                                            <c:forEach var="img" items="${v.images}">
                                                <div class="ExistingImageContainer">
                                                    <img src="getImage?path=${img.imageUrl}">
                                                    <button type="button" class="BtnDeleteImg" onclick="markImageDelete(${img.id}, this)">×</button>
                                                </div>
                                            </c:forEach>
                                        </div>

                                        <label style="margin-top:10px;">Thêm ảnh mới (Giữ Ctrl để chọn nhiều):</label>
                                        <input type="file" class="ImageInput" name="variants[${loop.index}].newImages" multiple accept="image/*">
                                        <div class="ImagePreviewBox" style="display: flex; gap: 10px; margin-top: 10px; flex-wrap: wrap;"></div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>

                    <button type="button" id="btnAddVariant" class="AddVariantButton">
                        + Thêm biến thể mới
                    </button>
                </div>

                <button type="submit" class="SubmitAddProduct">Cập nhật sản phẩm</button>
            </form>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/JS/manageProductDetail.js"></script>
</body>
</html>