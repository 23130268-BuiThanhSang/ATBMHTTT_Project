package vn.edu.hcmuaf.fit.laptrinhweb2.dao;

import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.util.List;

public class AccountDao extends BaseDao {

    public List<Account> getAll() {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM accounts")
                        .map((rs, ctx) -> {
                            Account a = new Account();
                            a.setId(rs.getInt("id"));
                            a.setUsername(rs.getString("user_name"));
                            a.setPassword(rs.getString("password"));
                            a.setAccountStatus(rs.getInt("account_status"));
                            a.setPhoneNumber(
                                    rs.getObject("user_phone_number") == null
                                            ? null
                                            : rs.getString("user_phone_number")
                            );
                            a.setAccountEmail(rs.getString("user_email"));
                            a.setAccountName(rs.getString("account_name"));
                            a.setAvatarUrl(rs.getString("avatar_url"));
                            a.setRole(rs.getString("role"));
                            return a;
                        })
                        .list()
        );
    }

    public Account getByID(int id) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM accounts WHERE id = :id")
                        .bind("id", id)
                        .map((rs, ctx) -> {
                            Account a = new Account();
                            a.setId(rs.getInt("id"));
                            a.setUsername(rs.getString("user_name"));
                            a.setPassword(rs.getString("password"));
                            a.setAccountStatus(rs.getInt("account_status"));
                            a.setPhoneNumber(
                                    rs.getObject("user_phone_number") == null
                                            ? null
                                            : rs.getString("user_phone_number")
                            );
                            a.setAccountEmail(rs.getString("user_email"));
                            a.setAccountName(rs.getString("account_name"));
                            a.setAvatarUrl(rs.getString("avatar_url"));
                            a.setRole(rs.getString("role"));
                            return a;
                        })
                        .findFirst()
                        .orElse(null)
        );
    }

    public Account getByUsername(String username) {
        return get().withHandle(h ->
                h.createQuery("SELECT * FROM accounts WHERE user_name = :username")
                        .bind("username", username)
                        .map((rs, ctx) -> {
                            Account a = new Account();
                            a.setId(rs.getInt("id"));
                            a.setUsername(rs.getString("user_name"));
                            a.setPassword(rs.getString("password"));
                            a.setAccountStatus(rs.getInt("account_status"));
                            a.setPhoneNumber(
                                    rs.getObject("user_phone_number") == null
                                            ? null
                                            : rs.getString("user_phone_number")
                            );
                            a.setAccountEmail(rs.getString("user_email"));
                            a.setAccountName(rs.getString("account_name"));
                            a.setAvatarUrl(rs.getString("avatar_url"));
                            a.setRole(rs.getString("role"));
                            return a;
                        })
                        .findFirst()
                        .orElse(null)
        );
    }

    public void update(Account acc, Boolean skipPass) {
        if (!skipPass) {
            get().useHandle(h ->
                    h.createUpdate("""
                    UPDATE accounts
                    SET
                        user_name = :username,
                        role = :role,
                        password = :password,
                        account_status = :accountStatus,
                        user_phone_number = :phoneNumber,
                        user_email = :accountEmail,
                        account_name = :accountName,
                        avatar_url = :avatarUrl,
                        role = :role
                    WHERE id = :id
                """)
                .bind("id", acc.getId())
                .bind("role", acc.getRole())
                .bind("username", acc.getUsername())
                .bind("password", acc.getPassword())
                .bind("accountStatus", acc.getAccountStatus())
                .bind("phoneNumber", acc.getPhoneNumber())
                .bind("accountEmail", acc.getAccountEmail())
                .bind("accountName", acc.getAccountName())
                .bind("avatarUrl", acc.getAvatarUrl())
                .bind("role", acc.getRole())
                .execute()
            );
        } else {
            get().useHandle(h ->
                    h.createUpdate("""
                    UPDATE accounts
                    SET
                        user_name = :username,
                        role = :role,
                        account_status = :accountStatus,
                        user_phone_number = :phoneNumber,
                        user_email = :accountEmail,
                        account_name = :accountName,
                        avatar_url = :avatarUrl,
                        role = :role
                    WHERE id = :id
                """)
                .bind("id", acc.getId())
                .bind("role", acc.getRole())
                .bind("username", acc.getUsername())
                .bind("accountStatus", acc.getAccountStatus())
                .bind("phoneNumber", acc.getPhoneNumber())
                .bind("accountEmail", acc.getAccountEmail())
                .bind("accountName", acc.getAccountName())
                .bind("avatarUrl", acc.getAvatarUrl())
                .bind("role", acc.getRole())
                .execute()
            );
        }
    }
    public void add(Account acc) {
        get().useHandle(h ->
                h.createUpdate("""
                INSERT INTO accounts (
                    user_name,
                    password,
                    account_name,
                    account_status,
                    user_phone_number,
                    user_email,
                    role,
                    avatar_url
                )
                VALUES (
                    :username,
                    :password,
                    :accountName,
                    :accountStatus,
                    :phoneNumber,
                    :accountEmail,
                    'USER',
                    :avatarUrl
                )
            """)
                        .bind("username", acc.getUsername())
                        .bind("password", acc.getPassword())
                        .bind("accountName", acc.getAccountName())
                        .bind("accountStatus", acc.getAccountStatus())
                        .bind("phoneNumber", acc.getPhoneNumber())
                        .bind("accountEmail", acc.getAccountEmail())
                        .bind("avatarUrl", acc.getAvatarUrl())
                        .execute()
        );
    }

    public void updateProfile(int id, String accountName, String avatarUrl, String phoneNumber) {
        get().useHandle(h ->
                h.createUpdate("""
                UPDATE accounts
                SET
                    account_name = :accountName,
                    avatar_url = :avatarUrl,
                    user_phone_number = :phoneNumber
                WHERE id = :id
            """)
                        .bind("id", id)
                        .bind("accountName", accountName)
                        .bind("avatarUrl", avatarUrl)
                        .bind("phoneNumber", phoneNumber)
                        .execute()
        );
    }


}