package dao;

import model.Question;
import util.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDAO {

    public List<Question> findAll(String keyword) {
        List<Question> list = new ArrayList<>();

        String sql = " SELECT q.QuestionID, q.Title, q.Content, q.UserID, q.CreatedAt, q.IsAnonymous, u.FullName AS AuthorName "
                + "FROM Questions q "
                + "LEFT JOIN Users u ON q.UserID = u.UserID ";

        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();

        if (hasKeyword) {
            sql += "WHERE q.Title LIKE ? OR q.Content LIKE ? ";
        }

        sql += "ORDER BY q.CreatedAt DESC";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            if (hasKeyword) {
                String k = "%" + keyword.trim() + "%";
                ps.setString(1, k);
                ps.setString(2, k);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("QuestionID"));
                q.setTitle(rs.getString("Title"));
                q.setContent(rs.getString("Content"));

                Integer userId = rs.getInt("UserID");
                if (rs.wasNull()) {
                    userId = null;
                }
                q.setUserId(userId);
                q.setAnonymous(rs.getBoolean("IsAnonymous"));
                q.setAuthorName(rs.getString("AuthorName"));
                q.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Lấy câu hỏi theo ID
    public Question findById(int id) {
       String sql = "SELECT q.QuestionID, q.Title, q.Content, q.UserID, q.CreatedAt, q.IsAnonymous, u.FullName "
           + "FROM Questions q LEFT JOIN Users u ON q.UserID = u.UserID WHERE q.QuestionID=?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("QuestionID"));
                q.setTitle(rs.getString("Title"));
                q.setContent(rs.getString("Content"));

                Integer userId = rs.getInt("UserID");
                if (rs.wasNull()) {
                    userId = null;
                }
                q.setUserId(userId);
                q.setAnonymous(rs.getBoolean("IsAnonymous"));
                q.setCreatedAt(rs.getTimestamp("CreatedAt"));

                String authorName = rs.getString("FullName");
                if (authorName == null) {
                    authorName = "me";
                }
                q.setAuthorName(authorName);

                return q;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Thêm câu hỏi, userId có thể null nếu ẩn danh
    public boolean add(String title, String content, int userId, boolean isAnonymous) {
        String sql = "INSERT INTO Questions (Title, Content, UserID, IsAnonymous, CreatedAt) VALUES (?, ?, ?, ?, GETDATE())";

        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, userId);          // luôn có userId
            ps.setBoolean(4, isAnonymous); // thêm cột mới

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa câu hỏi theo ID và userId
    public boolean deleteQuestion(int questionId, int userId) {
        String sql = "DELETE FROM Questions WHERE QuestionID = ? AND UserID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, questionId);
            ps.setInt(2, userId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy câu hỏi theo ID (không join Users)
    public Question getQuestionById(int id) {
        String sql = "SELECT * FROM Questions WHERE QuestionID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Question q = new Question();
                q.setQuestionId(rs.getInt("QuestionID"));

                Integer userId = rs.getInt("UserID");
                if (rs.wasNull()) {
                    userId = null;
                }
                q.setUserId(userId);

                q.setTitle(rs.getString("Title"));
                q.setContent(rs.getString("Content"));
                q.setCreatedAt(rs.getTimestamp("CreatedAt"));

                return q;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cập nhật câu hỏi
    public boolean updateQuestion(int id, int userId, String title, String content) {
        String sql = "UPDATE Questions SET Title = ?, Content = ? WHERE QuestionID = ? AND UserID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, content);
            ps.setInt(3, id);
            ps.setInt(4, userId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Admin xóa
    public boolean deleteById(int questionId) {
        String sql = "DELETE FROM Questions WHERE QuestionID = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, questionId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
