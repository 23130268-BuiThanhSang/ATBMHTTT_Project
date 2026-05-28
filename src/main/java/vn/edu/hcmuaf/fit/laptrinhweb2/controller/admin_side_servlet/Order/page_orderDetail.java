package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;

import java.io.IOException;

@WebServlet(name = "page_orderDetail", value = "/page_orderDetail")
public class page_orderDetail extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String idParam = request.getParameter("orderId");

        if (idParam == null) {
            response.sendRedirect("getOrderList?action=all");
            return;
        }

        int orderId;
        try {
            orderId = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect("page_manageOrder?action=all");
            return;
        }

        OrderService orderService = new OrderService();
        Order order = orderService.getOrder(orderId);

        if (order == null) {
            response.sendRedirect("page_manageOrder?action=all");
            return;
        }

        request.setAttribute("order", order);
        request.getRequestDispatcher("manageOrder_OrderDetail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}