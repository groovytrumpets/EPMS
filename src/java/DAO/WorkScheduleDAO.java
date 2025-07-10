/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import model.WorkSchedule;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class WorkScheduleDAO extends DBContext{

    public boolean addSlot(WorkSchedule schedule) {
        String sql = """
                     INSERT INTO [dbo].[WorkSchedule]
                                ([Status]
                                ,[StartDate]
                                ,[EndDate]
                                ,[RemainLeave]
                                ,[WorkDay]
                                ,[UserId])
                          VALUES
                                (?,?,?,?,?,?)
                     """;
        try (PreparedStatement rs = connection.prepareStatement(sql)) {
            rs.setString(1, schedule.getStatus());
            rs.setTimestamp(2, Timestamp.valueOf(schedule.getStartDate())); // LocalDateTime → SQL Timestamp
            rs.setTimestamp(3, Timestamp.valueOf(schedule.getEndDate()));
            rs.setInt(4, schedule.getRemainLeave());
            rs.setInt(5, schedule.getWorkDay());
            rs.setInt(6, schedule.getUserId());
            
            int rowsInserted = rs.executeUpdate();
            return rowsInserted > 0; // Returns true if the payment was added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();
        LocalTime time = LocalTime.of(8, 0);
        LocalDateTime result = LocalDateTime.of(today, time);
        WorkScheduleDAO wsd = new WorkScheduleDAO();
        WorkSchedule schedule = new WorkSchedule();
        schedule.setStatus("pending");
        schedule.setStartDate(result);
        schedule.setEndDate(result.plusYears(1));
        schedule.setRemainLeave(12);      
        schedule.setWorkDay(0);          
        schedule.setUserId(8);
        wsd.addSlot(schedule);
    }

    public List<WorkSchedule> getListofPendingSchedule() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
