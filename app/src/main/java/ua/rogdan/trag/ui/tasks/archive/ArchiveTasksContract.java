package ua.rogdan.trag.ui.tasks.archive;

import java.util.ArrayList;
import java.util.List;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.task.Task;

public interface ArchiveTasksContract {
    interface IArchiveTasksView extends IBaseView {
        void onTasksLoaded(List<Task> tasks);
    }

    interface IArchiveTasksPresenter {
        void loadArchiveTasks();
    }
}
