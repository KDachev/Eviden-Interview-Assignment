package taskManager;

import taskManager.config.TaskManagerConfig;
import taskManager.ui.UserInterface;

public class Main {
    public static void main(String[] args){
        UserInterface userInterface = TaskManagerConfig.configureApp();
        userInterface.startup();
    }
}