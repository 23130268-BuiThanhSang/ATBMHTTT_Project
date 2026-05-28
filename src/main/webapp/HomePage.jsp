<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="root" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="CSS/Style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/StyleForProductCard.css">

</head>
<body id="HomePageBody">
<jsp:include page="/Share/header.jsp"/>
<div class="banner">
    <div class="BannerSlides" id="BannerSlides">
        <c:forEach var="banner" items="${banners}">
            <a href="${banner.targetUrl}">
                <img src="${banner.imageUrl}" alt="Banner Image"/>
            </a>
        </c:forEach>
    </div>
    <button class="nav-btn prev" id="prevBtn"> <<</button>
    <button class="nav-btn next" id="nextBtn"> >></button>
</div>
<div class="ProductCategories">
    DANH MỤC SẢN PHẨM
    <div class="ProductCategoriesButtonContainer">
        <a href="${root}/ListAllProduct?productType=1" class="ProductCategoriesButton">
            Dụng Cụ Tập Gym
            <div class="inner-square">
                <img src="lgImg/pngimg.com - gym_equipment_PNG144.png"/>
            </div>
        </a>
        <a href="${root}/ListAllProduct?productType=3" class="ProductCategoriesButton">
            Đồ Thể Thao
            <div class="inner-square">
                <img src="lgImg/Sports-Wear-PNG-Photos.png"/>
            </div>
        </a>
        <a href="${root}/ListAllProduct?productType=2" class="ProductCategoriesButton">
            Phụ Kiện
            <div class="inner-square">
                <img src="lgImg/phu-kien-Photoroom.png"/>
            </div>
        </a>
    </div>
</div>
<hr>
<!-- hiển thị các sản phẩm nổi bât-->
<div class="CategoriesShowcase">
    <div class="Hotdeal">
        <h2 class="HotdealTitle">ƯU ĐÃI SẬP SÀN</h2>
        <a href="${root}/ListProductHotDeal?hotDealId=${mainHotDeal.id}">
            <div Class="CategoriesShowcaseHotDealSpecial">
                <img src="${mainHotDeal.mainImageUrl}" alt="Hotdeal" class="HotDealSpecial">
            </div>
        </a>

        <div Class="CategoriesShowcaseHotDealNormal">
            <c:forEach var="hotDeal" items="${subHotDeals}">
                <a href="${root}/ListProductHotDeal?hotDealId=${hotDeal.id}">
                    <img src="${hotDeal.mainImageUrl}" alt="hotdeal1" class="HotDeal1">
                </a>
            </c:forEach>
        </div>
    </div>
    <hr>
    <div class="HotdealProduct">
        <div class="ProductSection">
            <div class="HeaderListProductTitle">
                <h2 class="ProductDealTitle">
                    ƯU ĐÃI NỔI BẬT
                </h2>
                <button class="SubProductDealButton">
                    XEM TẤT CẢ
                </button>
            </div>
            <div class="ProductSpecialContainer">
                <c:forEach var="p" items="${hotDealProductCards}">
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
        </div>
        <hr>
        <div class="ProductSection">
            <div class="HeaderListProductTitle">
                <h2 class="ProductDealTitle">
                    SẢN PHẨM MỚI
                </h2>
                <button class="SubProductDealButton">
                    XEM TẤT CẢ
                </button>
            </div>
            <div class="ProductSpecialContainer">
                <!-- đây chính là sản phẩm -->
                <c:forEach var="p" items="${ProductCardsNewest}">
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
        </div>
    </div>
</div>


<div class="SubCategories">
    <hr>
    <div class="SpecBrand">
        <div class=SubCategoriesTitleContainer>THƯƠNG HIỆU NỔI BẬT
        </div>
        <div class="SpecBrandButtonContainer">
            <c:forEach var="brand" items="${brands}">
                <a class="SpecBrandButton" href="${root}/ListAllProduct?brandId=${brand.id}">
                    <img src="${brand.thumbnailUrl}">
                </a>
            </c:forEach>
        </div>
    </div>
    <hr>
    <div class="SpecCollection">
        <div class="SubCategoriesTitleContainer">BỘ SƯU TẬP NỔI BẬT
        </div>
        <div class="SpecCollectionButtonContainer">
            <c:forEach var="collection" items="${collections}">
                <a class="SpecCollectButton" href="${root}/ListAllProduct?collectionId=${collection.id}">
                    <img src="${collection.thumbnailUrl}"/>
                </a>
            </c:forEach>

        </div>
    </div>
    <hr>
</div>

<jsp:include page="/Share/footer.jsp"/>

<script src="JS/Notification.js"></script>
<script src="JS/Script.js"></script>
</body>
</html>