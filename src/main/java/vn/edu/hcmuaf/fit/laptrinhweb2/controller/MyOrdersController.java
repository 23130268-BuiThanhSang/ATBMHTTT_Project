package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.OrderDTO;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderSignatureService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "MyOrdersController", value = "/MyOrders")
public class MyOrdersController extends HttpServlet {
    OrderService orderService = new OrderService();
    OrderSignatureService orderSignatureService = new OrderSignatureService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect("Login");
            return;
        }

        Account auth = (Account) session.getAttribute("auth");

        int userId = auth.getId();

        List<Order> listOrders = orderService.getOrderByUserId(userId);
        System.out.println("listOrders: " + listOrders.size());
        List<OrderDTO> listOrderDTOs = orderSignatureService.prepareOrderDTOList(listOrders);
        System.out.println("listOrders: " + listOrderDTOs.size());
        request.setAttribute("listOrderDTOs", listOrderDTOs);
//        response.sendRedirect("MyOrders");
        request.getRequestDispatcher("HistorySale.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}