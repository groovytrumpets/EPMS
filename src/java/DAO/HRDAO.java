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
            System.out.println(CVList.get(0).getDocumentId());
            return CVList;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }
    public static void main(String[] args) {
        HRDAO hrd = new HRDAO();
        System.out.println(hrd.getListOfCV().get(0).getFileLink());

        
        
    }
}
