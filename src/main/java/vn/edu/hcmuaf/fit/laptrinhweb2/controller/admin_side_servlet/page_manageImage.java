package vn.edu.hcmuaf.fit.laptrinhweb2.controller.admin_side_servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ImageService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "page_manageImage", value = "/page_manageImage")
public class page_manageImage extends HttpServlet {
    private final ImageService imageService = new ImageService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getParameter("path");
        if (path == null) path = "";

        File[] files = imageService.getAllFiles(path);
        if (files == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid folder");
            return;
        }
        List<File> fileList = new ArrayList<File>();
        for (File file : files) {
            if (file.isDirectory()) {
                fileList.add(file);
            }
        }
        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        request.setAttribute("files", fileList);
        request.setAttribute("path", path);

        File[] all = imageService.getAllFiles("");
        List<File> folders = Arrays.stream(all)
                .filter(File::isDirectory)
                .toList();
        request.setAttribute("folders", folders);


        request.getRequestDispatcher("/manageImage.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}