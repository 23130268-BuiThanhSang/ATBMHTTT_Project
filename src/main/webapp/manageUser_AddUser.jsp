<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Detail</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageUser_UserDetail.css">
</head>
<body>

<div class="ManagerUser">
    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="manageUserDetailUi">

        <div class="manageUserHeader">
            <h2>User Management</h2>
        </div>

        <!-- FORM START -->
        <form action="addAccount" method="post" enctype="multipart/form-data">>

            <!-- Hidden ID (important) -->
            <input type="hidden" name="id" value="${account.account.id}">

            <div class="userDetailCard">

                <!-- Avatar -->
                <div class="userAvatarSection">
                    <img src="getImage?path=base_avatar.png" alt="User Avatar" class="userAvatar">
                </div>

                <!-- User Info -->
                <div class="userInfoSection">

                    <div class="userField">
                        <label>User Role</label>
                        <select name="accountRole">
                            <option value="USER">
                                USER
                            </option>
                            <option value="ADMIN">
                                ADMIN
                            </option>
                        </select>
                    </div>

                    <div class="userField">
                        <label>Account Name</label>
                        <input type="text" name = "accountName" value="">
                    </div>

                    <div class="userField">
                        <label>User Name</label>
                        <input type="text"
                               name="username"
                               value="">
                    </div>

                    <div class="userField">
                        <label>Password</label>
                        <input type="text"
                               name="password"
                               placeholder=""
                               value="">
                    </div>

                    <div class="userField">
                        <label>Phone Number</label>
                        <input type="text"
                               name="phoneNumber"
                               value="">
                    </div>

                    <div class="userField">
                        <label>Email</label>
                        <input type="email"
                               name="accountEmail"
                               value="">
                    </div>

                    <div class="userField">
                        <label>Account Status</label>
                        <select name="accountStatus">
                            <option value="1">
                                Active
                            </option>
                            <option value="0">
                                Inactive
                            </option>
                        </select>
                    </div>

                    <div class="userField">
                        <label for="avatar">Avatar</label>
                        <input type="file"
                               id="avatar"
                               name="avatar"
                               accept="image/*">
                    </div>


                </div>
            </div>
            <c:if test="${not empty error}">
                <div class="error-message">
                        ${error}
                </div>
            </c:if>

            <div class="userActionBar">
                <button type="submit" class="btnSave">
                    <i class="fa-solid fa-floppy-disk"></i> Save Changes
                </button>

                <a href="getAllAccount" class="btnCancel">
                    <i class="fa-solid fa-xmark"></i> Cancel
                </a>
            </div>

        </form>
        <!-- FORM END -->

    </div>
</div>

</body>
</html>
