package taskManager.service;

import taskManager.model.BaseTask;
import taskManager.model.Category;
import taskManager.model.Status;
import taskManager.model.Task;
import taskManager.repository.TaskRepository;
import taskManager.util.FileHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TaskServiceImpl implements TaskService{
    private final TaskRepository taskRepository;
    FileHandler fileHandler = new FileHandler();

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void addTask(Task task) {
        taskRepository.addTask(task);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(taskRepository.getTasks().values());
    }

    public List<Task> getTaskByTitle(String title) {
        return taskRepository.getTaskByTitle(title);
    }

    public List<Task> getTaskByDescription(String description) {
        return taskRepository.getTaskByDescription(description);
    }

    public void markTaskCompleted(long id) {
        taskRepository.getTasks().get(id).setStatus(Status.COMPLETED);
    }

    public void deleteTask(Task task) {
         taskRepository.deleteTask(task.getId());
    }

    public List<Task> loadTasksFromFile() {
        try {
            List<Task> tasks = fileHandler.loadTasksFromFile();
            tasks.forEach(taskRepository::addTask);
            return tasks;
        } catch (IOException ioException) {
            System.out.println("Couldn't load tasks file");
            return Collections.emptyList();
        }
    }

    public List<Task> writeTasksToFile() {
        try {
            List<Task> tasks = new ArrayList<>(taskRepository.getTasks().values());
            fileHandler.writeTasksToFile(tasks);
            return tasks;
        } catch (IOException ioException) {
            System.out.println("Couldn't write to tasks file");
            return Collections.emptyList();
        }
    }

    public List<Task> getSortedTasks(String sortBy, boolean isDescending) {
        Comparator<BaseTask> comparator;

        switch (sortBy.toLowerCase()) {
            case "due date":
                comparator = Comparator.comparing(BaseTask::getDueDate);
                break;
            case "priority":
                comparator = Comparator.comparing(BaseTask::getPriority);
                break;
            case "status":
                comparator = Comparator.comparing(BaseTask::getStatus);
                break;
            case "category":
                comparator = Comparator.comparing(BaseTask::getCategory);
                break;

            default:
                System.out.println("Invalid sort option: " + sortBy);
                return Collections.emptyList();
        }

        if (isDescending) {
            comparator = comparator.reversed();
        }

        return getAllTasks().stream().sorted(comparator).collect(Collectors.toList());
    }

    public List<Task> getTasksByCategory(Category category) {
        return getAllTasks().stream()
                .filter(task -> task.getCategory() == category)
                .collect(Collectors.toList());
    }
}
