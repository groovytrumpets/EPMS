package DAO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import model.FormTemplate;

public class FormTemplateDAO extends DBContext {
    
public FormTemplate getFormById(int id) throws SQLException {
    String sql = "SELECT * FROM FormTemplate WHERE TemplateId = ?";
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setInt(1, id);
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
        Timestamp ts = rs.getTimestamp("CreateDate");
        LocalDateTime createDate = null;
        if (ts != null) {
            createDate = ts.toLocalDateTime();
        }

        return new FormTemplate(
                rs.getInt("TemplateId"),
                rs.getString("Status"),
                createDate,
                rs.getInt("DownloadCount"),
                rs.getString("FileLink"),
                rs.getString("Title"),
                rs.getInt("UserId")
        );
    }
    return null;
}

public List<FormTemplate> getAllForms() throws SQLException {
    List<FormTemplate> list = new ArrayList<>();
    String sql = "SELECT * FROM FormTemplate";
    PreparedStatement ps = connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
        Timestamp ts = rs.getTimestamp("CreateDate");
        LocalDateTime createDate = null;
        if (ts != null) {
            createDate = ts.toLocalDateTime();
        }
        FormTemplate form = new FormTemplate(
            rs.getInt("TemplateId"),
            rs.getString("Status"),
            createDate,
            rs.getInt("DownloadCount"),
            rs.getString("FileLink"),
            rs.getString("Title"),
            rs.getInt("UserId")
        );
        list.add(form);
    }
    return list;
}

    
    public void updateDownloadCount(int id) throws SQLException {
        String sql = "UPDATE FormTemplate SET DownloadCount = DownloadCount + 1 WHERE TemplateId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
}
