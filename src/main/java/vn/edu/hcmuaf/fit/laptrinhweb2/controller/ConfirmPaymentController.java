package vn.edu.hcmuaf.fit.laptrinhweb2.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.Cart;
import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.ProductVariantDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.OrderItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.OrderService;
import vn.edu.hcmuaf.fit.laptrinhweb2.services.ProductService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ConfirmPaymentController", value = "/ConfirmPayment")
public class ConfirmPaymentController extends HttpServlet {
    OrderService orderService = new OrderService();
    ProductService productService = new ProductService();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        Cart cart = (Cart) session.getAttribute("cart");
        Account auth = (Account) session.getAttribute("auth");

        if (cart == null || auth == null) {
            response.sendRedirect("Cart.jsp");
            return;
        }

        String address = request.getParameter("address");

        Order order = new Order();
        order.setUser_id(auth.getId());
        order.setOrder_date(LocalDateTime.now());
        order.setOrder_status("PENDING");
        order.setAddress(address);
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

        order.setItems(orderItems);


        orderService.addOrder(order);


        cart.getItems().removeIf(CartItem::isSelected);
        session.setAttribute("cart", cart);


        response.sendRedirect("PaymentSuccess.jsp");
    }
    }
