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
        <form action="updateAccount" method="post" enctype="multipart/form-data">>

            <!-- Hidden stuff -->
            <input type="hidden" name="id" value="${account.account.id}">
            <input type="hidden" name="base_username" value="${base_username}" />
            <input type="hidden" name="base_mail" value="${base_mail}" />

            <div class="userDetailCard">

                <!-- Avatar -->
                <div class="userAvatarSection">
                    <img src="getImage?path=${account.account.avatarUrl}" alt="User Avatar" class="userAvatar">
                </div>

                <!-- User Info -->
                <div class="userInfoSection">

                    <div class="userField">
                        <label>User Role</label>
                        <select name="accountRole">
                            <option value="USER"
                            ${account.account.role == 'USER' ? 'selected' : ''}>
                                USER
                            </option>

                            <option value="ADMIN"
                            ${account.account.role == 'ADMIN' ? 'selected' : ''}>
                                ADMIN
                            </option>

                        </select>
                    </div>

                    <div class="userField">
                        <label>Account Name</label>
                        <input type="text" name = "accountName" value="${account.account.accountName}">
                    </div>

                    <div class="userField">
                        <label>User Name</label>
                        <input type="text"
                               name="username"
                               value="${account.account.username}">
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
                               value="${account.account.phoneNumber}">
                    </div>

                    <div class="userField">
                        <label>Email</label>
                        <input type="email"
                               name="accountEmail"
                               value="${account.account.accountEmail}">
                    </div>

                    <div class="userField">
                        <label>Account Status</label>
                        <select name="accountStatus">
                            <option value="1"
                            ${account.account.accountStatus == 1 ? "selected" : ""}>
                                Active
                            </option>
                            <option value="0"
                            ${account.account.accountStatus == 0 ? "selected" : ""}>
                                Inactive
                            </option>
                        </select>
                    </div>
                    <input type="hidden"
                           name="oldAvatarUrl"
                           value="${account.account.avatarUrl}">
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
