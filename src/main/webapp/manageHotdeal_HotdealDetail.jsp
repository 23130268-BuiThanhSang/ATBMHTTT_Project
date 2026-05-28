<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hot Deal Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageHotdeal.css">
</head>
<body>
<div style="display: flex;">

    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="OrderManagerUI">

        <div class="topRow">
            <a href="page_manageHotdeal" class="turnBack">Back to List</a>
        </div>

        <div class="bottomBox">
            <label class="BoxTitle">Update Hot Deal Details</label>

            <form action="updateHotdeal" method="post" enctype="multipart/form-data">
                <input type="hidden" name="id" value="${hotDeal.id}">

                <div class="FormGrid">
                    <div class="InputGroup">
                        <label>Name</label>
                        <input type="text" name="name" class="StyledInput" value="${hotDeal.name}" required>
                    </div>

                    <div class="InputGroup">
                        <label>Display Order</label>
                        <input type="number" name="display_order" class="StyledInput" value="${hotDeal.displayOrder}" required>
                    </div>

                    <div class="InputGroup">
                        <label>Discount (%)</label>
                        <input type="number" name="discount_per" class="StyledInput" value="${hotDeal.discountPercentage}" required>
                    </div>
                </div>

                <div style="margin-top: 30px; border-top: 1px dashed #e8ecf1; padding-top: 20px;"></div>

                <div class="FormGrid">
                    <div class="InputGroup">
                        <label>Current Thumbnail</label>
                        <img src="getImage?path=${hotDeal.thumbnailUrl}" class="CurrentImgPreview">

                        <label style="margin-top: 10px;">Change Thumbnail (Optional)</label>
                        <input type="file" name="thumbnailImage" class="StyledFile" accept="image/*">
                    </div>

                    <div class="InputGroup">
                        <label>Current Main Image</label>
                        <img src="getImage?path=${hotDeal.mainImageUrl}" class="CurrentImgPreview" style="width: 100%; height: 120px;">

                        <label style="margin-top: 10px;">Change Main Image (Optional)</label>
                        <input type="file" name="mainImage" class="StyledFile" accept="image/*">
                    </div>
                </div>

                <div class="ButtonRow" style="margin-top: 40px;">
                    <button type="submit" class="ActionBtn">
                        Save Changes
                    </button>
                </div>
            </form>

            <div style="margin-top: 40px; border-top: 2px solid #f3f4f6; padding-top: 20px;">
                <form action="deleteHotdeal" method="post" onsubmit="return confirm('Are you sure you want to delete this hot deal? This cannot be undone.');">
                    <input type="hidden" name="id" value="${hotDeal.id}">
                    <button type="submit" class="ActionBtn danger">
                        Delete Hot Deal
                    </button>
                </form>
            </div>

        </div>
    </div>
</div>
</body>
</html>