package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;

import java.io.IOException;

@WebServlet(name = "DigitalSignature", value = "/DigitalSignature")
public class DigitalSignatureController extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            Account auth = (Account) session.getAttribute("auth");

            Order order = orderService.getOrder(orderId);

            if (order == null || order.getUser_id() != auth.getId()) {
                response.sendRedirect("Cart.jsp");
                return;
            }

            request.setAttribute("user", auth);
            request.setAttribute("order", order);


            request.getRequestDispatcher("/DigitalSignature.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect("Cart.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Luồng POST xử lý file ký tên
    }
}