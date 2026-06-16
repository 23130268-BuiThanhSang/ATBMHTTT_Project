package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderSignatureService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "DownloadOrderController", value = "/DownloadOrderController")
public class DownloadOrderController extends HttpServlet {
    private final OrderSignatureService signatureService = OrderSignatureService.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrderService orderService = new OrderService();
        Order order = orderService.getOrder(Integer.parseInt(request.getParameter("orderId")));
        String jsonContent = signatureService.buildOrderJsonForSigning(order);
        String fileName = "Order_" + order.getId() + ".json";

        /**
         * thiết lập header để trình duyệt hiểu đây là file tải về và có tên file theo định dạng đã đặt, đồng thời đảm bảo encoding UTF-8 để tránh lỗi font khi mở file JSON.
         */
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

        /**
         * thực hiện cho người dungfg tải về
         */
        try (PrintWriter writer = response.getWriter()) {
            writer.write(jsonContent);
            writer.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}