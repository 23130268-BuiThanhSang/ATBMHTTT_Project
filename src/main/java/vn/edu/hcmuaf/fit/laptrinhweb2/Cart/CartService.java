package vn.edu.hcmuaf.fit.laptrinhweb2.Cart;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CartService {

    private static final String CART_SESSION_KEY = "CART";

    public Cart getCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART_SESSION_KEY);

        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public void addToCart(HttpServletRequest request, CartItem item) {
        Cart cart = getCart(request);
        cart.addItem(item);
    }

    public void removeFromCart(HttpServletRequest request, int variantId) {
        Cart cart = getCart(request);
        cart.remove(variantId);
    }

    public void clear(HttpServletRequest request) {
        Cart cart = getCart(request);
        cart.clear();
    }
}
