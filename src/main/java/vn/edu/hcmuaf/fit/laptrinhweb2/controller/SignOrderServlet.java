package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderSignatureService;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.KeyDao;

import java.io.IOException;
import java.io.InputStream;

@WebServlet(name = "SignOrderServlet", value = "/sign-process")
@MultipartConfig
public class SignOrderServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();
    private final OrderSignatureService signatureService = OrderSignatureService.getInstance();
    private final KeyDao keyDao = new KeyDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Cart.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("auth") == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        Account auth = (Account) session.getAttribute("auth");
        String orderIdParam = request.getParameter("orderId");

        if (orderIdParam == null || orderIdParam.trim().isEmpty()) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        try {
            int orderId = Integer.parseInt(orderIdParam);
            Order order = orderService.getOrder(orderId);

            if (order == null || order.getUser_id() != auth.getId()) {
                response.sendRedirect("Cart.jsp");
                return;
            }

            request.setAttribute("user", auth);
            request.setAttribute("order", order);

            Part filePart = request.getPart("signatureFile");
            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("message", "Vui lòng chọn file chứa chữ ký điện tử (.sig)!");
                request.setAttribute("isError", true);
                request.getRequestDispatcher("/DigitalSignature.jsp").forward(request, response);
                return;
            }

            byte[] signatureBytes;
            try (InputStream is = filePart.getInputStream()) {
                signatureBytes = is.readAllBytes();
            }

//            String activeKeyStr = keyDao.getActiveKeyByUserId(auth.getId());
//            if (activeKeyStr == null || activeKeyStr.trim().isEmpty()) {
//                request.setAttribute("message", "Không tìm thấy Khóa công khai hợp lệ cho tài khoản của bạn!");
//                request.setAttribute("isError", true);
//                request.getRequestDispatcher("/DigitalSignature.jsp").forward(request, response);
//                return;
//            }

            boolean isValid = signatureService.verifyAndSaveOrderSignature(order, signatureBytes,auth.getId());

            if (isValid) {
                request.setAttribute("message", "Xác nhận chữ ký thành công! Đơn hàng của bạn đã hợp lệ.");
                request.setAttribute("isError", false);
            } else {
                request.setAttribute("message", "Xác thực chữ ký thất bại! Tệp ký hoặc Khóa không trùng khớp.");
                request.setAttribute("isError", true);
            }

        } catch (NumberFormatException e) {
            response.sendRedirect("Cart.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "Đã xảy ra lỗi hệ thống: " + e.getMessage());
            request.setAttribute("isError", true);
        }

        request.getRequestDispatcher("/DigitalSignature.jsp").forward(request, response);
    }
}