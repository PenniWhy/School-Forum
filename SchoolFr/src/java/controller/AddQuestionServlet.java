package controller;

import dao.QuestionDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addQuestion")
public class AddQuestionServlet extends HttpServlet {

    private QuestionDAO qDao = new QuestionDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User u = (User) req.getSession().getAttribute("user");
        if (u == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String anonymousParam = req.getParameter("anonymous");
        boolean isAnonymous = "true".equals(anonymousParam) || "on".equals(anonymousParam);

        int userId = u.getUserId(); // luôn có

        boolean success = qDao.add(title, content, userId, isAnonymous);

        System.out.println("Thêm câu hỏi: " + (success ? "✅ thành công" : "❌ thất bại")
                + " | userId=" + userId + " | title=" + title);

        if (!success) {
            req.setAttribute("error", "Không thể tạo câu hỏi. Vui lòng thử lại!");
            req.getRequestDispatcher("/ask.jsp").forward(req, resp);
            return;
        }

        // ✅ Redirect để servlet /questions load lại danh sách từ DB
        resp.sendRedirect(req.getContextPath() + "/questions");
    }
}
