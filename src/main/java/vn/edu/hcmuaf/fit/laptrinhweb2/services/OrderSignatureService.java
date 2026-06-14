package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;

import java.util.List;
import java.util.Map;

public class OrderSignatureService {

    private static OrderSignatureService instance;

    private OrderSignatureService() {}

    public static synchronized OrderSignatureService getInstance() {
        if (instance == null) {
            instance = new OrderSignatureService();
        }
        return instance;
    }

    /**
     * Hàm định dạng dữ liệu đơn hàng thành chuỗi chuẩn JSON.
     */
    public String buildOrderContentForSigning(Map<String, Object> orderData) {
        if (orderData == null) return "{}";

        String orderId = (String) orderData.get("id");
        List<CartItem> items = (List<CartItem>) orderData.get("items");
        double totalPrice = (double) orderData.get("totalPrice");

        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        sb.append("  \"orderId\": \"").append(orderId).append("\",\n");
        // Giữ nguyên kiểu số (không có ngoặc kép) cho giá tiền
        sb.append("  \"totalPrice\": ").append(String.format("%.0f", totalPrice)).append(",\n");
        sb.append("  \"items\": [\n");

        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                sb.append("    {\n");

                // Xử lý replace dấu ngoặc kép trong tên sản phẩm (nếu có) để tránh lỗi cú pháp JSON
                String productName = item.getProductName() != null ? item.getProductName().replace("\"", "\\\"") : "";

                sb.append("      \"productName\": \"").append(productName).append("\",\n");
                sb.append("      \"color\": \"").append(item.getColor()).append("\",\n");
                sb.append("      \"size\": \"").append(item.getSize()).append("\",\n");
                sb.append("      \"quantity\": ").append(item.getQuantity()).append(",\n");
                sb.append("      \"price\": ").append(String.format("%.0f", item.getPrice())).append("\n");

                sb.append("    }");
                // Nếu chưa phải là item cuối cùng thì thêm dấu phẩy
                if (i < items.size() - 1) {
                    sb.append(",");
                }
                sb.append("\n");
            }
        }

        sb.append("  ]\n");
        sb.append("}");

        return sb.toString();
    }
}