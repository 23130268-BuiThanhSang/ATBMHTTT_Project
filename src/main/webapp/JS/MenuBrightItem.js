// thực hiện đánh dấu menu hiện tại đang chọn -Bui Thanh Sang
document.addEventListener("DOMContentLoaded", function () {
    // thực hiện lấy đường dẫn hiện tại của trang kể cả khi có tham số filter
    var currentPath = window.location.pathname;

    var menuLinks = document.querySelectorAll(".MainMenuBar .GroupCenter .MenuItem > a");

    // Lấy đường dẫn gốc của website (Context Path) từ biến JSP
    var rootPath = "${root}";

    menuLinks.forEach(function (link) {
        var linkHref = link.getAttribute("href");

       // thục hiện giúp bỏ qua trang chủ
        if (linkHref === rootPath || linkHref === rootPath + "/") {
            return;
        }


        // Kiểm tra các link còn lại
        if (linkHref && linkHref !== "javascript:void(0)") {
            // So sánh đường dẫn quyết định active
            if (currentPath === link.pathname) {
                link.parentElement.classList.add("Active");
            }
        }
    });
});
