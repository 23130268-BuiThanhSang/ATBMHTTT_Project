package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.File;
import java.io.IOException;

@WebServlet(name = "renameImage", value = "/renameImage")
public class renameImage extends HttpServlet {

    private final ImageService imageService = new ImageService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getParameter("path");
        String newName = request.getParameter("newName");

        if (path == null || newName == null || newName.isBlank()) {
            return;
        }

        boolean success;
        try {
            success = imageService.renameImage(path, newName);
        } catch (Exception e) {
            response.sendRedirect("page_manageImage");
            return;
        }

        if (!success) {
            response.sendRedirect("page_manageImage?error=exists");
            return;
        }

        response.sendRedirect("page_manageImage");
    }
}
