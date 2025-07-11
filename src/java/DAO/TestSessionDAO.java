package DAO;

import java.sql.*;
import model.TestSession;

public class TestSessionDAO extends DBContext {

    public int insertTestSession(TestSession test) throws SQLException {
        String sql = """
                INSERT INTO TestSession
                (TestScheduleId, Title, Timer, Deadline, Mark, Status)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, test.getTestScheduleId());
        ps.setString(2, test.getTitle());
        ps.setInt(3, test.getTimer());
        ps.setTimestamp(4, test.getDeadline());
        ps.setInt(5, test.getMark());
        ps.setString(6, test.getStatus());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }
    public TestSession getTestById(int testId) throws SQLException {
    String sql = "SELECT * FROM TestSession WHERE TestId = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, testId);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        return new TestSession(
                rs.getInt("TestId"),
                rs.getInt("TestScheduleId"),
                rs.getString("Title"),
                rs.getInt("Timer"),
                rs.getTimestamp("Deadline"),
                rs.getInt("Mark"),
                rs.getString("Status")
        );
    }
    return null;
}
    public void updateMarkAndStatus(int testId, int mark, String status) throws SQLException {
    String sql = "UPDATE TestSession SET Mark = ?, Status = ? WHERE TestId = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, mark);
    ps.setString(2, status);
    ps.setInt(3, testId);
    ps.executeUpdate();
}


}
