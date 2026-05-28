package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Product;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductController", value = "/Product")
public class ProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       int id = Integer.parseInt(request.getParameter("id"));
       ProductService productService = new ProductService();
       Product product = productService.getProduct(id);
       request.setAttribute("product",product);
       request.getRequestDispatcher("ProductMainPage.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}