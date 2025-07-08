/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDateTime;

/**
 *
 * @author nguye
 */
public class TestSession {
    private int testId;
    private int testScheduleId;
    private String title;
    private int timer, mark;
    private LocalDateTime deadline, createDate;
    private String status;

    public TestSession() {
    }

    public TestSession(int testId, int testScheduleId, String title, int timer, int mark, LocalDateTime deadline, LocalDateTime createDate, String status) {
        this.testId = testId;
        this.testScheduleId = testScheduleId;
        this.title = title;
        this.timer = timer;
        this.mark = mark;
        this.deadline = deadline;
        this.createDate = createDate;
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

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
