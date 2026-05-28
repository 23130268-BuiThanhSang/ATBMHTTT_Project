package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet.Hotdeal;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.HotDeal;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.HotDealService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "page_manageHotdeal", value = "/page_manageHotdeal")
public class page_manageHotdeal extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HotDealService hotDealService = new HotDealService();
        List<HotDeal> hotDeals = hotDealService.getAllHotDeals();
        request.setAttribute("hotDeals", hotDeals);
        request.getRequestDispatcher("manageHotdeal.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}