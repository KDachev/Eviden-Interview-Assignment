package taskManager.model;

import java.time.LocalDate;

public abstract class BaseTask {
    protected long id;
    protected String title;
    protected String description;
    protected LocalDate dueDate;
    protected Priority priority;
    protected Status status;
    protected LocalDate creationDate;
    protected Category category;

    public BaseTask(){}

    public BaseTask(String title, String description, LocalDate dueDate,
                    Priority priority, Status status, Category category)
    {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.category = category;
        creationDate = LocalDate.now();
    }

    public BaseTask(long id, String title, String description, LocalDate dueDate,
                    Priority priority, Status status, LocalDate creationDate, Category category)
    {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.status = status;
        this.category = category;
        this.creationDate = creationDate;
    }

    public abstract void displayTask();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
