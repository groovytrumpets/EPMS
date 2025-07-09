/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author nguye
 */
public class TestSchedule {
    private int testScheduleId;
    private int userId;
    private String status;

    public TestSchedule() {
    }

    public TestSchedule(int testScheduleId, int userId, String status) {
        this.testScheduleId = testScheduleId;
        this.userId = userId;
        this.status = status;
    }

    public int getTestScheduleId() {
        return testScheduleId;
    }

    public void setTestScheduleId(int testScheduleId) {
        this.testScheduleId = testScheduleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
