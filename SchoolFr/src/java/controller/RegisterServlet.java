package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDao = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String fullname = req.getParameter("fullname");
        String email = req.getParameter("email");

        User u = new User();
        u.setUsername(username);
        u.setPassword(PasswordUtil.hash(password));
        u.setFullName(fullname);
        u.setEmail(email);
        u.setRole("User");

        boolean ok = userDao.register(u);
        if (ok) resp.sendRedirect(req.getContextPath() + "/login");
        else {
            req.setAttribute("error", "Tên đăng nhập đã tồn tại hoặc lỗi hệ thống");
            req.getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
