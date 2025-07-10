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
}
