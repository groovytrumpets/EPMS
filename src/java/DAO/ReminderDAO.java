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
import utilities.EmailSender;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class ReminderDAO extends DBContext{

    public void checkAndSendReminderEmails() {
        List<String> emailList = new ArrayList<>();
        String sql = """
                     SELECT Email, FullName FROM [User]
                     WHERE RoleId = 3
                       AND DATEDIFF(DAY, CreateDate, GETDATE()) >= 7
                       AND UserId NOT IN (SELECT DISTINCT UserId FROM WorkSchedule)
                     """;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                emailList.add(rs.getString("Email"));
            }
            if (!emailList.isEmpty()) {
            String subject = "Reminder to Create Your Work Schedule";
            String body = """
                Dear employee,
                
                Our system has recorded that you created your account more than 7 days ago, but you have not yet created any work schedule.
                
                Please log in to the system and create your work schedule as soon as possible.
                
                Best regards,
                EPMS System
            """;

            EmailSender.sendNotificationEmailBCC(emailList, subject, body);
            System.out.println("Reminder to " + emailList.size() + " User.");
        } else {
            System.out.println("No user need Reminder.");
        }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public static void main(String[] args) {
        ReminderDAO rmd = new ReminderDAO();
        rmd.checkAndSendReminderEmails();
    }
    
}
