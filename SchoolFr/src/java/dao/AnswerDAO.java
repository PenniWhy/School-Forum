package dao;

import model.Answer;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDAO {

    public int addAnswer(Answer ans) {
        String sql = "INSERT INTO Answers (QuestionID, UserID, Content, IsAnonymous, CreatedAt) "
                + "VALUES (?, ?, ?, ?, GETDATE())";

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ans.getQuestionId());
            ps.setInt(2, ans.getUserId()); // luôn có user
            ps.setString(3, ans.getContent());
            ps.setBoolean(4, ans.isAnonymous());

            int affected = ps.executeUpdate();

            if (affected > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return -1;
    }

    // ✅ Lấy danh sách câu trả lời cho một câu hỏi
    public List<Answer> getAnswersByQuestionId(int questionId) {
        List<Answer> list = new ArrayList<>();

        String sql = "SELECT a.AnswerID, a.Content, a.CreatedAt, a.UserID, a.IsAnonymous, "
                + "u.FullName AS AuthorName "
                + "FROM Answers a "
                + "LEFT JOIN Users u ON a.UserID = u.UserID "
                + "WHERE a.QuestionID = ? "
                + "ORDER BY a.CreatedAt ASC";

        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, questionId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Answer a = new Answer();

                a.setAnswerId(rs.getInt("AnswerID"));
                a.setContent(rs.getString("Content"));
                a.setCreatedAt(rs.getTimestamp("CreatedAt"));

                Integer userId = rs.getInt("UserID");
                if (rs.wasNull()) {
                    userId = null;
                }
                a.setUserId(userId);

                // 🔥 QUAN TRỌNG
                a.setAnonymous(rs.getBoolean("IsAnonymous"));

                a.setAuthorName(rs.getString("AuthorName"));

                list.add(a);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public Answer findById(int id) {
        String sql = "SELECT a.AnswerID, a.QuestionID, a.Content, a.UserID, a.CreatedAt, a.IsAnonymous, "
                + "u.FullName AS AuthorName "
                + "FROM Answers a "
                + "LEFT JOIN Users u ON a.UserID = u.UserID "
                + "WHERE a.AnswerID = ?";

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Answer a = new Answer();

                a.setAnswerId(rs.getInt("AnswerID"));
                a.setQuestionId(rs.getInt("QuestionID"));
                a.setContent(rs.getString("Content"));

                Integer userId = rs.getInt("UserID");
                if (rs.wasNull()) {
                    userId = null;
                }
                a.setUserId(userId);

                // 🔥 QUAN TRỌNG
                a.setAnonymous(rs.getBoolean("IsAnonymous"));

                a.setCreatedAt(rs.getTimestamp("CreatedAt"));
                a.setAuthorName(rs.getString("AuthorName"));

                return a;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ Xoá câu trả lời
    public boolean delete(int id) {
        String sql = "DELETE FROM Answers WHERE AnswerID = ?";
        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteByQuestionId(int questionId) {
        String sql = "DELETE FROM Answers WHERE QuestionID = ?";

        try (Connection con = DBUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, questionId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
