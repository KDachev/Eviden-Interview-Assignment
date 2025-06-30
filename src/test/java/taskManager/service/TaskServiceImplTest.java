package taskManager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import taskManager.model.Category;
import taskManager.model.Priority;
import taskManager.model.Status;
import taskManager.model.Task;
import taskManager.repository.TaskRepository;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TaskServiceImplTest {
    private TaskService taskService;
    private TaskRepository taskRepository;
    private List<Task> sampleTasks;

    @BeforeEach
    public void init(){
        Task getGroceriesTask = new Task(
                1L,
                "Get groceries",
                "Go to the store and get groceries",
                LocalDate.now().plusDays(1),
                Priority.MEDIUM,
                Status.PENDING,
                LocalDate.now(),
                Category.CHORES);

        Task cleanKitchenTask = new Task(
                2L,
                "Clean desc",
                "Clean the desc at your work",
                LocalDate.now(),
                Priority.HIGH,
                Status.PENDING,
                LocalDate.now(),
                Category.WORK);

        Task doHomeworkTask = new Task(
                3L,
                "Do homework",
                "Finish Math homework",
                LocalDate.now().plusDays(10),
                Priority.LOW,
                Status.COMPLETED,
                LocalDate.now(),
                Category.SCHOOL);

        taskRepository = new TaskRepository();
        taskService = new TaskServiceImpl(taskRepository);

        sampleTasks = List.of(getGroceriesTask, cleanKitchenTask, doHomeworkTask);

        taskRepository.addTask(getGroceriesTask);
        taskRepository.addTask(cleanKitchenTask);
        taskRepository.addTask(doHomeworkTask);
    }

    @Test
    public void shouldAddTask(){
        assertEquals(sampleTasks.size(), taskRepository.getTasks().size());

        Task findHobbyTask = new Task(
                4L,
                "Find hobby",
                "Find something you can do in your free time",
                LocalDate.now().plusDays(1),
                Priority.HIGH,
                Status.PENDING,
                LocalDate.now(),
                Category.PERSONAL);

        taskService.addTask(findHobbyTask);
        assertEquals(sampleTasks.size() + 1, taskRepository.getTasks().size());
    }

    @Test
    public void shouldReturnAllTasks() {
        assertEquals(sampleTasks.size(), taskService.getAllTasks().size());
    }

    @Test
    public void shouldGetTaskByTitle() {
        Task getGroceriesTask = sampleTasks.get(0);

        assertEquals(List.of(getGroceriesTask), taskService.getTaskByTitle("groceries"));
    }

    @Test
    public void shouldGetTaskByDescription() {
        Task getGroceriesTask = new Task(1L, "Get groceries",
                "Go to the store and get groceries",
                LocalDate.now().plusDays(1),
                Priority.MEDIUM,
                Status.PENDING,
                LocalDate.now(),
                Category.CHORES);

        assertEquals(List.of(getGroceriesTask), taskService.getTaskByDescription("Go to the store"));
    }

    @Test
    public void shouldMarkTaskCompleted() {
        taskService.markTaskCompleted(sampleTasks.get(0).getId());

        assertEquals(Status.COMPLETED, taskRepository.getTasks().get(1L).getStatus());
    }

    @Test
    public void shouldDeleteTask() {
        assertEquals(sampleTasks.size(), taskRepository.getTasks().size());
        assertTrue(taskRepository.getTasks().containsValue(sampleTasks.get(0)));

        taskService.deleteTask(sampleTasks.get(0));

        assertEquals(sampleTasks.size() - 1, taskRepository.getTasks().size());
        assertFalse(taskRepository.getTasks().containsValue(sampleTasks.get(0)));
    }

    @Test
    public void shouldSortTasks() {
        String sortBy = "priority";
        boolean isDescending = true;

        List<Task> expectedList = List.of(sampleTasks.get(1), sampleTasks.get(0), sampleTasks.get(2));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));

        isDescending = false;

        expectedList = List.of(sampleTasks.get(2), sampleTasks.get(0), sampleTasks.get(1));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));

        sortBy = "due date";

        expectedList = List.of(sampleTasks.get(1), sampleTasks.get(0), sampleTasks.get(2));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));

        sortBy = "status";
        isDescending = true;

        expectedList = List.of(sampleTasks.get(2), sampleTasks.get(0), sampleTasks.get(1));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));


        isDescending = false;

        expectedList = List.of(sampleTasks.get(0), sampleTasks.get(1), sampleTasks.get(2));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));

        sortBy = "category";

        expectedList = List.of(sampleTasks.get(0), sampleTasks.get(2), sampleTasks.get(1));
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));

        sortBy = "5";
        expectedList = Collections.emptyList();
        assertEquals(expectedList, taskService.getSortedTasks(sortBy, isDescending));
    }

    @Test
    public void shouldGetTasksWithSameCategory() {
        Category category = Category.WORK;

        List<Task> expectedList = List.of(sampleTasks.get(1));
        assertEquals(expectedList, taskService.getTasksByCategory(category));
    }
}