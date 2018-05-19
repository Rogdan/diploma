package ua.rogdan.trag.ui.map;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.task.Task;

public interface RoadBuilderContract {
    interface IRoadBuilderView extends IBaseView {
        void onTaskLoaded(Task task);
    }

    interface IRoadBuilderPresenter {
        void test();
    }
}
