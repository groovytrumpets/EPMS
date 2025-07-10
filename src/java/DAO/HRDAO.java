/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class HRDAO extends DBContext {

    public List<User> getListOfCandidateUsers() {
        List<User> candidateList = new ArrayList<>();
        String sql = "select * from [User] where RoleId=4;";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User can = new User();
                can.setUserId(rs.getInt("userId"));
                can.setUserName(rs.getString("username"));
                can.setPassword(rs.getString("password"));
                can.setStatus(rs.getString("status"));
                can.setFullName(rs.getString("fullName"));
                can.setPhone(rs.getString("phone"));
                can.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
                can.setDob(rs.getDate("dob"));
                can.setEmail(rs.getString("email"));
                can.setGender(rs.getString("gender"));
                can.setRoleId(rs.getInt("roleId"));
                candidateList.add(can);
            }
            return candidateList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean addCandidate(String fullName, String email, String phone, String password, String cvFileName) {
        String sql = "INSERT INTO [User] (username, password, status, fullName, phone, createDate, email, roleId) VALUES (?, ?, ?, ?, ?, GETDATE(), ?, 4)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email); // username là email
            st.setString(2, password);
            st.setString(3, cvFileName); // status tạm lưu tên file CV
            st.setString(4, fullName);
            st.setString(5, phone);
            st.setString(6, email);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE username = ? OR email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE phone = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, phone);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Thêm mới bản ghi FormSubmission
    public boolean addFormSubmission(model.FormSubmission submission) {
        String sql = "INSERT INTO FormSubmission (type, purpose, status, note, fileLink, createDate, userId) VALUES (?, ?, ?, ?, ?, GETDATE(), ?)";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, submission.getType());
            st.setString(2, submission.getPurpose());
            st.setString(3, submission.getStatus());
            st.setString(4, submission.getNote());
            st.setString(5, submission.getFileLink());
            st.setInt(6, submission.getUserId());
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    // Lấy userId theo email (username)
    public int getUserIdByEmail(String email) {
        String sql = "SELECT userId FROM [User] WHERE username = ? OR email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt("userId");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return -1;
    }

    // Lấy danh sách FormSubmission theo type
    public List<model.FormSubmission> getFormSubmissionsByType(String type) {
        List<model.FormSubmission> list = new ArrayList<>();
        String sql = "SELECT * FROM FormSubmission WHERE type = ? ORDER BY createDate DESC";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, type);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                model.FormSubmission f = new model.FormSubmission();
                f.setSubmissionId(rs.getInt("submissionId"));
                f.setType(rs.getString("type"));
                f.setPurpose(rs.getString("purpose"));
                f.setStatus(rs.getString("status"));
                f.setNote(rs.getString("note"));
                f.setFileLink(rs.getString("fileLink"));
                f.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
                f.setUserId(rs.getInt("userId"));
                list.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    // Cập nhật trạng thái và ghi chú FormSubmission
    public boolean updateFormSubmissionStatus(int submissionId, String status, String note) {
        String sql = "UPDATE FormSubmission SET status = ?, note = ? WHERE submissionId = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, status);
            st.setString(2, note);
            st.setInt(3, submissionId);
            int rows = st.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
