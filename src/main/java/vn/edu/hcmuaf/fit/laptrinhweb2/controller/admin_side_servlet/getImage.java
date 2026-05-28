package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "getImage", value = "/getImage")
public class getImage extends HttpServlet {
    private static final String ROOT = "D:/lgImg/";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        ImageService imageService = new ImageService();

        File file = imageService.getImageFile(path);
        if (file == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String mimeType = getServletContext().getMimeType(file.getName());
        response.setContentType(mimeType != null ? mimeType : "application/octet-stream");
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file);
             var out = response.getOutputStream()) {
            in.transferTo(out);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}