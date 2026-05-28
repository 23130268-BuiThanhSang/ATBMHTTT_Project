<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/ProductMainPage.css?v=<%=System.currentTimeMillis()%>">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body>
<jsp:include page="/Share/header.jsp"/>
<nav class="breadcrumb">
    <a href="#">Trang chủ</a>
    <span class="separator">›</span>
    <a href="#">Trang sản phẩm</a>
    <span class="separator">›</span>
    <a href="#">${product.productType}</a>
    <span class="separator">›</span>
    <a href="#">${product.name}</a>
</nav>
<c:if test="${not empty product.variants}">
    <c:set var="defaultVariant" value="${product.variants[0]}" />
</c:if>
<c:set var="displayedColors" value="" />
<div class = "UpperPart">
    <div class="ProductPreview">
        <div class="MainImageContainer">
            <c:if test="${not empty product.variants and not empty product.variants[0].images}">
                <img src="${product.variants[0].images[0].imageUrl}"
                     id="mainImage" class="MainImage">
            </c:if>
        </div>
        <div class="ImageSlider GallerySlider">
            <c:forEach items="${product.variants}" var="variant">
                <c:forEach items="${variant.images}" var="img" varStatus="i">
                    <img src="${img.imageUrl}"
                         class="GalleryThumb ${variant.color == defaultVariant.color && i.index == 0 ? 'active' : ''}"
                         data-color="${variant.color}"
                         style="${variant.color == defaultVariant.color ? '' : 'display:none'}"
                         onclick="changeMainImage(this)">
                </c:forEach>
            </c:forEach>
        </div>

    </div>
    <div class="ProductInfo">
        <div class = "HighlightBox">
            <h1 class="ProductName">${product.name}</h1>
            <p class="ProductBrand">Thương hiệu: ${product.brand.getName()}</p>
            <p class="ProductBrand">Phân loại: ${product.productType.getName()}</p>
            <p class="ProductBrand">Giới tính: ${product.productGender}</p>
            <p class="ProductBrand">
                Màu sắc: <span id="selectedColorText">${defaultVariant.color}</span>
            </p>
            <div class="Stars" id="avgStars">
                <c:forEach begin="1" end="${fullStars}">
                    <i class="fa-solid fa-star"></i>
                </c:forEach>

                <c:if test="${hasHalfStar}">
                    <i class="fa-solid fa-star-half-stroke"></i>
                </c:if>

                <c:forEach begin="1" end="${5 - fullStars - (hasHalfStar ? 1 : 0)}">
                    <i class="fa-regular fa-star"></i>
                </c:forEach>

                <span style="margin-left:8px">(${avgRating}) sao, ${reviewCount} lượt đánh giá</span>
            </div>
        </div>
        <div class="ProductColor">
            <p class="ProductBrand">Danh sách màu:</p>
            <div class="ImageSlider ColorSlider">
                <c:set var="shownColors" value="" />

                <c:forEach items="${product.variants}" var="variant" varStatus="v">
                    <c:if test="${!shownColors.contains(variant.color)}">
                        <img src="${variant.images[0].imageUrl}"
                             class="ColorThumb ${variant.color == defaultVariant.color ? 'active' : ''}"
                             data-color="${variant.color}"
                             onclick="changeColor(this)">

                        <c:set var="shownColors" value="${shownColors}${variant.color}," />
                    </c:if>
                </c:forEach>
            </div>
        </div>

        <div class="ProductSize">
            <p class="SectionTitle">Kích Thước</p>
            <div class="SizeOptions"></div>
        </div>

        <div class="ProductSize">
            <p class="SectionTitle">Giá sản phẩm:
                <span id="unitPrice" data-price="${defaultVariant.price}">
                    ${defaultVariant.price}
                </span> VNĐ
            </p>
            <p class="SectionTitle">Tổng thành tiền:
                <span id="totalPrice">
                    ${defaultVariant.price}
                </span> VNĐ
            </p>
        </div>

        <div class="PurchaseSection">
            <div class = "HighlightBox">
                <div class="QuantityContainer">
                    <div class="QuantityBoxForProduct">
                        <label for="quantity" class="quantityText">Chọn số lượng:</label>
                        <button class="qty-minus">-</button>
                        <span class="quantity">1</span>
                        <button class="qty-plus">+</button>
                    </div>
                </div>

                <div class="ActionButtons">
                    <button class="AddToCart">THÊM VÀO GIỎ HÀNG</button>
                    <button class="BuyNow">MUA NGAY</button>
                </div>
            </div>
        </div>
    </div>

</div>

<div class="LowerPart">
    <div class="LowerHighlightBox">
        <div class="TabContainer">
            <div class="TabButtons">
                <button class="TabButton active">Thông tin sản phẩm</button>
                <button class="TabButton">Quy định đổi trả</button>
                <button class="TabButton">Hướng dẫn chăm sóc</button>
            </div>

            <div class="TabWindow">
                <div class="TabContents">
                    <div class="TabContent active">
                        ${product.productInfor}
                    </div>
                    <div class="TabContent">
                        ${product.productReturnInfor}
                    </div>
                    <div class="TabContent">
                        ${product.productCareInstruction}
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<div class="RatingSection">
    <c:if test="${isLogin}">
        <h3>ĐÁNH GIÁ CỦA BẢN THÂN</h3>

        <div class="HighlightBox">
            <div class="Reviewer">
                <img src="${sessionScope.auth.avatarUrl}" class="Avatar"/>
                <span class="username">${sessionScope.auth.username}</span>
            </div>
            <form method="post" action="${pageContext.request.contextPath}/AddReview" class="SelfReviewForm">
                <input type="hidden" name="productId" value="${product.id}" />
                <div class="Self_review">
                    <textarea name="reviewText" placeholder="Nhập đánh giá..." required></textarea>
                    <div class="ReviewActionRow">
                        <select name="rating" class="self_rating">
                            <option value="5">5 Sao</option>
                            <option value="4">4 Sao</option>
                            <option value="3">3 Sao</option>
                            <option value="2">2 Sao</option>
                            <option value="1">1 Sao</option>
                        </select>
                        <button type="submit" class="review_complete_button">
                            Hoàn Thành
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </c:if>
    <h3>ĐÁNH GIÁ CỦA MỌI NGƯỜI</h3>
    <div class="RatingSummary">
        <div class="AverageRating">
            <span class="Score">${reviewCount}</span>
            <span>Đánh giá</span>
        </div>

        <div class="Self_review">
            <form method="get" action="ProductMainPage">
                <input type="hidden" name="id" value="${product.id}" />

                <select name="rating" class="self_rating" onchange="this.form.submit()">
                    <option value="">Tất cả</option>
                    <option value="5" ${param.rating == '5' ? 'selected' : ''}>5 Sao</option>
                    <option value="4" ${param.rating == '4' ? 'selected' : ''}>4 Sao</option>
                    <option value="3" ${param.rating == '3' ? 'selected' : ''}>3 Sao</option>
                    <option value="2" ${param.rating == '2' ? 'selected' : ''}>2 Sao</option>
                    <option value="1" ${param.rating == '1' ? 'selected' : ''}>1 Sao</option>
                </select>
            </form>
        </div>
    </div>

    <div class="ReviewList" id="reviewList">
        <c:forEach items="${reviews}" var="r">
            <div class="ReviewItem">
                <div class="Reviewer">
                    <img src="${r.avatarUrl}" class="Avatar"/>
                    <span class="username">${r.userName}</span>
                    <span class="date">${r.reviewDate}</span>
                </div>

                <div class="Stars">
                    <c:forEach begin="1" end="${r.rating}">
                        <i class="fa-solid fa-star"></i>
                    </c:forEach>
                </div>
                <p>${r.reviewText}</p>
            </div>
        </c:forEach>
    </div>
</div>
<jsp:include page="/Share/footer.jsp" />
<script>
    const IS_LOGGED_IN = ${sessionScope.auth != null};
</script>
<script>
    const VARIANTS = [
        <c:forEach items="${product.variants}" var="v" varStatus="i">
        {
            id: ${v.id},
            productId: ${product.id},
            productName: "${product.name}",
            color: "${v.color}",
            size: ${v.size},
            sizeText: "${sizeMap[v.size]}",
            price: ${v.price},
            stock: ${v.stock},
            image: "${v.images[0].imageUrl}"
        }${i.last ? "" : ","}
        </c:forEach>
    ];
</script>
<script src="JS/Notification.js"></script>
<script src="${pageContext.request.contextPath}/JS/ProductMainPage.js?v=<%=System.currentTimeMillis()%>"></script>
</body>
</html>
