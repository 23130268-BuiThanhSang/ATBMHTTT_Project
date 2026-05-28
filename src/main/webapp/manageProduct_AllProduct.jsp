<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin - Quản lý sản phẩm</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageHotdeal.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageProduct_All.css">
</head>
<body>

<div style="display: flex;">

    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="OrderManagerUI">

        <div class="AdminProductContainer">

            <aside class="AdminFilterSidebar">
                <div class="FilterHeader">
                    <i class="fa-solid fa-filter"></i> BỘ LỌC TÌM KIẾM
                </div>

                <form method="get" action="page_allProduct" id="filterForm">

                    <input type="hidden" name="keyword" value="${keyword}">

                    <details class="FilterGroup" open>
                        <summary>DANH MỤC</summary>
                        <div class="FilterBody">
                            <label class="CustomRadio">
                                <input type="radio" name="productType" value="1" <c:if test="${productType == 1}">checked</c:if>>
                                <span class="RadioDot"></span> Dụng cụ gym
                            </label>
                            <label class="CustomRadio">
                                <input type="radio" name="productType" value="2" <c:if test="${productType == 2}">checked</c:if>>
                                <span class="RadioDot"></span> Phụ kiện
                            </label>
                            <label class="CustomRadio">
                                <input type="radio" name="productType" value="3" <c:if test="${productType == 3}">checked</c:if>>
                                <span class="RadioDot"></span> Đồ thể thao
                            </label>
                            <label class="CustomRadio">
                                <input type="radio" name="productType" value="4" <c:if test="${productType == 4}">checked</c:if>>
                                <span class="RadioDot"></span> Giày
                            </label>
                        </div>
                    </details>

                    <details class="FilterGroup" open>
                        <summary>GIỚI TÍNH</summary>
                        <div class="FilterBody">
                            <label class="CustomRadio">
                                <input type="radio" name="gender" value="Nam" <c:if test="${gender eq 'Nam'}">checked</c:if>>
                                <span class="RadioDot"></span> Nam
                            </label>
                            <label class="CustomRadio">
                                <input type="radio" name="gender" value="Nữ" <c:if test="${gender eq 'Nữ'}">checked</c:if>>
                                <span class="RadioDot"></span> Nữ
                            </label>
                            <label class="CustomRadio">
                                <input type="radio" name="gender" value="Khác" <c:if test="${gender eq 'Khác'}">checked</c:if>>
                                <span class="RadioDot"></span> Khác
                            </label>
                        </div>
                    </details>

                    <details class="FilterGroup" open>
                        <summary>MÀU SẮC</summary>
                        <div class="FilterBody ColorGrid">
                            <label class="ColorSwatch" title="Tím">
                                <input type="radio" name="color" value="Tím" <c:if test="${color eq 'Tím'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#800080"></span>
                            </label>

                            <label class="ColorSwatch" title="Nâu">
                                <input type="radio" name="color" value="Nâu" <c:if test="${color eq 'Nâu'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#8b4513"></span>
                            </label>

                            <label class="ColorSwatch" title="Đen">
                                <input type="radio" name="color" value="Đen" <c:if test="${color eq 'Đen'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#000"></span>
                            </label>

                            <label class="ColorSwatch" title="Trắng">
                                <input type="radio" name="color" value="Trắng" <c:if test="${color eq 'Trắng'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#fff; border:1px solid #ddd"></span>
                            </label>

                            <label class="ColorSwatch" title="Bạc">
                                <input type="radio" name="color" value="Bạc" <c:if test="${color eq 'Bạc'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#c0c0c0"></span>
                            </label>

                            <label class="ColorSwatch" title="Hồng">
                                <input type="radio" name="color" value="Hồng" <c:if test="${color eq 'Hồng'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#ff69b4"></span>
                            </label>

                            <label class="ColorSwatch" title="Xanh Navy">
                                <input type="radio" name="color" value="Xanh Navy" <c:if test="${color eq 'Xanh Navy'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#001f3f"></span>
                            </label>

                            <label class="ColorSwatch" title="Xanh Dương">
                                <input type="radio" name="color" value="Xanh Dương" <c:if test="${color eq 'Xanh Dương'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#007bff"></span>
                            </label>

                            <label class="ColorSwatch" title="Xanh Lục">
                                <input type="radio" name="color" value="Xanh Lục" <c:if test="${color eq 'Xanh Lục'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#2ecc71"></span>
                            </label>

                            <label class="ColorSwatch" title="Vàng">
                                <input type="radio" name="color" value="Vàng" <c:if test="${color eq 'Vàng'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#f5a623"></span>
                            </label>

                            <label class="ColorSwatch" title="Đỏ">
                                <input type="radio" name="color" value="Đỏ" <c:if test="${color eq 'Đỏ'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#d0021b"></span>
                            </label>

                            <label class="ColorSwatch" title="Cam">
                                <input type="radio" name="color" value="Cam" <c:if test="${color eq 'Cam'}">checked</c:if>>
                                <span class="SwatchDot" style="background:#ff851b"></span>
                            </label>
                        </div>
                    </details>

                    <details class="FilterGroup" open>
                        <summary>THƯƠNG HIỆU</summary>
                        <div class="FilterBody">
                            <c:forEach var="b" items="${brands}">
                                <label class="CustomRadio">
                                    <input type="radio" name="brandId" value="${b.id}" <c:if test="${brandId == b.id}">checked</c:if>>
                                    <span class="RadioDot"></span> ${b.name}
                                </label>
                            </c:forEach>
                        </div>
                    </details>

                    <c:if test="${not empty sizeOptions}">
                        <details class="FilterGroup" open>
                            <summary>KÍCH CỠ</summary>
                            <div class="FilterBody SizeGrid">
                                <c:forEach var="s" items="${sizeOptions}">
                                    <label class="SizeBox">
                                        <input type="radio" name="size" value="${s.value}" <c:if test="${size == s.value}">checked</c:if>>
                                        <span>${s.label}</span>
                                    </label>
                                </c:forEach>
                            </div>
                        </details>
                    </c:if>

                    <div style="margin-top: 20px;">
                        <button type="submit" class="AdminAddBtn" style="width: 100%; justify-content: center;">Áp dụng lọc</button>
                        <a href="page_allProduct" style="display:block; text-align:center; margin-top:10px; color:#666; font-size:13px;">Xóa bộ lọc</a>
                    </div>
                </form>
            </aside>

            <main class="AdminProductMain">

                <div class="AdminToolbar">
                    <form method="get" action="page_allProduct" style="flex:1;">
                        <div class="SearchWrapper">
                            <i class="fa fa-search"></i>
                            <input type="text" name="keyword" placeholder="Tìm kiếm sản phẩm..." value="${keyword}">

                            <input type="hidden" name="gender" value="${gender}">
                            <input type="hidden" name="color" value="${color}">
                            <input type="hidden" name="brandId" value="${brandId}">
                            <input type="hidden" name="size" value="${size}">
                            <input type="hidden" name="productType" value="${productType}">
                        </div>
                    </form>

                    <a href="page_add_product" class="AdminAddBtn">
                        <i class="fa-solid fa-plus"></i> Thêm sản phẩm
                    </a>
                </div>

                <div style="margin-bottom: 15px; font-size: 14px; color: #555;">
                    Hiển thị <b>${products.size()}</b> kết quả
                </div>

                <div class="AdminProductGrid">
                    <c:forEach var="p" items="${products}">
                        <div class="AdminCard">
                            <span class="CardID">#${p.id}</span>
                            <img src="${p.imageUrl}" class="CardImage" alt="${p.name}">

                            <div class="CardInfo">
                                <div class="CardBrand">${p.brandName}</div>
                                <a href="ProductMainPage?id=${p.id}" target="_blank" class="CardName" title="${p.name}">
                                        ${p.name}
                                </a>

                                <div class="CardPrice">
                                    <fmt:formatNumber value="${p.price}" type="number"/> đ
                                </div>
                            </div>

                            <div class="CardActions">
                                <a href="getProductDetail?id=${p.id}" class="ActionBtn BtnEdit">
                                    <i class="fa-solid fa-pen"></i> Sửa
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <c:set var="filterParams" value="" />
                <c:if test="${not empty keyword}"><c:set var="filterParams" value="${filterParams}&keyword=${keyword}" /></c:if>
                <c:if test="${not empty gender}"><c:set var="filterParams" value="${filterParams}&gender=${gender}" /></c:if>
                <c:if test="${not empty color}"><c:set var="filterParams" value="${filterParams}&color=${color}" /></c:if>
                <c:if test="${not empty brandId}"><c:set var="filterParams" value="${filterParams}&brandId=${brandId}" /></c:if>
                <c:if test="${not empty size}"><c:set var="filterParams" value="${filterParams}&size=${size}" /></c:if>
                <c:if test="${not empty productType}"><c:set var="filterParams" value="${filterParams}&productType=${productType}" /></c:if>

                <div class="AdminPagination">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <a href="page_allProduct?page=${i}${filterParams}" class="PageNum ${i == currentPage ? 'Active' : ''}">
                                ${i}
                        </a>
                    </c:forEach>
                </div>

            </main>
        </div>
    </div>
</div>

<script>
    // Auto-submit filter when a radio button is clicked
    document.querySelectorAll('.AdminFilterSidebar input[type=radio]').forEach(radio => {
        radio.addEventListener('change', () => {
            document.getElementById('filterForm').submit();
        });
    });
</script>

</body>
</html>