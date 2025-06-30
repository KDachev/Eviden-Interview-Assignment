package taskManager.repository;

import taskManager.model.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaskRepository {
    long idCounter = 1;
    private final Map<Long, Task> tasks;

    public TaskRepository() {
        tasks = new HashMap<>();
    }

    public void addTask(Task task) {
        if (task.getId() == 0)
            task.setId(idCounter);
        tasks.put(task.getId(), task);
        idCounter++;
    }

    public Map<Long, Task> getTasks() {
        return tasks;
    }

    public List<Task> getTaskByTitle(String title) {
        return tasks.values().stream()
                .filter(task -> task.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    public List<Task> getTaskByDescription(String description) {
        return tasks.values().stream()
                .filter(task -> task.getDescription().contains(description))
                .collect(Collectors.toList());
    }

    public void deleteTask(long id) {
        tasks.remove(id);
    }
}
