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
 * @author Acer
 */
public class UserDAO extends DBContext {

    public User findUserByEmail(String email) {
        String sql = "SELECT [UserId], [Username], [Password], [Status], [FullName], "
                + "[Phone], [CreateDate], [Dob], [Email], [Gender], [RoleId] "
                + "FROM [EPMS].[dbo].[User] "
                + "WHERE Email = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email); // Gán giá trị username vào dấu ?
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setUserName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setStatus(rs.getString("Status"));
                user.setFullName(rs.getString("FullName"));
                user.setPhone(rs.getString("Phone"));
                user.setCreateDate(rs.getTimestamp("CreateDate").toLocalDateTime());
                user.setDob(rs.getDate("Dob"));
                user.setEmail(rs.getString("Email"));
                user.setGender(rs.getString("Gender"));
                user.setRoleId(rs.getInt("RoleId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User findUserPass(String username, String password) {
        String sql = "SELECT [UserId], [Username], [Password], [Status], [FullName], "
                + "[Phone], [CreateDate], [Dob], [Email], [Gender], [RoleId] "
                + "FROM [EPMS].[dbo].[User] "
                + "WHERE Email = ? AND Password = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            st.setString(2, password);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("UserId"));
                user.setUserName(rs.getString("Username"));
                user.setPassword(rs.getString("Password"));
                user.setStatus(rs.getString("Status"));
                user.setFullName(rs.getString("FullName"));
                user.setPhone(rs.getString("Phone"));
                user.setCreateDate(rs.getTimestamp("CreateDate").toLocalDateTime());
                user.setDob(rs.getDate("Dob"));
                user.setEmail(rs.getString("Email"));
                user.setGender(rs.getString("Gender"));
                user.setRoleId(rs.getInt("RoleId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePasswordByEmail(String email, String newPassword) {
        String sql = "UPDATE [User] SET Password = ? WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newPassword);  // Lưu ý: nên đã mã hóa trước khi truyền vào
            st.setString(2, email);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateStatusByEmail(String email, String newStatus) {
        String sql = "UPDATE [User] SET Status = ? WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, newStatus);  // newStatus: e.g., "verified", "active", "banned"
            st.setString(2, email);
            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserProfileByEmail(String email, String userName, String fullName,
            String phone, java.util.Date dob, String gender) {
        String sql = "UPDATE [User] SET Username = ?, FullName = ?, Phone = ?, Dob = ?, Gender = ? WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, userName);
            st.setString(2, fullName);
            st.setString(3, phone);

            // Convert util.Date to sql.Date
            java.sql.Date sqlDob = new java.sql.Date(dob.getTime());
            st.setDate(4, sqlDob);

            st.setString(5, gender);
            st.setString(6, email);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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

    public boolean isUsernameExists(String username) {
        String sql = "SELECT 1 FROM [User] WHERE Username = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String sql = "SELECT 1 FROM [User] WHERE Email = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT 1 FROM [User] WHERE Phone = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, phone);
            ResultSet rs = st.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int insertUser(User user) {
        String sql = "INSERT INTO [User] (Username, Password, Status, FullName, Phone, Email, RoleId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            st.setString(1, user.getUserName());
            st.setString(2, user.getPassword());
            st.setString(3, user.getStatus());
            st.setString(4, user.getFullName());
            st.setString(5, user.getPhone());
            st.setString(6, user.getEmail());
            st.setInt(7, user.getRoleId()); // Bạn cần set RoleId phù hợp (ví dụ: 3 cho candidate)
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void activateUser(int userId) {
        String sql = "UPDATE [User] SET Status = 'Active' WHERE UserId = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Tạo DAO hoặc class chứa hàm
        UserDAO dao = new UserDAO(); // Giả định class tên là UserDAO

        // Gọi hàm kiểm tra đăng nhập
        User user = dao.findUserPass("takashi.kato@example.com", "e10adc3949ba59abbe56e057f20f883e");

        if (user != null) {
            System.out.println("✅ Đăng nhập thành công:");
            System.out.println("Tên người dùng: " + user.getFullName());
            System.out.println("Email: " + user.getEmail());
            System.out.println("Vai trò (RoleId): " + user.getRoleId());
        } else {
            System.out.println("❌ Đăng nhập thất bại. Sai email hoặc mật khẩu.");
        }
    }

}
