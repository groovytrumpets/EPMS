package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.FormSubmission;

public class FormSubmissionDAO extends DBContext {

    public FormSubmissionDAO() {
        super();
    }

    public FormSubmission getSubmissionById(int id) {
        String sql = "SELECT * FROM FormSubmission WHERE SubmissionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    FormSubmission form = new FormSubmission();
                    form.setSubmissionId(rs.getInt("SubmissionId"));
                    form.setType(rs.getString("Type"));
                    form.setPurpose(rs.getString("Purpose"));
                    form.setStatus(rs.getString("Status"));
                    form.setNote(rs.getString("Note"));
                    form.setFileLink(rs.getString("FileLink"));

                    Timestamp ts = rs.getTimestamp("CreateDate");
                    if (ts != null) {
                        form.setCreateDate(ts.toLocalDateTime());
                    }

                    form.setUserId(rs.getInt("UserId"));
                    return form;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // nếu không tìm thấy
    }

    public void insertFormSubmission(FormSubmission form) {
        String sql = "INSERT INTO FormSubmission (type, purpose, status, note, fileLink, createDate, userId) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, form.getType());
            ps.setString(2, form.getPurpose());
            ps.setString(3, form.getStatus());
            ps.setString(4, form.getNote());
            ps.setString(5, form.getFileLink());
            LocalDateTime createDate = form.getCreateDate();
            if (createDate != null) {
                ps.setTimestamp(6, Timestamp.valueOf(createDate));
            } else {
                ps.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            }
            ps.setInt(7, form.getUserId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Object[]> getPendingSubmissionsWithUser() {
        List<Object[]> list = new ArrayList<>();
        String sql = "SELECT f.*, u.FullName, u.Email, u.Phone, u.Status as UserStatus FROM FormSubmission f JOIN [User] u ON f.UserId = u.UserId WHERE f.Status = 'Chờ duyệt'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Object[] row = new Object[8];
                row[0] = rs.getInt("SubmissionId");
                row[1] = rs.getString("FullName");
                row[2] = rs.getString("Email");
                row[3] = rs.getString("Phone");
                row[4] = rs.getString("UserStatus");
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

    public void approveSubmission(int submissionId) {
        String sql = "UPDATE FormSubmission SET Status = 'Đã duyệt' WHERE SubmissionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, submissionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rejectSubmission(int submissionId) {
        String sql = "UPDATE FormSubmission SET Status = 'Từ chối' WHERE SubmissionId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, submissionId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // 1. Tạo đối tượng mẫu
        FormSubmission form = new FormSubmission();
        form.setType("Leave Request");
        form.setPurpose("Nghỉ phép vì lý do cá nhân");
        form.setStatus("pending");
        form.setNote("Tôi xin nghỉ 2 ngày cuối tuần.");
        form.setFileLink("uploads/test_leave_file.pdf");
        form.setCreateDate(LocalDateTime.now());
        form.setUserId(2);  // đảm bảo ID này tồn tại trong bảng User

        // 2. Gọi DAO để insert
        FormSubmissionDAO dao = new FormSubmissionDAO();  // đảm bảo connection hoạt động

        try {
            dao.insertFormSubmission(form);
            System.out.println("✅ Insert thành công!");
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi insert: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
