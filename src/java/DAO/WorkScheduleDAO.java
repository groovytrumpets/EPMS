/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.WorkSchedule;

/**
 *
 * @author groovytrumpets <nguyennamkhanhnnk@gmail.com>
 */
public class WorkScheduleDAO extends DBContext {

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

    public List<WorkSchedule> getListofPendingSchedule(int id) {
        List<WorkSchedule> workSchedules = new ArrayList<>();
        String sql = "select * from WorkSchedule where UserId=? and Status = 'pending'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                WorkSchedule slot = new WorkSchedule();
                slot.setWorkScheduleId(rs.getInt("WorkScheduleId"));
                slot.setStatus(rs.getString("Status"));
                slot.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
                slot.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
                slot.setRemainLeave(rs.getInt("RemainLeave"));
                slot.setWorkDay(rs.getInt("WorkDay"));
                slot.setUserId(rs.getInt("UserId"));

                workSchedules.add(slot);
            }
            return workSchedules;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void main(String[] args) {
//        LocalDate today = LocalDate.now();
//        LocalTime time = LocalTime.of(8, 0);
//        LocalDateTime result = LocalDateTime.of(today, time);
        WorkScheduleDAO wsd = new WorkScheduleDAO();
        
        System.out.println(wsd.deleteWorkSchedule(3));
    }

    public WorkSchedule getWorkScheduleById(int id) {
        String sql = "select * from WorkSchedule where WorkScheduleId=? and Status = 'pending'";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                WorkSchedule slot = new WorkSchedule();
                slot.setWorkScheduleId(rs.getInt("WorkScheduleId"));
                slot.setStatus(rs.getString("Status"));
                slot.setStartDate(rs.getTimestamp("StartDate").toLocalDateTime());
                slot.setEndDate(rs.getTimestamp("EndDate").toLocalDateTime());
                slot.setRemainLeave(rs.getInt("RemainLeave"));
                slot.setWorkDay(rs.getInt("WorkDay"));
                slot.setUserId(rs.getInt("UserId"));
                return slot;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public boolean deleteWorkSchedule(int id) {
        String sql = "delete from WorkSchedule where WorkScheduleId=? and Status = 'pending'";
        try (PreparedStatement rs = connection.prepareStatement(sql)) {
            rs.setInt(1, id);
            int rowsInserted = rs.executeUpdate();
        return rowsInserted > 0; // Returns true if the payment was added successfully
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }
}
