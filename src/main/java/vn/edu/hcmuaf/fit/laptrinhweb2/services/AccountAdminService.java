package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.AccountDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.OrderDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO.AccountAdminView;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AccountAdminService {

    private final AccountDao accountDao = new AccountDao();
    private final OrderDao orderDao = new OrderDao();

    public List<AccountAdminView> getAll() {
        List<Account> accounts = accountDao.getAll();
        List<AccountAdminView> result = new ArrayList<>();
        for (Account acc : accounts) {
            List<Order> orders = orderDao.getByUserId(acc.getId());
            double totalSpent = 0;
            for (Order o : orders) {
                totalSpent += o.getPrice();
            }
            result.add(new AccountAdminView(acc, totalSpent));
        }
        return result;
    }

    public AccountAdminView getById(int id) {
        Account acc = accountDao.getByID(id);
        if (acc == null) {
            return null;
        }

        List<Order> orders = orderDao.getByUserId(acc.getId());

        double totalSpent = 0;
        for (Order o : orders) {
            totalSpent += o.getPrice();
        }

        return new AccountAdminView(acc, totalSpent);
    }

    public AccountAdminView getByUsername(String username) {
        Account acc = accountDao.getByUsername(username);
        if (acc == null) {
            return null;
        }

        List<Order> orders = orderDao.getByUserId(acc.getId());

        double totalSpent = 0;
        for (Order o : orders) {
            totalSpent += o.getPrice();
        }

        return new AccountAdminView(acc, totalSpent);
    }

    public List<AccountAdminView> searchUser(String keyword) {
        List<Account> accounts = accountDao.getAll();
        List<AccountAdminView> result = new ArrayList<>();

        if (keyword == null) {
            keyword = "";
        }
        keyword = keyword.trim().toLowerCase();

        Integer searchId = null;
        try {
            searchId = Integer.parseInt(keyword);
        } catch (NumberFormatException ignored) {}

        for (Account acc : accounts) {

            boolean matched = false;

            // Empty keyword → match all
            if (keyword.isEmpty()) {
                matched = true;
            }

            // ID match (if numeric)
            if (!matched && searchId != null && acc.getId() == searchId) {
                matched = true;
            }

            // Username contains
            if (!matched && acc.getUsername() != null
                    && acc.getUsername().toLowerCase().contains(keyword)) {
                matched = true;
            }

            // Account name contains
            if (!matched && acc.getAccountName() != null
                    && acc.getAccountName().toLowerCase().contains(keyword)) {
                matched = true;
            }

            if (!matched) {
                continue;
            }

            double totalSpent = 0;
            List<Order> orders = orderDao.getByUserId(acc.getId());
            for (Order o : orders) {
                totalSpent += o.getPrice();
            }

            result.add(new AccountAdminView(acc, totalSpent));
        }

        return result;
    }

}