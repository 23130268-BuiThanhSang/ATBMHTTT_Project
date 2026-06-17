package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.Cart;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.enum_macro.VerifyStatus;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.OrderItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.VerifyIfor;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "CreateOrderController", value = "/CreateOrder")
public class CreateOrderBeforeSignatureController extends HttpServlet {

    private OrderService orderService = new OrderService();
    private ProductService productService = new ProductService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
         * kiểm tra session và thông tin đăng nhập, nếu không có thì chuyển hướng về trang cart, vì chỉ có trang cart mới có nút mua hàng và gửi request đến đây, nên nếu không có session hoặc thông tin đăng nhập thì chắc chắn là lỗi và chuyển hướng về cart để người dùng có thể đăng nhập lại
         */
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        Cart cart = (Cart) session.getAttribute("cart");
        Account auth = (Account) session.getAttribute("auth");

        if (auth == null || cart == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        /**
         * lấy thoonh tin các sản phẩm được tích ở giỏ hàng
         */
        String[] selectedIds = request.getParameterValues("selectedIds");
        if (selectedIds == null || selectedIds.length == 0) {
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
            return;
        }

        for (CartItem item : cart.getItems()) {
            item.setSelected(false);
        }
        for (String idStr : selectedIds) {
            try {
                int variantId = Integer.parseInt(idStr);
                CartItem item = cart.getItem(variantId);
                if (item != null) {
                    item.setSelected(true);
                }
            } catch (Exception e) {}
        }

        String address = request.getParameter("address");
        /**
         * tạo đơn hàng với thông tin user_id, order_date, order_status, address, price, và các sản phẩm được tích ở giỏ hàng, sau đó lưu vào database và trả về id của đơn hàng vừa tạo để phục vụ cho trang ký điện tử
         */
        try {
            Order order = new Order();
            order.setUser_id(auth.getId());
            order.setOrder_date(LocalDateTime.now());
            order.setOrder_status("PENDING");
            order.setAddress(address != null ? address : "");
            order.setPrice(cart.getSelectedTotalPrice());

            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem item : cart.getItems()) {
                if (item.isSelected()) {
                    OrderItem oi = new OrderItem();
                    oi.setProduct(productService.getProduct(item.getProductId()));
                    oi.setVariant(productService.getVariantById(item.getVariantId()));
                    oi.setQuantity(item.getQuantity());
                    oi.setPrice(item.getPrice());
                    orderItems.add(oi);
                }
            }

            if (orderItems.isEmpty()) {
                request.getRequestDispatcher("Cart.jsp").forward(request, response);
                return;
            }

            order.setItems(orderItems);
            /**
             * tạo bản ghi verify với thông tin orderId, verifyStatus là UNVERIFIED, keyId là null, dateVerify là thời điểm tạo đơn hàng, sau đó lưu vào database để phục vụ cho trang admin hiển thị trạng thái verify của đơn hàng và cập nhật trạng thái verify sau khi admin đã kiểm tra và xác nhận đơn hàng
             */
            VerifyIfor verifyInfo = new VerifyIfor();

            verifyInfo.setVerifyStatus(VerifyStatus.UNVERIFIED);

            int orderId = orderService.addOrderBeforeSignature(order, verifyInfo);

            /**
             * xóa các sản phẩm đã được tích ở giỏ hàng để tạo đơn hàng khỏi giỏ hàng, sau đó cập nhật lại session để hiển thị giỏ hàng đã được cập nhật, vì sau khi tạo đơn hàng thành công thì chắc chắn là các sản phẩm đó đã được mua và không còn trong giỏ hàng nữa nên cần xóa khỏi giỏ hàng để tránh nhầm lẫn cho người dùng và cập nhật lại session để hiển thị giỏ hàng đã được cập nhật
             */
            Iterator<CartItem> iterator = cart.getItems().iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.isSelected()) {
                    iterator.remove();
                }
            }
            session.setAttribute("cart", cart);

            /**
             * chuyển hướng đến trang ký điện tử với id của đơn hàng vừa tạo để người dùng có thể ký điện tử cho đơn hàng đó, vì sau khi tạo đơn hàng thành công thì chắc chắn là người dùng sẽ muốn ký điện tử cho đơn hàng đó nên chuyển hướng đến trang ký điện tử để người dùng có thể ký điện tử cho đơn hàng đó
             */
            response.sendRedirect("DigitalSignature?orderId=" + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("Cart.jsp").forward(request, response);
        }
    }
}