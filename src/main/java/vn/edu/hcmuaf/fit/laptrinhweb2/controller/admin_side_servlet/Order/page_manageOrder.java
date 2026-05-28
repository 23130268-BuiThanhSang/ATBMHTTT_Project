package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Order;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@WebServlet(name = "servlet_getOrder", value = "/page_manageOrder")
public class page_manageOrder extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if ((session == null) || (!"ADMIN".equals(session.getAttribute("auth_role")))) {
            System.out.println("not admin");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        OrderService orderService = new OrderService();
        String action = request.getParameter("action");

        List<Order> orders;

        // ===== DEFAULT: TODAY =====
        if (action == null || action.equals("today")) {

            orders = orderService.getToday();
            double income = 0;
            for (Order order : orderService.getAll()) {
                income += order.getPrice();
            }
            Locale vn = new Locale("vi", "VN");
            NumberFormat formatter = NumberFormat.getCurrencyInstance(vn);
            String total_income = formatter.format(income);

            request.setAttribute("total_income", total_income);
            String total_order = String.valueOf(orderService.getAll().size());
            request.setAttribute("orders", orders);
            request.setAttribute("total_income", total_income);
            request.setAttribute("total_order", total_order);
            request.getRequestDispatcher("manageOrder.jsp")
                    .forward(request, response);
            return;
        }

        // ===== ALL ORDERS =====
        if (action.equals("all")) {
            orders = orderService.getAll();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("manageOrder_AllOrders.jsp")
                    .forward(request, response);
            return;
        }

        // ===== FILTER =====
        if (action.equals("filter")) {
            String dayStr = request.getParameter("day");
            Integer day = null;
            if (dayStr != null && !dayStr.isBlank()) {
                day = Integer.parseInt(dayStr);
            }

            String monthStr = request.getParameter("month");
            Integer month = null;
            if (monthStr != null && !monthStr.isBlank()) {
                month = Integer.parseInt(monthStr);
            }

            String yearStr = request.getParameter("year");
            Integer year = null;
            if (yearStr != null && !yearStr.isBlank()) {
                year = Integer.parseInt(yearStr);
            }

            String status = request.getParameter("status");
            if (status == null || status.isBlank() || "all".equals(status)) {
                status = null;
            }


            orders = orderService.getByFilter(day, month, year, status);

            request.setAttribute("orders", orders);
            request.setAttribute("day", day);
            request.setAttribute("month", month);
            request.setAttribute("year", year);
            request.setAttribute("status", status);

            request.getRequestDispatcher("manageOrder_AllOrders.jsp")
                    .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
