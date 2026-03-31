/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.QuestionDAO;
import model.Question;
import model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/editQuestion")
public class EditQuestionServlet extends HttpServlet {
    private QuestionDAO qDao = new QuestionDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        Question q = qDao.getQuestionById(id);

        // chỉ cho sửa nếu là người đăng
        if (q == null || q.getUserId() != user.getUserId()) {
            resp.sendRedirect(req.getContextPath() + "/questions");
            return;
        }

        req.setAttribute("question", q);
        req.getRequestDispatcher("/editQuestion.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int id = Integer.parseInt(req.getParameter("id"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        qDao.updateQuestion(id, user.getUserId(), title, content);
        resp.sendRedirect(req.getContextPath() + "/questions");
    }
}

