package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.IOException;

@WebServlet(name = "deleteImage", value = "/deleteImage")
public class deleteImage extends HttpServlet {
    private final ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");

        if (path == null || path.isBlank()) {
            response.sendRedirect(request.getHeader("Referer"));
            return;
        }

        imageService.deleteImage(path);

        // go back to the same page
        response.sendRedirect(request.getHeader("Referer"));
    }
}