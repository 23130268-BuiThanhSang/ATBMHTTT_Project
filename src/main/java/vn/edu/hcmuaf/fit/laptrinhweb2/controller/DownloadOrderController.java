package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderSignatureService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "DownloadOrderController", value = "/DownloadOrderController")
public class DownloadOrderController extends HttpServlet {
    private final OrderSignatureService signatureService = OrderSignatureService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        /**
         * thục hiện kiểm tra nếu session chưa tồn tại hoặc chưa có dữ liệu đơn hàng (ordwer) thì trả về giò hàng
         */
        if (session == null || session.getAttribute("order") == null) {

            response.sendRedirect("Cart.jsp");
            return;
        }

        /**
         * lấy dữ liệu đơn hàng từ session được tạo từ bên trang DigitalSignatureController để tạo nội dung JSON cho file tải về.
         */
        Map<String, Object> orderData = (Map<String, Object>) session.getAttribute("order");
        String orderId = (String) orderData.get("id");


        /**
         * gọi xuống service nhằm mục đích tạo nội dung jdon cho file tải về
         * mục đích phân tách service để sau này có thể tái sử dụng cho trang admin
         */
        String fileContent = signatureService.buildOrderContentForSigning(orderData);

        /**
         * sử dụng file json thống nhất trong tool và cả web tránh xảy ra lỗi do khác định dạng giữa các công cụ tạo file json khác nhau, đồng thời dễ dàng quản lý và tái sử dụng sau này.
         */
        String filename = "DonHang_" + orderId + ".json";

        /**
         * thiết lập header để trình duyệt hiểu đây là file tải về và có tên file theo định dạng đã đặt, đồng thời đảm bảo encoding UTF-8 để tránh lỗi font khi mở file JSON.
         */
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        /**
         * thực hiện cho người dungfg tải về
         */
        try (PrintWriter writer = response.getWriter()) {
            writer.write(fileContent);
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}