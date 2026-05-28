<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/StyleForProductCard.css">
</head>
<body>
<jsp:include page="/Share/header.jsp" />

<div class="ProductContainer">
    <aside class="FilterSidebar">
        <div class="FilterHeader">
            <i class="fa-solid fa-filter"></i> BỘ LỌC TÌM KIẾM
        </div>
        <form method="get" action="listNewProduct" id="filterForm" class="FilterContent">
            <!-- DANH MỤC -->
            <details class="FilterGroup" open>
                <summary>DANH MỤC</summary>
                <div class="FilterBody">
                    <label class="CustomRadio">
                        <input type="radio" name="productType" value="1"
                               <c:if test="${productType == 1}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Dụng cụ gym</span>
                    </label>

                    <label class="CustomRadio">
                        <input type="radio" name="productType" value="2"
                               <c:if test="${productType == 2}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Phụ kiện</span>
                    </label>

                    <label class="CustomRadio">
                        <input type="radio" name="productType" value="3"
                               <c:if test="${productType == 3}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Đồ thể thao</span>
                    </label>

                    <label class="CustomRadio">
                        <input type="radio" name="productType" value="4"
                               <c:if test="${productType == 4}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">giày</span>
                    </label>
                </div>
            </details>
            <!-- GIỚI TÍNH -->
            <details class="FilterGroup" open>
                <summary>GIỚI TÍNH</summary>
                <div class="FilterBody">
                    <label class="CustomRadio">
                        <input type="radio" name="gender" value="Nam" <c:if test="${gender eq 'Nam'}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Nam</span>
                    </label>
                    <label class="CustomRadio">
                        <input type="radio" name="gender" value="Nữ" <c:if test="${gender eq 'Nữ'}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Nữ</span>
                    </label>
                    <label class="CustomRadio">
                        <input type="radio" name="gender" value="Khác" <c:if test="${gender eq 'Khác'}">checked</c:if>>
                        <span class="RadioDot"></span>
                        <span class="LabelText">Khác</span>
                    </label>
                </div>
            </details>
            <!-- MÀU SẮC -->
            <details class="FilterGroup" open>
                <summary>MÀU SẮC</summary>
                <div class="FilterBody ColorGrid">
                    <!-- Tím -->
                    <label class="ColorSwatch" title="Tím">
                        <input type="radio" name="color" value="Tím"
                               <c:if test="${color eq 'Tím'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#800080"></span>
                    </label>

                    <!-- Nâu -->
                    <label class="ColorSwatch" title="Nâu">
                        <input type="radio" name="color" value="Nâu"
                               <c:if test="${color eq 'Nâu'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#8b4513"></span>
                    </label>

                    <!-- Đen -->
                    <label class="ColorSwatch" title="Đen">
                        <input type="radio" name="color" value="Đen"
                               <c:if test="${color eq 'Đen'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#000"></span>
                    </label>

                    <!-- Trắng -->
                    <label class="ColorSwatch" title="Trắng">
                        <input type="radio" name="color" value="Trắng"
                               <c:if test="${color eq 'Trắng'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#fff; border:1px solid #ddd"></span>
                    </label>

                    <!-- Bạc -->
                    <label class="ColorSwatch" title="Bạc">
                        <input type="radio" name="color" value="Bạc"
                               <c:if test="${color eq 'Bạc'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#c0c0c0"></span>
                    </label>

                    <!-- Hồng -->
                    <label class="ColorSwatch" title="Hồng">
                        <input type="radio" name="color" value="Hồng"
                               <c:if test="${color eq 'Hồng'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#ff69b4"></span>
                    </label>

                    <!-- Xanh Navy -->
                    <label class="ColorSwatch" title="Xanh Navy">
                        <input type="radio" name="color" value="Xanh Navy"
                               <c:if test="${color eq 'Xanh Navy'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#001f3f"></span>
                    </label>

                    <!-- Xanh Dương -->
                    <label class="ColorSwatch" title="Xanh Dương">
                        <input type="radio" name="color" value="Xanh Dương"
                               <c:if test="${color eq 'Xanh Dương'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#007bff"></span>
                    </label>

                    <!-- Xanh Lục -->
                    <label class="ColorSwatch" title="Xanh Lục">
                        <input type="radio" name="color" value="Xanh Lục"
                               <c:if test="${color eq 'Xanh Lục'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#2ecc71"></span>
                    </label>

                    <!-- Vàng -->
                    <label class="ColorSwatch" title="Vàng">
                        <input type="radio" name="color" value="Vàng"
                               <c:if test="${color eq 'Vàng'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#f5a623"></span>
                    </label>

                    <!-- Đỏ -->
                    <label class="ColorSwatch" title="Đỏ">
                        <input type="radio" name="color" value="Đỏ"
                               <c:if test="${color eq 'Đỏ'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#d0021b"></span>
                    </label>

                    <!-- Cam -->
                    <label class="ColorSwatch" title="Cam">
                        <input type="radio" name="color" value="Cam"
                               <c:if test="${color eq 'Cam'}">checked</c:if>>
                        <span class="SwatchDot" style="background:#ff851b"></span>
                    </label>

                </div>
            </details>
            <!-- THƯƠNG HIỆU -->
            <details class="FilterGroup" open>
                <summary>Thương hiệu</summary>
                <div class="FilterBody">
                    <c:forEach var="b" items="${brands}">
                        <label class="CustomRadio">
                            <input type="radio"
                                   name="brandId"
                                   value="${b.id}"
                                   <c:if test="${brandId == b.id}">checked</c:if>>
                            <span class="RadioDot"></span>
                            <span class="LabelText">${b.name}</span>
                        </label>
                    </c:forEach>
                </div>

            </details>
            <!-- Collection -->
            <details class="FilterGroup" open>
                <summary>Bộ sưu tập</summary>
                <div class="FilterBody">
                    <c:forEach var="c" items="${collections}">
                        <label class="CustomRadio">
                            <input type="radio"
                                   name="collectionId"
                                   value="${c.id}"
                                   <c:if test="${collectionId == c.id}">checked</c:if>>
                            <span class="RadioDot"></span>
                            <span class="LabelText">${c.name}</span>
                        </label>
                    </c:forEach>
                </div>

            </details>
            <!-- KÍCH CỠ -->
            <c:if test="${not empty sizeOptions}">
                <details class="FilterGroup">
                    <summary>KÍCH CỠ</summary>
                    <div class="FilterBody SizeGrid">
                        <c:forEach var="s" items="${sizeOptions}">
                            <label class="SizeBox">
                                <input type="radio" name="size" value="${s.value}"
                                       <c:if test="${size == s.value}">checked</c:if>>
                                <span>${s.label}</span>
                            </label>
                        </c:forEach>
                    </div>
                </details>
            </c:if>

            <input type="hidden" name="keyword" value="${keyword}">

        </form>
    </aside>
    <main class="ProductMainArea">
        <div class="ProductToolbar">
            <form method="get" action="listNewProduct" id="searchForm">
                <div class="SearchWrapper">
                    <i class="fa fa-search"></i>
                    <input type="text" name="keyword"
                           placeholder="Tìm kiếm sản phẩm theo tên..."
                           value="${keyword}">
                </div>

                <%--                giữ nguyên bộ lọc khi tìm kiếm--%>
                <input type="hidden" name="gender" value="${gender}">
                <input type="hidden" name="color" value="${color}">
                <input type="hidden" name="brandId" value="${brandId}">
                <input type="hidden" name="size" value="${size}">
                <input type="hidden" name="productType" value="${productType}">
                <input type="hidden" name="collectionId" value="${collectionId}">

            </form>


            <div class="SortWrapper">
                <span class="ResultCount">Hiển thị <b>${products.size()}</b> sản phẩm</span>
            </div>
        </div>

        <div class="ProductGrid">
            <c:forEach var="p" items="${products}">
                <div class="ProductCard">
                    <div class="ProductImage">
                        <img src="${p.imageUrl}" alt="${p.name}">
                        <c:if test="${p.isNewProduct}">
                            <div class="ProductTag LeftTag">
                                <div class="NewBadge">NEW</div>
                            </div>
                        </c:if>
                        <c:if test="${ p.discountPercent >0 }">
                            <div class="ProductTag">
                                <div class="Discount">${p.discountPercent}%</div>
                            </div>
                        </c:if>
                    </div>
                    <div class="ProductInfo">
                        <span class="BrandProduct">${p.brandName}</span>
                        <p class="ProductName"><a href="ProductMainPage?id=${p.id}">${p.name}</a></p>
                        <div class="Rating">
                            <c:forEach begin="1" end="${p.fullStars}">
                                <i class="fa-solid fa-star"></i>
                            </c:forEach>

                            <c:if test="${p.hasHalfStar}">
                                <i class="fa-solid fa-star-half-stroke"></i>
                            </c:if>

                            <c:forEach begin="1" end="${5 - p.fullStars - (p.hasHalfStar ? 1 : 0)}">
                                <i class="fa-regular fa-star"></i>
                            </c:forEach>
                            <span class="ReviewCount">
                    ${p.reviewCount} đánh giá
                </span>
                        </div>
                        <div class="Price">
                            <span class="NewPrice">
                                <fmt:formatNumber value="${p.price}" type="number" groupingUsed="true"/> VNĐ
                            </span>
                            <c:if test="${ p.oldPrice != null }">
                                <span class="OldPrice">
                                    <fmt:formatNumber value="${p.oldPrice}" type="number" groupingUsed="true"/> VNĐ
                                </span>
                            </c:if>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <%-- Tạo chuỗi filterParams để giữ filter khi chuyển trang --%>
        <c:set var="filterParams" value="" />
        <c:if test="${not empty keyword}">
            <c:set var="filterParams" value="${filterParams}&keyword=${keyword}" />
        </c:if>
        <c:if test="${not empty gender}">
            <c:set var="filterParams" value="${filterParams}&gender=${gender}" />
        </c:if>
        <c:if test="${not empty color}">
            <c:set var="filterParams" value="${filterParams}&color=${color}" />
        </c:if>
        <c:if test="${not empty brandId}">
            <c:set var="filterParams" value="${filterParams}&brandId=${brandId}" />
        </c:if>
        <c:if test="${not empty size}">
            <c:set var="filterParams" value="${filterParams}&size=${size}" />
        </c:if>
        <c:if test="${not empty productType}">
            <c:set var="filterParams" value="${filterParams}&productType=${productType}" />
        </c:if>
        <c:if test="${not empty collectionId}">
            <c:set var="filterParams" value="${filterParams}&collectionId=${collectionId}" />
        </c:if>
        <div class="Pagination">
            <c:forEach begin="1" end="${totalPages}" var="i">
                <a href="?page=${i}${filterParams}" class="PageNum${i == currentPage ? ' Active' : ''}">${i}</a>
            </c:forEach>
        </div>
    </main>
</div>

<jsp:include page="/Share/footer.jsp" />
<script src="JS/Notification.js"></script>
<script src="JS/JSForListProduct.js"></script>
</body>
</html>