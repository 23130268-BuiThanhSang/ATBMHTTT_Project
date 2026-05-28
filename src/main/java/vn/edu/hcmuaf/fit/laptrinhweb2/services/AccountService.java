package vn.edu.hcmuaf.fit.laptrinhweb2.services;

import vn.edu.hcmuaf.fit.laptrinhweb2.dao.AccountDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.util.List;

public class AccountService {
    private AccountDao accountDao = new AccountDao();

    public void update(Account acc, Boolean skipPass) {
        Account baseAcc = accountDao.getByID(acc.getId());
        if (baseAcc == null) {
            throw new IllegalArgumentException("aaaaaa");
        }

        // allowed updates
        baseAcc.setUsername(acc.getUsername());
        if (!skipPass) {
            baseAcc.setPassword(acc.getPassword());
        }
        baseAcc.setRole(acc.getRole());
        baseAcc.setAccountName(acc.getAccountName());
        baseAcc.setPhoneNumber(acc.getPhoneNumber());
        baseAcc.setAccountEmail(acc.getAccountEmail());
        baseAcc.setAccountStatus(acc.getAccountStatus());
        baseAcc.setAvatarUrl(acc.getAvatarUrl());
        accountDao.update(baseAcc,skipPass);
    }

    public List<Account> getAllAccounts() {
        return accountDao.getAll();
    }

    public Account getAccountByID(int id) {
        return accountDao.getByID(id);
    }

    public Account getAccountByUsername(String username) {
        return accountDao.getByUsername(username);
    }

    public void add(Account acc) {
        accountDao.add(acc);
    }
}
