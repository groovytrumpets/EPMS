/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller.common;

import DAO.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends DBContext {

    public List<User> getUsersByRole(int roleId) throws SQLException {
        List<User> list = new ArrayList<>();
        String sql = "SELECT * FROM [User] WHERE RoleId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, roleId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            User u = new User();
            u.setUserId(rs.getInt("UserId"));
            u.setUserName(rs.getString("Username"));
            u.setRoleId(rs.getInt("RoleId"));
            u.setStatus(rs.getString("Status"));
            u.setEmail(rs.getString("Email"));
            u.setFullName(rs.getString("FullName"));
            // Add other fields if needed
            list.add(u);
        }
        return list;
    }
}

