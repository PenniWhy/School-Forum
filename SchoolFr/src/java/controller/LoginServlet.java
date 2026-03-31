package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserDAO userDao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String hashed = PasswordUtil.hash(password);
        User u = userDao.login(username, hashed);
        if (u == null) {
            // thử password cũ
            u = userDao.login(username, password);

            // nếu đúng → update lại thành hash
            if (u != null) {
                userDao.updatePassword(u.getUserId(), hashed);
            }
        }
        if (u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            resp.sendRedirect(req.getContextPath() + "/questions");
        } else {
            req.setAttribute("error", "Tên hoặc mật khẩu không đúng");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
