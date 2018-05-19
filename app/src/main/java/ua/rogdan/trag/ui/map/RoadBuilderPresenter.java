package ua.rogdan.trag.ui.map;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.task.Goods;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.data.task.TaskPoint;

public class RoadBuilderPresenter extends Presenter<RoadBuilderContract.IRoadBuilderView> implements RoadBuilderContract.IRoadBuilderPresenter {
    @Override
    public void test() {
        view().showProgress();
        Random r = new Random();

        new Handler()
                .postDelayed(() -> {
                    Task task = new Task();
                    ArrayList<TaskPoint> taskPoints = new ArrayList<>();


                    task.setTaskPoints(taskPoints);
                    view().onTaskLoaded(task);
                    view().hideProgress();
                }, 1000);
    }



    /*

     */
}
