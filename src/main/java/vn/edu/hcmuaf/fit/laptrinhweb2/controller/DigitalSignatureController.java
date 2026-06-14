package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.Cart;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "DigitalSignature", value = "/DigitalSignature")
public class DigitalSignatureController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * kiểm tra session
         */
        HttpSession session = request.getSession(false);
        if (session != null) {
            Object auth = session.getAttribute("auth");
            Cart cart = (Cart) session.getAttribute("cart");

            if (auth == null || cart == null || cart.isEmpty()) {
                response.sendRedirect("Cart.jsp");
                return;
            }

            /**
             * Lấy mảng ID được gửi dưới dạng query parameter (?selectedIds=1&selectedIds=2)
             */
            String[] selectedIds = request.getParameterValues("selectedIds");
            if (selectedIds == null || selectedIds.length == 0) {
                response.sendRedirect("Cart.jsp");
                return;
            }

            /**
             * reset lại trạng thái chọn của tất cả CartItem trong giỏ hàng trước khi đánh dấu lại các sản phẩm được chọn để thanh toán.
             */
            for (CartItem item : cart.getItems()) {
                item.setSelected(false);
            }

            List<CartItem> selectedItems = new ArrayList<>();
            double totalPrice = 0;

            /**
             * Duyệt qua mảng ID đã chọn, tìm CartItem tương ứng trong giỏ hàng và đánh dấu chúng là đã được chọn (isSelected = true).
             */
            for (String id : selectedIds) {
                try {
                    int variantId = Integer.parseInt(id);
                    CartItem item = cart.getItem(variantId);
                    if (item != null) {
                        item.setSelected(true); // Đánh dấu chọn thành công dùng cho admin quản lý đơn hàng sau này
                        selectedItems.add(item);
                        totalPrice += item.getPrice() * item.getQuantity();
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            /**
             * cập nhật trang thái giỏ hàng mới vào session
             */
            session.setAttribute("cart", cart);

            if (selectedItems.isEmpty()) {
                response.sendRedirect("Cart.jsp");
                return;
            }

            /**
             * gửi dữ liệu sang jsp
             */
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("id", "OD" + System.currentTimeMillis());
            orderData.put("items", selectedItems);
            orderData.put("totalPrice", totalPrice);

            request.setAttribute("user", auth);


            /**
             * Lưu dữ liệu đơn hàng vào session để DownloadOrderController có thể truy cập khi người dùng bấm nút tải file JSON.
             */
            session.setAttribute("order", orderData);

            request.getRequestDispatcher("/DigitalSignature.jsp").forward(request, response);
        } else {
            response.sendRedirect("Cart.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Luồng xử lý Post (khi bấm nút xác thực ở form trang chữ ký) tạm thời chưa đụng tới
    }
}