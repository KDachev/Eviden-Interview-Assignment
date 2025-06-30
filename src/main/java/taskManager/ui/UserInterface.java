package taskManager.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import taskManager.model.*;
import taskManager.service.ReminderService;
import taskManager.service.TaskService;
import taskManager.util.DateUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UserInterface {
    private static final Logger logger = LoggerFactory.getLogger(UserInterface.class);

    private final Scanner scanner;
    private final TaskService taskService;
    private final ReminderService reminderService;

    public UserInterface(TaskService taskService, ReminderService reminderService) {
        scanner = new Scanner(System.in);
        this.taskService = taskService;
        this.reminderService = reminderService;
    }

    public void startup() {
        System.out.println("Starting Task Management App");
        logger.info("Starting app");

        String answer;
        do {
            System.out.println("Do you wish to load Tasks from file? Yes/No");
            logger.info("Do you wish to load Tasks from file? Yes/No");
            answer = scanner.nextLine().trim();
            logUserChoice(answer);
        } while (!answer.equalsIgnoreCase("Yes") && !answer.equalsIgnoreCase("No"));

        if (answer.equalsIgnoreCase("Yes")) {
            loadTasksFromFile().forEach(Task::displayTask);

            checkForReminders();
        }

        displayMainMenu();
    }

    public void displayMainMenu() {
        logger.info("Main menu displayed");

        String answer;
        do {
            printMenu();
            answer = scanner.nextLine().trim();
            logUserChoice(answer);

            switch (answer) {
                case "1":
                    createTask();
                    break;
                case "2":
                    listAllTasks();
                    break;
                case "3":
                    searchTask();
                    break;
                case "4":
                    markTaskCompleted();
                    break;
                case "5":
                    deleteTask();
                    break;
                case "6":
                    saveTasksToFile();
                    break;
                case "7":
                    loadTasksFromFile();
                    break;
                case "8":
                    sortTasks();
                    break;
                case "9":
                    addReminder();
                    break;
                case "10":
                    System.out.println("Exiting the application ...");
                    scanner.close();
                    logger.info("Closing application");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (!answer.equals("10"));
    }

    private void checkForReminders() {
        List<Reminder> reminders = reminderService.loadReminders();

        if (reminders.isEmpty()) {
            System.out.println("\nYou have no reminders!");
            logger.info("User has no reminders");
            return;
        }

        reminders.stream()
                .filter(reminder -> reminder.getRemindOn().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        System.out.println("\nYou have " + reminders.size() + " reminders:");
        reminders.forEach(System.out::println);
        logger.info("Displayed reminders {}", reminders);
        System.out.println("Reminders will be removed once task is completed");
    }

    private void printMenu() {
        System.out.println("\n---------------Main menu---------------");
        System.out.println("What would you like to do?"
                + "\n1. Add a new task"
                + "\n2. List all tasks"
                + "\n3. Search for a task"
                + "\n4. Mark a task as completed"
                + "\n5. Delete a task"
                + "\n6. Save tasks to file"
                + "\n7. Load tasks from file"
                + "\n8. Sort tasks"
                + "\n9. Add reminder to Task"
                + "\n10. Exit");
    }

    private void createTask() {
        System.out.println("What is the title of the Task?");
        String title = scanner.nextLine();

        System.out.println("What is the description of the Task?");
        String description = scanner.nextLine();

        System.out.println("What date is the Task due? (yyyy-MM-dd)");

        LocalDate dueDate = null;
        while (dueDate == null) {
            String input = scanner.nextLine().trim();
            dueDate = DateUtil.parseDate(input);
        }

        System.out.println("What is the priority of the Task? " + Arrays.toString(Priority.values()));
        Priority priority = null;
        while (priority == null) {
            try {
                priority = Priority.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid priority value. Please give a valid priority - " + Arrays.toString(Priority.values()));
            }
        }

        System.out.println("What is the priority of the Task? " + Arrays.toString(Category.values()));
        Category category = null;
        while (category == null) {
            try {
                category = Category.valueOf(scanner.nextLine().trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid priority value. Please give a valid priority - " + Arrays.toString(Priority.values()));
            }
        }

        Task task = new Task(title, description, dueDate, priority, Status.PENDING, category);
        System.out.println("Task created successfully - " + task);
        taskService.addTask(task);

        logger.info("User has created a Task - {}", task);
    }

    private void listAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        tasks.forEach(Task::displayTask);
        logger.info("Displayed all tasks: {}", tasks);
    }

    private void searchTask() {
        System.out.println("How would you like to search" +
                "\n1. By title" +
                "\n2. By description");
        String searchMethod = scanner.nextLine();

        List<Task> tasks;
        String keyword;
        switch (searchMethod) {
            case "1":
                System.out.println("Enter a keyword to search by title:");
                keyword = scanner.nextLine();
                tasks = taskService.getTaskByTitle(keyword);
                logger.info("User searched for task by title using keyword {} and found {}", keyword, tasks);
                break;
            case "2":
                System.out.println("Enter a keyword to search by description:");
                keyword = scanner.nextLine();
                tasks = taskService.getTaskByDescription(keyword);
                logger.info("User searched for task by description using keyword {} and found {}", keyword, tasks);
                break;
            default:
                System.out.println("Invalid choice");
                return;
        }

        if (tasks.isEmpty()) {
            System.out.println("No Tasks containing \"" + keyword + "\" found");
        } else {
            System.out.println("Tasks found with \"" + keyword + "\": ");
            tasks.forEach(Task::displayTask);
        }
    }

    private void markTaskCompleted() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to mark as completed.");
            return;
        }

        System.out.println("Please give the number of the task to be marked as Completed");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }

        try {
            int index = Integer.parseInt(scanner.nextLine()) - 1;

            if (index < 0 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            Task task = tasks.get(index);
            System.out.printf("Are you sure you want to mark task \"%s\" as completed? (yes/no): ", task.getTitle());
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {
                taskService.markTaskCompleted(task.getId());
                System.out.println("Task marked as completed.");
                logger.info("User has marked as completed task - {}", task);
            } else {
                System.out.println("Task completion canceled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void deleteTask() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to delete.");
            return;
        }

        System.out.println("Please give the number of the task to be deleted");

        for (int i = 1; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i, tasks.get(i));
        }

        try {
            int index = Integer.parseInt(scanner.nextLine());

            if (index < 1 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }

            Task task = tasks.get(index);
            System.out.printf("Are you sure you want to delete the task \"%s\"? (yes/no): ", task.getTitle());
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (confirm.equals("yes") || confirm.equals("y")) {
                taskService.deleteTask(task);
                System.out.println("Task deleted.");
                logger.info("User deleted task {}", task);
            } else {
                System.out.println("Task deletion canceled.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private void saveTasksToFile() {
        System.out.println("Wrote " + taskService.writeTasksToFile().size() + " tasks to file");
        logger.info("Wrote tasks to file");
    }

    private List<Task> loadTasksFromFile() {
        List<Task> tasks = taskService.loadTasksFromFile();
        System.out.println("Loaded " + tasks.size() + " tasks from file");
        logger.info("Loaded tasks from file");
        return tasks;
    }

    private void sortTasks() {
        System.out.println("How would you like to sort the tasks:" +
                "\n1. By due date" +
                "\n2. By priority" +
                "\n3. By status" +
                "\n4. By category");

        String comparingChoice = scanner.nextLine();
        String sortStyle;

        switch (comparingChoice) {
            case "1":
                sortStyle = "due date";
                break;
            case "2":
                sortStyle = "priority";
                break;
            case "3":
                sortStyle = "status";
                break;
            case "4":
                sortStyle = "category";
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        System.out.println("Should it be in descending order? Yes/No");
        String descendingOrderChoice = scanner.nextLine();

        boolean isDescending = false;
        if (descendingOrderChoice.equalsIgnoreCase("Yes"))
            isDescending = true;

        taskService.getSortedTasks(sortStyle, isDescending).forEach(Task::displayTask);
        logger.info("User has sorted tasks by {}", sortStyle);
    }

    private void addReminder() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to add .");
            return;
        }
        System.out.println("Please give the number of the task to add a reminder to");

        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, tasks.get(i));
        }

        int index;
        try {
            index = Integer.parseInt(scanner.nextLine());

            if (index < 1 || index >= tasks.size()) {
                System.out.println("Invalid task number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task number.");
            return;
        }

        Task task = tasks.get(index);

        System.out.println("What date should you be reminded? (yyyy-MM-dd)");
        LocalDate remindOn = null;
        while (remindOn == null) {
            String input = scanner.nextLine().trim();
            remindOn = DateUtil.parseDate(input);
        }

        reminderService.addReminder(task, remindOn);
        System.out.println("Successfully added reminder for task " + task + " and will be reminded on " + remindOn);
        logger.info("User created reminder for task {} and will be reminded on {}", task, remindOn);
    }

    private void logUserChoice(String choice) {
        logger.info("User has chosen: {}", choice);
    }
}
