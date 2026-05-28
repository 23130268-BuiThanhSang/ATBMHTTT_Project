<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Banner Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageUI.css">
</head>
<body>

<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp"/>

    <div class="manageUI">

        <div class="HighlightBox">
            <label class="BoxTitle">Banner Detail</label>

            <!-- ===== IMAGE PREVIEW ===== -->
            <form action="updateBanner"
                  method="post"
                  enctype="multipart/form-data">


            <input type="hidden" name="id" value="${banner.id}">

                <!-- ===== IMAGE (READ-ONLY) ===== -->
                <label>Banner Image</label>
                <div class="banner-box">
                    <a href="getImage?path=${banner.imageUrl}" target="_blank">
                        <img src="getImage?path=${banner.imageUrl}" alt="Banner Image">
                    </a>
                    <span class="banner-text-left">
                        ${banner.imageUrl}
                    </span>
                </div>
                <label>Upload New Image (optional)</label>
                <div class="banner-box">
                    <input
                            type="file"
                            name="newBannerImage"
                            accept="image/*"
                    >
                                <span class="banner-text-left">
                    Leave empty to keep current image
                </span>
                </div>

                <!-- ===== TARGET URL ===== -->
                <label>Target URL</label>
                <div class="banner-box">
                    <input
                            class="banner-text-right"
                            name="targetUrl"
                            value="${banner.targetUrl}"
                            required
                    >
                </div>

                <!-- ===== DISPLAY ORDER ===== -->
                <label>Display Order</label>
                <div class="banner-box">
                    <input
                            class="banner-text-order"
                            type="number"
                            name="displayOrder"
                            value="${banner.displayOrder}"
                            min="1"
                            required
                    >
                </div>

                <!-- ===== ACTIVE STATUS ===== -->
                <label>Status</label>
                <div class="banner-box">
                    <label>
                        <input
                                type="checkbox"
                                name="isActive"
                                value="1"
                        ${banner.active ? "checked" : ""}
                        >
                        Active
                    </label>
                </div>

                <!-- ===== ACTIONS ===== -->
                <label>Hành động</label>
                <div class="banner-box">
                    <a href="page_manageBanner" class="add-banner-id-btn">
                        ← Back to List
                    </a>
                    <!-- UPDATE -->
                    <button
                            type="submit"
                            name="action"
                            value="update"
                            class="add-banner-id-btn"
                    >
                        Update Banner
                    </button>

                    <!-- DELETE -->
                    <button
                            type="submit"
                            name="action"
                            value="delete"
                            class="remove-banner-btn"
                            onclick="return confirm('Bạn có chắc muốn xóa banner này?');"
                    >
                        Delete Banner
                    </button>

                </div>

            </form>


        </div>

    </div>
</div>

</body>
</html>
