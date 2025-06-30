package taskManager.config;

import taskManager.repository.TaskRepository;
import taskManager.service.*;
import taskManager.ui.UserInterface;

public class TaskManagerConfig {
    public static UserInterface configureApp() {
        TaskRepository taskRepository = new TaskRepository();
        TaskService taskService = new TaskServiceImpl(taskRepository);
        ReminderService reminderService = new ReminderService();
        return new UserInterface(taskService, reminderService);
    }
}
