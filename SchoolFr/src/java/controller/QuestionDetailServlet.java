package controller;

import dao.QuestionDAO;
import dao.AnswerDAO;
import model.Question;
import model.Answer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewQuestion")
public class QuestionDetailServlet extends HttpServlet {
    private QuestionDAO qDao = new QuestionDAO();
    private AnswerDAO aDao = new AnswerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idStr = req.getParameter("id");
        int id = 0;
        try {
            id = Integer.parseInt(idStr);
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/questions");
            return;
        }

        // ✅ Lấy thông tin câu hỏi
        Question q = qDao.findById(id);
        if (q == null) {
            resp.sendRedirect(req.getContextPath() + "/questions");
            return;
        }

        // ✅ Lấy danh sách câu trả lời
        List<Answer> answers = aDao.getAnswersByQuestionId(id);

        // Gửi sang JSP
        req.setAttribute("question", q);
        req.setAttribute("answers", answers);

        req.getRequestDispatcher("/viewQuestion.jsp").forward(req, resp);
    }
}
