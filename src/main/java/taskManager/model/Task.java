package taskManager.model;

import java.time.LocalDate;
import java.util.Objects;

public class Task extends BaseTask{

    public Task(){}

    public Task(String title, String description, LocalDate dueDate,
                Priority priority, Status status, Category category)
    {
        super(title, description, dueDate, priority, status, category);
    }

    public Task(long id, String title, String description, LocalDate dueDate,
                Priority priority, Status status, LocalDate creationDate, Category category)
    {
        super(id, title, description, dueDate, priority, status, creationDate, category);
    }

    @Override
    public void displayTask() {
        System.out.println("Task: '" + super.getTitle() + '\'' +
                ", Description: '" + description + '\'' +
                ", Due Date: " + dueDate +
                ", Priority: " + priority +
                ", Status: " + status +
                ", Creation Date: " + creationDate +
                ", Category: " + category);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dueDate=" + dueDate +
                ", priority=" + priority +
                ", status=" + status +
                ", creationDate=" + creationDate +
                ", Category: " + category +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Task other = (Task) obj;

        return Objects.equals(title, other.title)
                && Objects.equals(description, other.description)
                && Objects.equals(dueDate, other.dueDate)
                && priority == other.priority
                && status == other.status
                && Objects.equals(creationDate, other.creationDate)
                && category == other.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dueDate, priority, status, creationDate, category);
    }
}
