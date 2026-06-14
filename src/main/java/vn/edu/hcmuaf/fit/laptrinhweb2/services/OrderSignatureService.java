package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;

import java.util.List;
import java.util.Map;

/**
 * thực hiện áp dụng Singleton Pattern để đảm bảo chỉ có một instance duy nhất của OrderSignatureService trong toàn bộ ứng dụng, giúp tiết kiệm tài nguyên và đảm bảo tính nhất quán khi tạo nội dung JSON cho đơn hàng.
 * Service này có nhiệm vụ định dạng dữ liệu đơn hàng thành chuỗi JSON chuẩn để phục vụ cho việc tạo file JSON tải về, đồng thời có thể tái sử dụng cho các mục đích khác như quản lý đơn hàng trong trang admin sau này nếu cần thiết.
 */
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


        sb.append("  \"totalPrice\": ").append(String.format("%.0f", totalPrice)).append(",\n");
        sb.append("  \"items\": [\n");

        if (items != null && !items.isEmpty()) {
            for (int i = 0; i < items.size(); i++) {
                CartItem item = items.get(i);
                sb.append("    {\n");

                String productName = item.getProductName() != null ? item.getProductName().replace("\"", "\\\"") : "";

                sb.append("      \"productName\": \"").append(productName).append("\",\n");
                sb.append("      \"color\": \"").append(item.getColor()).append("\",\n");
                sb.append("      \"size\": \"").append(item.getSize()).append("\",\n");
                sb.append("      \"quantity\": ").append(item.getQuantity()).append(",\n");
                sb.append("      \"price\": ").append(String.format("%.0f", item.getPrice())).append("\n");

                sb.append("    }");
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