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

@WebServlet("/addAnswer")
public class AddAnswerServlet extends HttpServlet {

    private AnswerDAO aDao = new AnswerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String content = req.getParameter("content");
        String qid = req.getParameter("questionId");

        // ✅ Kiểm tra dữ liệu đầu vào tránh lỗi null
        if (content == null || content.trim().isEmpty() || qid == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Thiếu dữ liệu câu trả lời hoặc questionId");
            return;
        }

        int questionId = Integer.parseInt(qid);
        boolean isAnonymous = "on".equals(req.getParameter("anonymous")); // Checkbox “Ẩn danh”

        User currentUser = (User) req.getSession().getAttribute("user");

        Answer ans = new Answer();
        ans.setContent(content.trim());
        ans.setQuestionId(questionId);

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        ans.setUserId(currentUser.getUserId()); // LUÔN có user
        ans.setAnonymous(isAnonymous);          // thêm dòng này

        int newAnswerId = aDao.addAnswer(ans);

        // ✅ Nếu thêm thành công (ID > 0)
        if (newAnswerId > 0) {
            // Nếu là ẩn danh, lưu ID câu trả lời để cho phép xoá sau
            if (isAnonymous) {
                Set<Integer> anonAnswers = (Set<Integer>) req.getSession().getAttribute("anonAnswers");
                if (anonAnswers == null) {
                    anonAnswers = new HashSet<>();
                }
                anonAnswers.add(newAnswerId);
                req.getSession().setAttribute("anonAnswers", anonAnswers);
            }

            // ✅ Chuyển hướng về trang chi tiết câu hỏi
            resp.sendRedirect(req.getContextPath() + "/viewQuestion?id=" + questionId);
        } else {
            // ❌ Nếu thêm thất bại
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Không thể thêm câu trả lời.");
        }
    }
}
