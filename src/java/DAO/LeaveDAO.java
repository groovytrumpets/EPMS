package DAO;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.LeaveRequest;

public class LeaveDAO extends DBContext {
    // Lấy số ngày phép còn lại của employee
    public int getLeaveBalance(int userId) {
        String sql = "SELECT leaveBalance FROM [User] WHERE userId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("leaveBalance");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return 0;
    }

    // Tạo đơn xin nghỉ phép
    public boolean createLeaveRequest(LeaveRequest req) {
        String sql = "INSERT INTO LeaveRequest (userId, startDate, endDate, reason, status, createdDate) VALUES (?, ?, ?, ?, 'PENDING', ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, req.getUserId());
            st.setDate(2, Date.valueOf(req.getStartDate()));
            st.setDate(3, Date.valueOf(req.getEndDate()));
            st.setString(4, req.getReason());
            st.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Lấy tất cả đơn xin nghỉ phép (cho HR)
    public List<LeaveRequest> getAllLeaveRequests() {
        List<LeaveRequest> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaveRequest ORDER BY createdDate DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                LeaveRequest lr = new LeaveRequest();
                lr.setId(rs.getInt("id"));
                lr.setUserId(rs.getInt("userId"));
                lr.setStartDate(rs.getDate("startDate").toLocalDate());
                lr.setEndDate(rs.getDate("endDate").toLocalDate());
                lr.setReason(rs.getString("reason"));
                lr.setStatus(rs.getString("status"));
                lr.setCreatedDate(rs.getTimestamp("createdDate").toLocalDateTime());
                lr.setApprovedBy((Integer)rs.getObject("approvedBy"));
                lr.setApprovedDate(rs.getTimestamp("approvedDate") != null ? rs.getTimestamp("approvedDate").toLocalDateTime() : null);
                lr.setNote(rs.getString("note"));
                list.add(lr);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // HR duyệt đơn
    public boolean approveLeaveRequest(int requestId, int hrId, String note) {
        String sql = "UPDATE LeaveRequest SET status='APPROVED', approvedBy=?, approvedDate=?, note=? WHERE id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, hrId);
            st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            st.setString(3, note);
            st.setInt(4, requestId);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // HR từ chối đơn
    public boolean rejectLeaveRequest(int requestId, int hrId, String note) {
        String sql = "UPDATE LeaveRequest SET status='REJECTED', approvedBy=?, approvedDate=?, note=? WHERE id=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, hrId);
            st.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            st.setString(3, note);
            st.setInt(4, requestId);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
} 