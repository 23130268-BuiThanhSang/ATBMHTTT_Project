package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet("/AddReview")
public class AddReviewController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("Login");
            return;
        }

        Account acc = (Account) session.getAttribute("auth");
        if (acc == null) {
            String productId = request.getParameter("productId");

            session.setAttribute("redirectAfterLogin",
                    "/ProductMainPage?id=" + productId);
            response.sendRedirect("Login");
            return;
        }


        int productId = Integer.parseInt(request.getParameter("productId"));
        int rating = Integer.parseInt(request.getParameter("rating"));
        String reviewText = request.getParameter("reviewText");

        ProductDao dao = new ProductDao();
        dao.insertReview(acc.getId(), productId, rating, reviewText);

        response.sendRedirect("ProductMainPage?id=" + productId);
    }
}

