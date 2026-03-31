package controller;

import dao.QuestionDAO;
import model.Question;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/questions")
public class QuestionListServlet extends HttpServlet {
    private QuestionDAO qDao = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Lấy từ khóa tìm kiếm (nếu có)
        String q = req.getParameter("search");

        // ✅ Sửa lỗi: nếu không có search => gán chuỗi rỗng để tránh WHERE ... LIKE null
        if (q == null) {
            q = "";
        }

        // Lấy danh sách câu hỏi (kể cả ẩn danh)
        List<Question> list = qDao.findAll(q);

        // Gửi danh sách sang JSP
        req.setAttribute("questions", list);

        // Chuyển hướng đến trang hiển thị
        req.getRequestDispatcher("/questions.jsp").forward(req, resp);
    }
}
