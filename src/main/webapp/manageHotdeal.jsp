<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Hot Deal</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageHotdeal.css">
</head>
<body>
<div style="display: flex;">

    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="OrderManagerUI">

        <div class="bottomBox">
            <label class="BoxTitle">Add New Hot Deal</label>

            <form action="addHotdeal" method="post" enctype="multipart/form-data">
                <div class="FormGrid">

                    <div class="InputGroup">
                        <label>Hot Deal Name</label>
                        <input type="text" name="name" class="StyledInput" placeholder="Ex: Summer Sale" required>
                    </div>

                    <div class="InputGroup">
                        <label>Display Order</label>
                        <input type="number" name="display_order" class="StyledInput" min="1" placeholder="1" required>
                    </div>

                    <div class="InputGroup">
                        <label>Discount (%)</label>
                        <input type="number" name="discount_per" class="StyledInput" min="1" max="100" placeholder="50" required>
                    </div>

                    <div class="InputGroup">
                        <label>Thumbnail Image</label>
                        <input type="file" name="thumbnailImage" class="StyledFile" accept="image/*" required>
                    </div>

                    <div class="InputGroup">
                        <label>Main Image (Banner)</label>
                        <input type="file" name="mainImage" class="StyledFile" accept="image/*" required>
                    </div>
                </div>

                <div class="ButtonRow">
                    <button type="submit" class="ActionBtn">
                        <i class="fas fa-plus" style="margin-right: 8px;"></i> Add Hot Deal
                    </button>
                </div>
            </form>
        </div>

        <div class="bottomBox">
            <label class="BoxTitle">Current Hot Deals</label>

            <div class="OrdersDetailDisplay">
                <c:forEach var="h" items="${hotDeals}">
                    <div class="OrderItem" onclick="location.href='getHotdeal?id=${h.id}';">

                        <div class="DealImageContainer">
                            <img src="getImage?path=${h.thumbnailUrl}" class="DealImage" alt="${h.name}">
                        </div>

                        <div class="DealTitle">${h.name}</div>

                        <div class="DealInfo">
                            <span class="OrderBadge">Order: ${h.displayOrder}</span>
                            <span class="DiscountBadge">-${h.discountPercentage}%</span>
                        </div>

                        <div class="DetailLink">
                            Manage Details
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    </div>
</div>
</body>
</html>