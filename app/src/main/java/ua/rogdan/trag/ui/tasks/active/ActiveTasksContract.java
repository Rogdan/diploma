package ua.rogdan.trag.ui.tasks.active;

import java.util.ArrayList;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.task.Task;

public interface ActiveTasksContract {
    interface IActiveTasksView extends IBaseView {
        void onTaskListLoaded(ArrayList<Task> taskList);
    }

    interface IBaseTasksPresenter {

        void loadTasks();
    }
}
