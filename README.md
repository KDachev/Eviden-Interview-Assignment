# Eviden-Interview-Assignment

This is a command-line Task Management System. It lets the user create, view, update and delete Tasks.

To start the application run Main.java. When the application starts the user will be prompted if they want to load existing Tasks from a file or start a new session with no tasks. (A small sample data is initially provided) If the user chooses yes, the application loads all reminders of not completed tasks.

After starting the application and loading of tasks is finished, the application displays the main menu. You are given a choice of:
1. Add a new task
2. List all tasks
3. Search for a task
4. Mark a task as completed
5. Delete a task
6. Save tasks to file
7. Load tasks from file
8. Sort tasks
9. Add reminder to Task
10. Exit

The user can choose the action you want to make by typing the number corresponding to it.

In src/main/resources/task-manager.properties you can change the location of the files that contain the tasks and reminders.

The application is logged to a task-manager.log file.
