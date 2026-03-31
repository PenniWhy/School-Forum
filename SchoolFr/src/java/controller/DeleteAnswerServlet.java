package controller;

import dao.AnswerDAO;
import model.Answer;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/deleteAnswer")
public class DeleteAnswerServlet extends HttpServlet {
    private AnswerDAO aDao = new AnswerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("user");

        int answerId = 0;
        try {
            answerId = Integer.parseInt(req.getParameter("id"));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/questions");
            return;
        }

        Answer answer = aDao.findById(answerId);
        if (answer == null) {
            resp.sendRedirect(req.getContextPath() + "/questions");
            return;
        }

        boolean canDelete = false;

        // ✅ Nếu là ẩn danh
        if (answer.getUserId() == null) {
            Set<Integer> anonAnswers = (Set<Integer>) req.getSession().getAttribute("anonAnswers");
            if (anonAnswers == null) anonAnswers = new HashSet<>();
            if (anonAnswers.contains(answerId)) {
                canDelete = true;
            }
        }

        // ✅ Nếu là người đăng (có tài khoản)
        else if (currentUser != null && currentUser.getUserId() == answer.getUserId()) {
            canDelete = true;
        }

        // ✅ Nếu là admin
        else if (currentUser != null &&
                 currentUser.getRole() != null &&
                 currentUser.getRole().equalsIgnoreCase("admin")) {
            canDelete = true;
        }

        // ✅ Nếu có quyền thì xóa
        if (canDelete) {
            aDao.delete(answerId);

            // Nếu là ẩn danh, xóa luôn khỏi session để không còn hiển thị
            Set<Integer> anonAnswers = (Set<Integer>) req.getSession().getAttribute("anonAnswers");
            if (anonAnswers != null) {
                anonAnswers.remove(answerId);
                req.getSession().setAttribute("anonAnswers", anonAnswers);
            }
        }

        resp.sendRedirect(req.getContextPath() + "/viewQuestion?id=" + answer.getQuestionId());
    }
}
