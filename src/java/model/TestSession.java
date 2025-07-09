/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

public class TestSession {
    private int testId;
    private int testScheduleId;
    private String title;
    private int timer;
    private Date deadline;
    private Date createDate;
    private int mark;
    private String status;

    public TestSession() {
    }

    public TestSession(int testId, int testScheduleId, String title, int timer, Date deadline, Date createDate, int mark, String status) {
        this.testId = testId;
        this.testScheduleId = testScheduleId;
        this.title = title;
        this.timer = timer;
        this.deadline = deadline;
        this.createDate = createDate;
        this.mark = mark;
        this.status = status;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getTestScheduleId() {
        return testScheduleId;
    }

    public void setTestScheduleId(int testScheduleId) {
        this.testScheduleId = testScheduleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    
}

