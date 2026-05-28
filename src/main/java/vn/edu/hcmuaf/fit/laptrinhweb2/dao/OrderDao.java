package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.OrderItem;

import java.util.List;

public class OrderDao extends BaseDao {

    private final OrderItemDao orderItemDao = new OrderItemDao();

    public List<Order> getAll() {
        return get().withHandle(handle -> {

            List<Order> orders = handle
                    .createQuery("SELECT * FROM orders")
                    .mapToBean(Order.class)
                    .list();

            // attach order items
            for (Order o : orders) {
                o.setItems(orderItemDao.getByOrderId(o.getId()));
            }

            return orders;
        });
    }

    public Order getById(int id) {
        return get().withHandle(handle -> {

            Order order = handle
                    .createQuery("SELECT * FROM orders WHERE id = :id")
                    .bind("id", id)
                    .mapToBean(Order.class)
                    .findOne()
                    .orElse(null);

            if (order != null) {
                order.setItems(orderItemDao.getByOrderId(order.getId()));
            }

            return order;
        });
    }

    public List<Order> getByUserId(int userId) {
        return get().withHandle(handle -> {

            List<Order> orders = handle
                    .createQuery("SELECT * FROM orders WHERE user_id = :userId")
                    .bind("userId", userId)
                    .mapToBean(Order.class)
                    .list();

            for (Order order : orders) {
                order.setItems(orderItemDao.getByOrderId(order.getId()));
            }

            return orders;
        });
    }

    public void updateStatus(int orderId, String status) {
        get().withHandle(handle ->
                handle.createUpdate(
                                "UPDATE orders " +
                                        "SET order_status = :status " +
                                        "WHERE id = :id"
                        )
                        .bind("status", status)
                        .bind("id", orderId)
                        .execute()
        );
    }

    public List<Order> getToday() {
        return get().withHandle(handle -> {

            List<Order> orders = handle
                    .createQuery(
                            "SELECT * FROM orders " +
                                    "WHERE DATE(order_date) = CURDATE()"
                    )
                    .mapToBean(Order.class)
                    .list();

            for (Order order : orders) {
                order.setItems(orderItemDao.getByOrderId(order.getId()));
            }

            return orders;
        });
    }
    public List<Order> getByFilter(
            Integer day,
            Integer month,
            Integer year,
            String status
    ) {
        return get().withHandle(handle -> {

            StringBuilder sql = new StringBuilder(
                    "SELECT * FROM orders WHERE 1=1 "
            );

            if (day != null) {
                sql.append("AND DAY(order_date) = :day ");
            }

            if (month != null) {
                sql.append("AND MONTH(order_date) = :month ");
            }

            if (year != null) {
                sql.append("AND YEAR(order_date) = :year ");
            }

            if (status != null && !"all".equalsIgnoreCase(status)) {
                sql.append("AND order_status = :status ");
            }

            var query = handle.createQuery(sql.toString());

            if (day != null) {
                query.bind("day", day);
            }

            if (month != null) {
                query.bind("month", month);
            }

            if (year != null) {
                query.bind("year", year);
            }

            if (status != null && !"all".equalsIgnoreCase(status)) {
                query.bind("status", status);
            }

            List<Order> orders = query
                    .mapToBean(Order.class)
                    .list();

            // attach order items
            for (Order order : orders) {
                order.setItems(orderItemDao.getByOrderId(order.getId()));
            }

            return orders;
        });
    }
    public int addOrder(Order o) {
        return get().inTransaction(handle -> {
            int orderId = handle.createUpdate(
                            "INSERT INTO orders (user_id, order_date, price, order_status, address) " +
                                    "VALUES (:userId, :orderDate, :price, :status, :address)"
                    )
                    .bind("userId", o.getUser_id())
                    .bind("orderDate", o.getOrder_date()) // LocalDateTime
                    .bind("price", o.getPrice())
                    .bind("status", o.getOrder_status())
                    .bind("address", o.getAddress())
                    .executeAndReturnGeneratedKeys("id")
                    .mapTo(Integer.class)
                    .one();

            if (o.getItems() != null) {
                OrderItemDao itemDao = new OrderItemDao();
                for (OrderItem item : o.getItems()) {
                    item.setOrderId(orderId);
                    itemDao.insert(item);
                }
            }
            return orderId;
        });
    }

}
