/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class WorkSchedule {
    private int workScheduleId;
    private String status;
    private Date startDate;
    private Date endDate;
    private int remainLeave;
    private int workDay;
    private int userId;

    public WorkSchedule() {
    }

    public WorkSchedule(int workScheduleId, String status, Date startDate, Date endDate, int remainLeave, int workDay, int userId) {
        this.workScheduleId = workScheduleId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remainLeave = remainLeave;
        this.workDay = workDay;
        this.userId = userId;
    }

    public int getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(int workScheduleId) {
        this.workScheduleId = workScheduleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getRemainLeave() {
        return remainLeave;
    }

    public void setRemainLeave(int remainLeave) {
        this.remainLeave = remainLeave;
    }

    public int getWorkDay() {
        return workDay;
    }

    public void setWorkDay(int workDay) {
        this.workDay = workDay;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    
}

