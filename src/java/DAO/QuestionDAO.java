package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Question;

public class QuestionDAO extends DBContext {

    public void insertQuestion(Question q) throws SQLException {
        String sql = """
                INSERT INTO Question
                (Question, Answer, Status, TestId)
                VALUES (?, ?, ?, ?)
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, q.getQuestion());
        ps.setInt(2, q.getAnswer());
        ps.setString(3, q.getStatus());
        ps.setInt(4, q.getTestId());
        ps.executeUpdate();
    }
    public List<Question> getQuestionsByTestId(int testId) throws SQLException {
    List<Question> list = new ArrayList<>();
    String sql = "SELECT * FROM Question WHERE TestId = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, testId);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Question q = new Question(
                rs.getInt("QuestionId"),
                rs.getString("Question"),
                rs.getInt("Answer"),
                rs.getString("Status"),
                rs.getInt("TestId")
        );
        list.add(q);
    }
    return list;
}

}
