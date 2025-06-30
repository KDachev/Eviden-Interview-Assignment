package taskManager.service;

import taskManager.model.Reminder;
import taskManager.model.Status;
import taskManager.model.Task;
import taskManager.util.FileHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReminderService {
    FileHandler fileHandler = new FileHandler();

    public List<Reminder> loadReminders() {
        try {
            return fileHandler.readReminders().stream()
                    .filter(reminder -> !reminder.getTask().getStatus().equals(Status.COMPLETED))
                    .collect(Collectors.toList());
        } catch (IOException ioException) {
            System.out.println("Couldn't load reminders file");
            return Collections.emptyList();
        }
    }

    public void addReminder(Task task, LocalDate remindOn) {
        try {
            Reminder reminder = new Reminder(task, remindOn);
            fileHandler.addReminder(reminder);
        } catch (IOException ioException) {
            System.out.println("Couldn't write to reminders file");
        }
    }
}
