package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.Cart.CartItem;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.OrderItem;

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

    public String buildOrderJsonForSigning(Order order) {
        if (order == null) return "{}";

        StringBuilder sb = new StringBuilder();

        sb.append("{\n");

        // ===== ORDER =====
        sb.append("\t\"order\": {\n");
        sb.append("\t\t\"id\": ").append(order.getId()).append(",\n");
        sb.append("\t\t\"user_id\": ").append(order.getUser_id()).append(",\n");
        sb.append("\t\t\"price\": ").append(formatNumber(order.getPrice())).append(",\n");

        if (order.getOrder_date() != null) {
            sb.append("\t\t\"order_date\": \"")
                    .append(order.getOrder_date())
                    .append("\",\n");
        } else {
            sb.append("\t\t\"order_date\": null,\n");
        }

        String address = order.getAddress() != null
                ? order.getAddress().replace("\"", "\\\"")
                : "";

        sb.append("\t\t\"address\": \"").append(address).append("\"\n");
        sb.append("\t},\n");

        // ===== ORDER DETAIL =====
        sb.append("\t\"order_details\": [\n");

        List<OrderItem> items = order.getItems();

        if (items != null && !items.isEmpty()) {

            for (int i = 0; i < items.size(); i++) {
                OrderItem item = items.get(i);

                sb.append("\t\t{\n");

                sb.append("\t\t\t\"id\": ").append(item.getId()).append(",\n");
                sb.append("\t\t\t\"order_id\": ").append(order.getId()).append(",\n");

                int variantId = (item.getVariant() != null)
                        ? item.getVariant().getId()
                        : 0;

                sb.append("\t\t\t\"variant_id\": ").append(variantId).append(",\n");
                sb.append("\t\t\t\"price\": ").append(formatNumber(item.getPrice())).append(",\n");
                sb.append("\t\t\t\"quantity\": ").append(item.getQuantity()).append("\n");

                sb.append("\t\t}");

                if (i < items.size() - 1) {
                    sb.append(",");
                }

                sb.append("\n");
            }
        }

        sb.append("\t]\n");

        sb.append("}");

        return sb.toString();
    }
//    public String buildOrderJsonForSigning(Order order) {
//        if (order == null) return "{}";
//
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("{");
//
//        // ===== ORDER =====
//        sb.append("\"order\":{");
//        sb.append("\"id\":").append(order.getId()).append(",");
//        sb.append("\"user_id\":").append(order.getUser_id()).append(",");
//        sb.append("\"price\":").append(formatNumber(order.getPrice())).append(",");
//
//        if (order.getOrder_date() != null) {
//            sb.append("\"order_date\":\"").append(order.getOrder_date()).append("\",");
//        } else {
//            sb.append("\"order_date\":null,");
//        }
//
//        String address = order.getAddress() != null
//                ? order.getAddress().replace("\"", "\\\"")
//                : "";
//
//        sb.append("\"address\":\"").append(address).append("\"");
//        sb.append("},");
//
//        // ===== ORDER DETAIL =====
//        sb.append("\"order_details\":[");
//
//        List<OrderItem> items = order.getItems();
//
//        if (items != null && !items.isEmpty()) {
//
////            items.sort((a, b) -> {
////                int va = (a.getVariant() != null) ? a.getVariant().getId() : 0;
////                int vb = (b.getVariant() != null) ? b.getVariant().getId() : 0;
////                return Integer.compare(va, vb);
////            });
//
//            for (int i = 0; i < items.size(); i++) {
//                OrderItem item = items.get(i);
//
//                sb.append("{");
//
//                sb.append("\"id\":").append(item.getId()).append(",");
//                sb.append("\"order_id\":").append(order.getId()).append(",");
//
//                int variantId = (item.getVariant() != null)
//                        ? item.getVariant().getId()
//                        : 0;
//
//                sb.append("\"variant_id\":").append(variantId).append(",");
//                sb.append("\"price\":").append(formatNumber(item.getPrice())).append(",");
//                sb.append("\"quantity\":").append(item.getQuantity());
//
//                sb.append("}");
//
//                if (i < items.size() - 1) {
//                    sb.append(",");
//                }
//            }
//        }
//
//        sb.append("]");
//
//        sb.append("}");
//
//        return sb.toString();
//    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.valueOf((long) number);
        }
        return String.valueOf(number);
    }
}