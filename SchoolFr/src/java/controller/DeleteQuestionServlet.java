/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.AnswerDAO;
import dao.QuestionDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/deleteQuestion")
public class DeleteQuestionServlet extends HttpServlet {

    private QuestionDAO qDao = new QuestionDAO();
    private AnswerDAO aDao = new AnswerDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User currentUser = (User) req.getSession().getAttribute("user");

        if (currentUser == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        try {
            int questionId = Integer.parseInt(req.getParameter("id"));
            boolean deleted = false;

            // 👑 ADMIN → xóa tất cả
            if ("admin".equalsIgnoreCase(currentUser.getRole())) {

                aDao.deleteByQuestionId(questionId); // xóa answer trước
                deleted = qDao.deleteById(questionId);

            } // 👤 USER → chỉ xóa bài mình
            else {
                deleted = qDao.deleteQuestion(questionId, currentUser.getUserId());
            }

            System.out.println("DELETE RESULT: " + deleted);

        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect(req.getContextPath() + "/questions");
    }
}
