package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.OrderDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "HistorySaleController", value = "/HistorySale")
public class HistorySaleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       request.getRequestDispatcher("HistorySale.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

