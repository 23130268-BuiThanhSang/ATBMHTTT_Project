<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Thông báo</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
    <link rel="stylesheet" href="CSS/manageUser.css">
    <link rel="stylesheet" href="CSS/manageAnoun.css">
</head>
<body>

<div class="MainUI">
    <jsp:include page="/Share/admin_leftbar.jsp" />

    <div class="HighlightBox">
        <div class="manageAnoun">
            <h1>Thông báo</h1>

            <!-- FORM START -->
            <form method="post" action="addAnnouncement">

                <div class="form-group">
                    <label for="audience">Gửi đến:</label>
                    <select id="audience" name="customer_type">
                        <option value="ALL">Toàn bộ người dùng</option>
                        <option value="NORMAL">Ko có membership</option>
                        <option value="MEMBER">Có membership</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="exp_date">Ngày hết hạn</label>
                    <div id="exp_date">
                        <input id="exp_day" name="exp_day" placeholder="Nhập ngày hết hạn">
                        <select id="month" name="exp_month">
                            <option value="">Month</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                        <input id="exp_year" name="exp_year" placeholder="Nhập năm hết hạn">
                    </div>
                </div>

                <div class="form-group">
                    <label for="content">Nội dung thông báo</label>
                    <textarea id="content" name="content" placeholder="Nhập nội dung thông báo..."></textarea>
                </div>

                <div class="form-group">
                    <label for="targetUrl">
                        URL trang đích khi người dùng nhấn thông báo (tùy chọn):
                    </label>
                    <input type="text" id="targetUrl" name="target_url"
                           placeholder="https://example.com/page" />
                </div>

                <button type="submit" class="submit-btn">Phát Thông Báo</button>

            </form>
            <!-- FORM END -->

        </div>
    </div>
</div>

</body>
</html>
