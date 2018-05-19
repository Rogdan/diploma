package ua.rogdan.trag.ui.tasks.active;

import android.os.Handler;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.Generator;

public class ActiveTasksPresenter extends Presenter<ActiveTasksContract.IActiveTasksView> implements ActiveTasksContract.IBaseTasksPresenter {
    @Override
    public void loadTasks() {
        view().showProgress();

        new Handler()
                .postDelayed(() -> {
                    if (!isUnbinded() && view().isActive()) {
                        view().onTaskListLoaded(Generator.createTaskList(14));
                        view().hideProgress();
                    }
                }, 1000);
    }
}
