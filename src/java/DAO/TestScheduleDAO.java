package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.TestSchedule;
import model.TestSession;

public class TestScheduleDAO extends DBContext {

    public int createTestSchedule(int userId, String status) throws SQLException {
        String sql = "INSERT INTO TestSchedule (UserId, Status) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, userId);
        ps.setString(2, status);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public List<TestSession> getTestsByUserId(int userId) throws SQLException {
        List<TestSession> list = new ArrayList<>();
        String sql = """
                SELECT T.*
                FROM TestSession T
                JOIN TestSchedule TS ON T.TestScheduleId = TS.TestScheduleId
                WHERE TS.UserId = ?
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            TestSession test = new TestSession(
                    rs.getInt("TestId"),
                    rs.getInt("TestScheduleId"),
                    rs.getString("Title"),
                    rs.getInt("Timer"),
                    rs.getTimestamp("Deadline"),
                    rs.getInt("Mark"),
                    rs.getString("Status")
            );
            list.add(test);
        }
        return list;
    }
    
}
