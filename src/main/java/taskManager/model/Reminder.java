package taskManager.model;

import java.time.LocalDate;

public class Reminder {
    private Task task;
    private LocalDate remindOn;

    public Reminder() {}

    public Reminder(Task task, LocalDate remindOn) {
        this.task = task;
        this.remindOn = remindOn;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDate getRemindOn() {
        return remindOn;
    }

    public void setRemindOn(LocalDate remindOn) {
        this.remindOn = remindOn;
    }

    @Override
    public String toString() {
        return "Reminder" +
                "\n" + task + "," +
                "\nremindOn: " + remindOn +"\n";
    }
}
