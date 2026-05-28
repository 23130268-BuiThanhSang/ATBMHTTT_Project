package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.Cart;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;

import java.io.IOException;

@WebServlet(name = "CheckOutController", value = "/CheckOut")
public class CheckOutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * Nếu người dùng truy cập trực tiếp vào trang thanh toán mà chưa chọn sản phẩm nào
         * thì sẽ được chuyển hướng về trang giỏ hàng
         */
        response.sendRedirect("Cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * Lấy giỏ hàng từ session
         */
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        /**
         *  Kiểm tra nếu giỏ hàng trống thì chuyển hướng về trang giỏ hàng
         */
        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }
        /**
         * lấy danh sách các variantId của những sản phẩm được khách tích chọn để thanh toán
         */
        String[] selectedIds = request.getParameterValues("selectedIds");

        /**
         * Xử lý trường hợp không có sản phẩm nào được chọn
         */
        if (selectedIds == null || selectedIds.length == 0) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        /**
         * Xử lý cập nhật trạng thái selected cho các CartItem trong giỏ hàng tránh việc
         * những sản phẩm không được chọn vẫn giữ trạng thái selected cũ
         */
        for (CartItem item : cart.getItems()) {
            item.setSelected(false);
        }

        /**
         * Cập nhật trạng thái selected cho các CartItem được chọn
         */
        for (String id : selectedIds) {
            try {
                int variantId = Integer.parseInt(id);
                CartItem item = cart.getItem(variantId);
                if (item != null) {
                    item.setSelected(true);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }



        /**
         * Cập nhật giỏ hàng trong session
         */
        session.setAttribute("cart", cart);
        /**
         * Chuyển hướng đến trang thanh toán
         */
        response.sendRedirect("PagePayment.jsp");
    }
}