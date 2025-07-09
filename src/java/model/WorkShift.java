/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

public class WorkShift {
    private int shiftId;
    private String slot;
    private String attendance;
    private String status;
    private int workScheduleId;

    public WorkShift() {
    }

    public WorkShift(int shiftId, String slot, String attendance, String status, int workScheduleId) {
        this.shiftId = shiftId;
        this.slot = slot;
        this.attendance = attendance;
        this.status = status;
        this.workScheduleId = workScheduleId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getAttendance() {
        return attendance;
    }

    public void setAttendance(String attendance) {
        this.attendance = attendance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWorkScheduleId() {
        return workScheduleId;
    }

    public void setWorkScheduleId(int workScheduleId) {
        this.workScheduleId = workScheduleId;
    }

    
}

