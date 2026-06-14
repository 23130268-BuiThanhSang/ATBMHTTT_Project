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

        // Kiểm tra session và order
        if (session == null || session.getAttribute("order") == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        // Lấy dữ liệu từ session
        Map<String, Object> orderData = (Map<String, Object>) session.getAttribute("order");
        String orderId = (String) orderData.get("id");

        // Gọi Service tạo nội dung JSON
        String fileContent = signatureService.buildOrderContentForSigning(orderData);

        // ĐỔI SANG ĐUÔI .json
        String filename = "DonHang_" + orderId + ".json";

        // ĐỔI CONTENT-TYPE SANG application/json
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        // Xuất file
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