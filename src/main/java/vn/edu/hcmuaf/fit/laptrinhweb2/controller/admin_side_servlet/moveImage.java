package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.IOException;

@WebServlet(name = "moveImage", value = "/moveImage")
public class moveImage extends HttpServlet {
    private final ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fromPath = request.getParameter("fromPath");
        String toPath = request.getParameter("toPath");

        imageService.moveImage(fromPath, toPath);

        response.sendRedirect(request.getHeader("Referer"));
    }
}