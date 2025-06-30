package taskManager.service;

import taskManager.model.Category;
import taskManager.model.Task;

import java.util.List;

public interface TaskService {

    void addTask(Task task);
    List<Task> getAllTasks();
    List<Task> getTaskByTitle(String title);
    List<Task> getTaskByDescription(String description);
    void markTaskCompleted(long id);
    void deleteTask(Task task);
    List<Task> loadTasksFromFile();
    List<Task> writeTasksToFile();
    List<Task> getSortedTasks(String sortBy, boolean descending);
    List<Task> getTasksByCategory(Category category);
}
