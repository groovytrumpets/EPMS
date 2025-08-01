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
import model.Document;
import model.FormSubmission;
import model.User;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class HRDAO extends DBContext {

    public List<User> getListOfCandidateUsers() {
        List<User> candidateList = new ArrayList<>();
        String sql = "select * from [User] where RoleId=4 or RoleId=3";
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
            if (!candidateList.isEmpty()) {
                System.out.println(candidateList.get(0).getFullName());
            }
            return candidateList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return candidateList;
    }

    public List<User> getListOfEmployeeAndCandidateUsers() {
        List<User> candidateList = new ArrayList<>();
        String sql = "select * from [User] where RoleId=3 or RoleId=4;";
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
            System.out.println(candidateList.get(0).getFullName());
            return candidateList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<FormSubmission> getAllFormSubmissions() {
        List<FormSubmission> list = new ArrayList<>();
        String sql = "SELECT [SubmissionId], [Type], [Purpose], [Status], [CreateDate], [Note], [FileLink], [UserId] FROM FormSubmission";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                FormSubmission form = new FormSubmission();
                form.setSubmissionId(rs.getInt("SubmissionId"));
                form.setType(rs.getString("Type"));
                form.setPurpose(rs.getString("Purpose"));
                form.setStatus(rs.getString("Status"));
                form.setCreateDate(rs.getTimestamp("CreateDate").toLocalDateTime());
                form.setNote(rs.getString("Note"));
                form.setFileLink(rs.getString("FileLink"));
                form.setUserId(rs.getInt("UserId"));
                list.add(form);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Object[]> getAllLeaveRequestsWithUserInfo() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT fs.SubmissionId, u.FullName, u.Email, u.Phone, "
                + "fs.Purpose, fs.FileLink, fs.Status, fs.UserId "
                + "FROM FormSubmission fs "
                + "JOIN [User] u ON fs.UserId = u.UserId "
                + "WHERE fs.Type = 'Leave Request'";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("SubmissionId");
                row[1] = rs.getString("FullName");
                row[2] = rs.getString("Email");
                row[3] = rs.getString("Phone");
                row[4] = rs.getString("Purpose");
                row[5] = rs.getString("FileLink");
                row[6] = rs.getString("Status");
                row[7] = rs.getInt("UserId");
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void updateLeaveRequestStatus(int submissionId, String status) {
        String sql = "UPDATE FormSubmission SET Status = ? WHERE SubmissionId = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, status);
            st.setInt(2, submissionId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Document> getListOfCV() {
        List<Document> CVList = new ArrayList<>();
        String sql = "select * from Document where Type='CV' and Status='submitted'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Document CV = new Document();
                CV.setDocumentId(rs.getInt("documentId"));
                CV.setTitle(rs.getString("title"));
                CV.setFileLink(rs.getString("fileLink"));
                CV.setType(rs.getString("type"));
                CV.setStatus(rs.getString("status"));
                CV.setUploadDate(rs.getTimestamp("uploadDate").toLocalDateTime());
                CV.setUserId(rs.getInt("userId"));
                CVList.add(CV);
            }
            return CVList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public List<Document> getAllListOfCV() {
        List<Document> CVList = new ArrayList<>();
        String sql = "select * from Document where Type='CV'";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Document CV = new Document();
                CV.setDocumentId(rs.getInt("documentId"));
                CV.setTitle(rs.getString("title"));
                CV.setFileLink(rs.getString("fileLink"));
                CV.setType(rs.getString("type"));
                CV.setStatus(rs.getString("status"));
                CV.setUploadDate(rs.getTimestamp("uploadDate").toLocalDateTime());
                CV.setUserId(rs.getInt("userId"));
                CVList.add(CV);
            }
            return CVList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public void changeCVStatusReject(int cvId) {
        String sql = "update Document set Status = 'rejected' where Type='CV' and DocumentId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cvId);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void changeCVStatusApprove(int cvId) {
        String sql = "update Document set Status = 'approved' where Type='CV' and DocumentId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cvId);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void hrCreateAccount(int userId, String password) {
        String sql = "update [User] set Status ='verified',Password=? where UserId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setInt(2, userId);
            st.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public User getUserbyId(int userId) {
        String sql = "select * from [User] where UserId=?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User newUser = new User();
                newUser.setUserId(rs.getInt("userId"));
                newUser.setUserName(rs.getString("username"));
                newUser.setPassword(rs.getString("password"));
                newUser.setStatus(rs.getString("status"));
                newUser.setFullName(rs.getString("fullName"));
                newUser.setPhone(rs.getString("phone"));
                newUser.setCreateDate(rs.getTimestamp("createDate").toLocalDateTime());
                newUser.setDob(rs.getDate("dob"));
                newUser.setEmail(rs.getString("email"));
                newUser.setGender(rs.getString("gender"));
                newUser.setRoleId(rs.getInt("roleId"));
                return newUser;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
        HRDAO dao = new HRDAO();
        List<Object[]> list = dao.getAllLeaveRequestsWithUserInfo();

        if (list.isEmpty()) {
            System.out.println("❌ No leave requests found.");
        } else {
            System.out.println("✅ Leave Requests:");
            for (Object[] row : list) {
                System.out.println("------------------------------------");
                System.out.println("Submission ID : " + row[0]);
                System.out.println("Full Name     : " + row[1]);
                System.out.println("Email         : " + row[2]);
                System.out.println("Phone         : " + row[3]);
                System.out.println("Purpose       : " + row[4]);
                System.out.println("File Link     : " + row[5]);
                System.out.println("Status        : " + row[6]);
                System.out.println("User ID       : " + row[7]);
            }
        }
    }
}
