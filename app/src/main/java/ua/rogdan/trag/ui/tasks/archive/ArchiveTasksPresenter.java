package ua.rogdan.trag.ui.tasks.archive;

import android.os.Handler;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.Generator;

public class ArchiveTasksPresenter extends Presenter<ArchiveTasksContract.IArchiveTasksView> implements ArchiveTasksContract.IArchiveTasksPresenter {
    @Override
    public void loadArchiveTasks() {
        view().showProgress();

        new Handler()
                .postDelayed(() -> {
                    if (!isUnbinded() && view().isActive()) {
                        view().onTasksLoaded(Generator.createTaskList(14, false));
                        view().hideProgress();
                    }
                }, 1000);
    }
}
