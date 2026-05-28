package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Hotdeal;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.HotDeal;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.HotDealService;

import java.io.IOException;

@WebServlet(name = "getHotdeal", value = "/getHotdeal")
public class getHotdeal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        HotDealService hotDealService = new HotDealService();
        if (idParam == null) {
            response.sendRedirect(request.getContextPath() + "/admin/hotdeal");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/hotdeal");
            return;
        }

        HotDeal hotDeal = hotDealService.getHotDealById(id);

        if (hotDeal == null) {
            response.sendRedirect(request.getContextPath() + "/admin/hotdeal");
            return;
        }

        request.setAttribute("hotDeal", hotDeal);
        request.getRequestDispatcher("manageHotdeal_HotdealDetail.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}