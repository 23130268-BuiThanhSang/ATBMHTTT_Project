package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.OrderDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public List<Order> getAll() {
        return orderDao.getAll();
    }

    public Order getOrder(int id) {
        return orderDao.getById(id);
    }

    public List<Order> getToday() {
        return orderDao.getToday();
    }
    public List<Order> getByFilter(
            Integer day,
            Integer month,
            Integer year,
            String status
    ) {
        return orderDao.getByFilter(day, month, year, status);
    }

    public void updateStatus(int orderId, String status) {
        orderDao.updateStatus(orderId,status);
    }

    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }
}

