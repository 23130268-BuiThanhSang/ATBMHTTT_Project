package vn.edu.hcmuaf.fit.laptrinhweb2.model.DTO;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

public class AccountAdminView {

    private Account account;
    private double totalSpent;

    public AccountAdminView(Account account, double totalSpent) {
        this.account = account;
        this.totalSpent = totalSpent;
    }

    public Account getAccount() {
        return account;
    }

    public double getTotalSpent() {
        return totalSpent;
    }
}