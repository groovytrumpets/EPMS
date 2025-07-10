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
import java.sql.Timestamp;
import model.FormTemplate;
import model.Role;
import model.User;

public class AdminDAO extends DBContext {

    public List<User> getAllNonAdminUsers() throws SQLException {
        String sql = "SELECT u.*, r.RoleName, r.Status AS RoleStatus "
                + "FROM [User] u "
                + "JOIN [Role] r ON u.RoleId = r.RoleId "
                + "WHERE r.RoleName != 'Admin'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<User> list = new ArrayList<>();
        while (rs.next()) {
            list.add(extractUser(rs));
        }
        return list;
    }

    public User getUserById(int id) throws SQLException {
        String sql = "SELECT u.*, r.RoleName, r.Status FROM [User] u JOIN Role r ON u.RoleId = r.RoleId WHERE UserId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return extractUser(rs);
        }
        return null;
    }

    public void updateUser(User u) throws SQLException {
        String sql = "UPDATE [User] SET FullName=?, Email=?, Phone=?, Gender=?, Dob=?, RoleId=?, Status=? WHERE UserId=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, u.getFullName());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getPhone());
        ps.setString(4, u.getGender());
        ps.setDate(5, new java.sql.Date(u.getDob().getTime()));
        ps.setInt(6, u.getRoleId());
        ps.setString(7, u.getStatus());
        ps.setInt(8, u.getUserId());
        ps.executeUpdate();
    }

    public void updateUserProfile(int userId, String username, String fullName, String email, String phone, String gender) throws SQLException {
        String sql = "UPDATE [User] SET UserName = ?, FullName = ?, Email = ?, Phone = ?, Gender = ? WHERE UserId = ?";
        try (
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, fullName);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, gender);
            ps.setInt(6, userId);
            ps.executeUpdate();
        }
    }

    public void updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE [User] SET Status=? WHERE UserId=?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    public List<Role> getAllRoles() throws SQLException {
        String sql = "SELECT * FROM Role WHERE RoleName != 'Admin'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        List<Role> roles = new ArrayList<>();
        while (rs.next()) {
            Role r = new Role();
            r.setRoleId(rs.getInt("RoleId"));
            r.setRoleName(rs.getString("RoleName"));
            r.setStatus(rs.getString("Status"));
            roles.add(r);
        }
        return roles;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User u = new User();
        u.setUserId(rs.getInt("UserId"));
        u.setUserName(rs.getString("Username"));
        u.setFullName(rs.getString("FullName"));
        u.setEmail(rs.getString("Email"));
        u.setPhone(rs.getString("Phone"));
        u.setPassword(rs.getString("Password"));
        u.setGender(rs.getString("Gender"));
        u.setDob(rs.getDate("Dob"));

        Timestamp ts = rs.getTimestamp("CreateDate");
        if (ts != null) {
            u.setCreateDate(ts.toLocalDateTime());
        }

        u.setStatus(rs.getString("Status"));

        Role role = new Role();
        role.setRoleId(rs.getInt("RoleId"));
        role.setRoleName(rs.getString("RoleName"));
        role.setStatus(rs.getString("RoleStatus"));

        u.setRole(role);
        return u;
    }

    public List<Object[]> getUserCreationStats() throws SQLException {
        List<Object[]> stats = new ArrayList<>();

        String sql = """
        SELECT 
            YEAR(CreateDate) AS Year,
            MONTH(CreateDate) AS Month,
            COUNT(*) AS Total
        FROM [User]
        WHERE RoleId != (SELECT RoleId FROM Role WHERE RoleName = 'Admin')
        GROUP BY YEAR(CreateDate), MONTH(CreateDate)
        ORDER BY Year, Month
    """;

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Object[] row = new Object[3];
            row[0] = rs.getInt("Year");
            row[1] = rs.getInt("Month");
            row[2] = rs.getInt("Total");
            stats.add(row);
        }

        return stats;
    }

    public void updateRole(int userId, int roleId) throws SQLException {
        String sql = "UPDATE [User] SET RoleId = ? WHERE UserId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, roleId);
        ps.setInt(2, userId);
        ps.executeUpdate();
    }

    public void insertFormTemplate(FormTemplate template) throws Exception {
        String sql = "INSERT INTO FormTemplate (Status, CreateDate, DownloadCount, FileLink, Title, UserId) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, template.getStatus());
            ps.setTimestamp(2, Timestamp.valueOf(template.getCreateDate()));
            ps.setInt(3, template.getDownloadCount());
            ps.setString(4, template.getFileLink());
            ps.setString(5, template.getTitle());
            ps.setInt(6, template.getUserId());

            ps.executeUpdate();
        }
    }
}
