package vn.edu.hcmuaf.fit.laptrinhweb2.Auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.hcmuaf.fit.laptrinhweb2.dao.AccountDao;
import vn.edu.hcmuaf.fit.laptrinhweb2.model.Account;

import java.io.IOException;

@WebServlet("/AccountUpdateProfile")
public class AccountUpdateProfile extends HttpServlet {

    private AccountDao accountDao = new AccountDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Account acc = (Account) session.getAttribute("auth");

        if (acc == null) {
            response.sendRedirect("Login");
            return;
        }

        String accountName = request.getParameter("accountName");
        String avatarUrl = request.getParameter("avatarUrl");
        String phone = request.getParameter("phone");

        if (accountName == null || accountName.isBlank()) {
            request.setAttribute("error", "Tên tài khoản không được để trống");
            request.getRequestDispatcher("UpdateAccountProfile.jsp").forward(request, response);
            return;
        }

        try {
            accountDao.updateProfile(acc.getId(), accountName, avatarUrl, phone);

            acc.setAccountName(accountName);
            acc.setAvatarUrl(avatarUrl);
            acc.setPhoneNumber(phone);
            session.setAttribute("auth", acc);

            request.setAttribute("success", "Cập nhật hồ sơ thành công");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Cập nhật thất bại. Vui lòng thử lại.");
        }

        request.getRequestDispatcher("UpdateAccountProfile.jsp").forward(request, response);
    }
}



