package taskManager.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import taskManager.model.Reminder;
import taskManager.model.Task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class FileHandler {
    private final ObjectMapper mapper;
    private File tasksStorageFile;
    private File reminderStorageFile;

    public FileHandler() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        Properties appProps = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/resources/task-manager.properties")) {
            appProps.load(fis);
        } catch (IOException e) {
            System.out.println("Couldn't load properties file, using default values.");
        }

        String tasksPath = appProps.getProperty("tasks.storage.file", "tasks.json");
        tasksStorageFile = new File(tasksPath);

        String remindersPath = appProps.getProperty("reminders.storage.file", "reminders.json");
        reminderStorageFile = new File(remindersPath);
    }

    public void writeTasksToFile(List<Task> tasksToAdd) throws IOException {
        List<Task> exisitingTasks = loadTasksFromFile();
        exisitingTasks.addAll(tasksToAdd);
        mapper.writerWithDefaultPrettyPrinter().writeValue(tasksStorageFile, exisitingTasks);
    }

    public List<Task> loadTasksFromFile() throws IOException {
        if (!tasksStorageFile.exists() || tasksStorageFile.length() == 0) {
            return new java.util.ArrayList<>();
        }
        return mapper.readValue(tasksStorageFile, new TypeReference<>() {});
    }

    public List<Reminder> readReminders() throws IOException {
        if (!reminderStorageFile.exists() || reminderStorageFile.length() == 0) {
            return new java.util.ArrayList<>();
        }
        return mapper.readValue(reminderStorageFile, new TypeReference<>() {});
    }

    public void addReminder(Reminder reminder) throws IOException {
        List<Reminder> reminders = readReminders();
        reminders.add(reminder);
        mapper.writerWithDefaultPrettyPrinter().writeValue(reminderStorageFile, reminders);
    }
}
