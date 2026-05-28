package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@WebServlet(name = "uploadImage", value = "/uploadImage")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 20 * 1024 * 1024
)
public class uploadImage extends HttpServlet {

    private final ImageService imageService = new ImageService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String path = request.getParameter("path");
        Part filePart = request.getPart("image");

        try {
            imageService.uploadImage(path, filePart);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        response.sendRedirect(request.getHeader("Referer"));
    }
}
