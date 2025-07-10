package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Document;
import java.time.LocalDateTime;

public class DocumentDAO extends DBContext {

    public void insertDocument(Document doc) throws SQLException {
        String sql = "INSERT INTO Document (Title, FileLink, Type, Status, UserId) "
                   + "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, doc.getTitle());
        ps.setString(2, doc.getFileLink());
        ps.setString(3, doc.getType());
        ps.setString(4, doc.getStatus());
        ps.setInt(5, doc.getUserId());
        ps.executeUpdate();
    }
    
    public List<Document> getDocumentsByUser(int userId) throws SQLException {
        List<Document> list = new ArrayList<>();
        String sql = "SELECT * FROM Document WHERE UserId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            LocalDateTime uploadDate = null;
            Timestamp ts = rs.getTimestamp("UploadDate");
            if (ts != null) {
                uploadDate = ts.toLocalDateTime();
            }
            
            Document doc = new Document(
                rs.getInt("DocumentId"),
                rs.getString("FileLink"),
                rs.getString("Type"),
                rs.getString("Status"),
                rs.getString("Title"),
                uploadDate,
                rs.getInt("UserId")
            );
            list.add(doc);
        }
        return list;
    }
    
    public Document getDocumentById(int documentId) throws SQLException {
        String sql = "SELECT * FROM Document WHERE DocumentId = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, documentId);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            LocalDateTime uploadDate = null;
            Timestamp ts = rs.getTimestamp("UploadDate");
            if (ts != null) {
                uploadDate = ts.toLocalDateTime();
            }
            
            return new Document(
                rs.getInt("DocumentId"),
                rs.getString("FileLink"),
                rs.getString("Type"),
                rs.getString("Status"),
                rs.getString("Title"),
                uploadDate,
                rs.getInt("UserId")
            );
        }
        return null;
    }
}
