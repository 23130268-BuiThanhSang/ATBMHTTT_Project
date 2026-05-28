<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Images</title>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageImage.css">
</head>

<body>
<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />
    <div class="ImageManagerUI">
        <!-- ===== TOP BAR ===== -->
        <div class="topRow">
            <div class="topBox leftBox">
                <p class="BoxTitle">Image Manager</p>
                <c:if test="${not empty path}">
                    <a class="DetailPageLink"
                       href="${pageContext.request.contextPath}/page_manageImage">
                        ⬅ Back to root
                    </a>
                </c:if>
            </div>
        </div>

        <!-- ===== UPLOAD BOX ===== -->
        <div class="bottomBox">
            <p class="BoxTitle">Upload Image</p>
            <form action="${pageContext.request.contextPath}/uploadImage"
                  method="post"
                  enctype="multipart/form-data"
                  class="UploadForm">
                <input type="hidden" name="path" value="${path}">
                <input type="file" name="image" accept="image/*" required>
                <button type="submit">
                    <i class="fa-solid fa-upload"></i> Upload
                </button>
            </form>

            <!-- ===== IMAGE GRID ===== -->
            <div class="ImageGrid">
                <c:forEach var="f" items="${files}">
                    <c:set var="name" value="${f.name}" />
                    <c:set var="relativePath"
                           value="${empty path ? name : path.concat('/').concat(name)}" />
                    <div class="ImageItem">

                        <!-- Folder or Image -->
                        <c:choose>

                            <c:when test="${f.directory}">
                                <a class="FolderItem"
                                   href="${pageContext.request.contextPath}/page_manageImage?path=${relativePath}">
                                    <i class="fa-solid fa-folder"></i>
                                    <span>${name}</span>
                                </a>
                            </c:when>

                            <c:when test="${name.endsWith('.jpg') or
                        name.endsWith('.jpeg') or
                        name.endsWith('.png') or
                        name.endsWith('.webp')}">

                                <a href="${pageContext.request.contextPath}/getImage?path=${relativePath}"
                                   target="_blank">
                                    <img src="${pageContext.request.contextPath}/getImage?path=${relativePath}"
                                         alt="${name}">
                                </a>

                                <div class="ImageName">${name}</div>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/renameImage"
                                      class="RenameForm">

                                    <input type="hidden" name="path" value="${relativePath}">
                                    <input type="text" name="newName" placeholder="New name" required>
                                    <button type="submit">
                                        <i class="fa-solid fa-pen"></i>
                                    </button>
                                </form>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/moveImage"
                                      class="MoveForm">

                                    <input type="hidden" name="fromPath" value="${relativePath}">

                                    <select name="toPath" required>
                                        <option value="">Move to folder</option>

                                        <c:forEach var="d" items="${folders}">
                                            <option value="${d.name}">
                                                    ${d.name}
                                            </option>
                                        </c:forEach>
                                    </select>

                                    <button type="submit">
                                        <i class="fa-solid fa-arrow-right"></i>
                                    </button>
                                </form>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/deleteImage"
                                      class="DeleteForm"
                                      onsubmit="return confirm('Delete this image?');">

                                    <input type="hidden" name="path" value="${relativePath}">

                                    <button type="submit" class="DeleteBtn">
                                        <i class="fa-solid fa-trash"></i>
                                    </button>
                                </form>

                            </c:when>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
