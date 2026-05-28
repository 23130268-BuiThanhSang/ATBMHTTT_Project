<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageUI.css">
</head>
<body>
<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />
    <div class = "manageUI">
        <div class="HighlightBox">
            <label class="BoxTitle">Add New Banner</label>

            <form action="addBanner"
                  method="post"
                  class="banner-editor"
                  enctype="multipart/form-data">

            <!-- ===== IMAGE ===== -->
                <label>Banner Image</label>
                <div class="banner-box">
                    <label>Banner Image</label>
                    <div class="banner-box">
                        <input
                                type="file"
                                name="bannerImage"
                                accept="image/*"
                                required
                        >
                    </div>

<%--                    <button type="button" class="add-banner-id-btn">--%>
<%--                        Upload Image--%>
<%--                    </button>--%>
                </div>
                <!-- ===== TARGET URL ===== -->
                <label>Target URL</label>
                <div class="banner-box">
                    <input
                            class="banner-text-right"
                            name="target_url"
                            placeholder="https://example.com/page"
                            required
                    >
                </div>
                <!-- ===== DISPLAY ORDER + ACTIVE ===== -->
                <label>Display Settings</label>
                <div class="banner-box">
                    <label class="checkbox-inline">
                        Mức độ ưu tiên
                    </label>
                    <input
                            class="banner-text-order"
                            name="display_order"
                            type="number"
                            min="1"
                            placeholder="1"
                            required
                    >
                    <label class="checkbox-inline">
                        Kích Hoạt:
                        <input type="checkbox" name="is_active" value="1" checked>
                    </label>
                    <button type="submit" class="add-banner-id-btn">
                        Thêm Banner
                    </button>
                </div>

            </form>
        </div>

<%--        <div class ="HighlightBox">--%>
<%--            <div class="search-section">--%>
<%--                <div class="search-row">--%>
<%--                    <label for="searchType">Tra cứu bằng:</label>--%>
<%--                    <select id="searchType">--%>
<%--                        <option value="id">ID</option>--%>
<%--                        <option value="name">Name</option>--%>
<%--                    </select>--%>
<%--                </div>--%>
<%--                <div class="search-row input-row">--%>
<%--                    <input--%>
<%--                            type="text"--%>
<%--                            id="searchInput"--%>
<%--                            placeholder="Enter search value..."--%>
<%--                    >--%>
<%--                    <button id="searchButton">Dò Database</button>--%>
<%--                </div>--%>
<%--                <div id="searchResult" class="result-box"></div>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class = "HighlightBox">
            <div class="all-displaying-banner">
                <label class="BoxTitle">Banner hiện tại:</label>

                <div class="banner-row">
                    <label class="banner-label-left">Hiển thị theo dạng: URL Hình ảnh | URL Trang đích | Mức ưu tiên hiển thị</label>
                </div>
                <div class="Banner-container">
                    <c:forEach var="b" items="${banners}">
                        <div class="banner-box">
                            <a href="getImage?path=${b.imageUrl}" target="_blank" class = "banner-thumbnail">
                            <img src="getImage?path=${b.imageUrl}">
                            </a>
                        <span class="banner-text-left">
                                ${b.imageUrl}
                        </span>

                            <div class="divider"></div>

                            <span class="banner-text-right">
                                    ${b.targetUrl}
                            </span>

                            <div class="divider"></div>

                            <span class="banner-text-order">
                                    ${b.displayOrder}
                            </span>

                            <a
                                    href="page_bannerDetail?id=${b.id}"
                                    class="add-banner-id-btn"
                            >
                                Detail
                            </a>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

