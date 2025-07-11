package model;

import java.sql.Timestamp;

public class TestSession {
    private int testId;
    private int testScheduleId;
    private String title;
    private int timer;
    private Timestamp deadline;
    private int mark;
    private String status;

    public TestSession() {}

    public TestSession(int testId, int testScheduleId, String title,
                       int timer, Timestamp deadline, int mark, String status) {
        this.testId = testId;
        this.testScheduleId = testScheduleId;
        this.title = title;
        this.timer = timer;
        this.deadline = deadline;
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

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
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
