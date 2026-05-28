package vn.edu.hcmuaf.fit.laptrinhweb2.Cart;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart implements Serializable {

    private Map<Integer, CartItem> items = new HashMap<>();

    public void addItem(CartItem item) {
        if (items.containsKey(item.getVariantId())) {
            CartItem old = items.get(item.getVariantId());
            old.setQuantity(old.getQuantity() + item.getQuantity());
        } else {
            items.put(item.getVariantId(), item);
        }
    }

    public void remove(int variantId) {
        items.remove(variantId);
    }

    public void clear() {
        items.clear();
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(CartItem::getTotalPrice)
                .sum();
    }

    public int getTotalQuantity() {
        return items.values().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Phương thức thực hiện lấy CartItem dựa trên variantId(key) từ Map items.
     * Phương thức này hỗ trợ trong việc đánh dấu các sản phẩm được chọn để thanh toán trong quá trình checkout.
     * @param variantId
     * @return
     */
    public CartItem getItem(int variantId) {
        return items.get(variantId);
    }

    /**
     * Phương thức thực hiện tính tổng tiền của các CartItem được chọn (isSelected = true).
     * phục vụ cho việc thanh toán những sản phẩm được chọn trong giỏ hàng.
     * @return
     */
    public double getSelectedTotalPrice() {
        double total = 0;
        for (CartItem item : items.values()) {
            if (item.isSelected()) {
                total += item.getTotalPrice();
            }
        }
        return total;
    }
}

