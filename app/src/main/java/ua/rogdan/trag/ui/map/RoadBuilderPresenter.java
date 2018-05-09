package ua.rogdan.trag.ui.map;

import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.Goods;
import ua.rogdan.trag.data.Task;
import ua.rogdan.trag.data.TaskPoint;

public class RoadBuilderPresenter extends Presenter<RoadBuilderContract.IRoadBuilderView> implements RoadBuilderContract.IRoadBuilderPresenter {
    @Override
    public void test() {
        view().showProgress();
        Random r = new Random();

        new Handler()
                .postDelayed(() -> {
                    Task task = new Task();
                    ArrayList<TaskPoint> taskPoints = new ArrayList<>();
                    for (String coordinate : coordinates) {
                        TaskPoint taskPoint = new TaskPoint();
                        taskPoint.setDescription("Test data");
                        int goodsAmount = r.nextInt(3) + 1;

                        ArrayList<Goods> goodsList = new ArrayList<>();
                        for (int j = 0; j < goodsAmount; j++) {
                            Goods goods = new Goods();
                            goods.setName("Test goods");
                            goodsList.add(goods);
                        }

                        taskPoint.setGoodsList(goodsList);
                        String[] latLng = coordinate.split(",");
                        taskPoint.setLatitude(latLng[0]);
                        taskPoint.setLongitude(latLng[1]);
                        taskPoints.add(taskPoint);
                    }

                    task.setTaskPoints(taskPoints);
                    view().onTaskLoaded(task);
                    view().hideProgress();
                }, 1000);
    }

    public static final String [] coordinates = {
            "49.989627,36.207934",
            "49.982231,36.244338",
            "49.986843,36.265855",
    };

    /*
    "50.023722,36.256247",
    "50.024244,36.219302",
    "50.023089,36.340054",
    "49.945774,36.264598",
    "50.059022,36.202866",
    "49.960849,36.324271",
    "49.961451,36.216274"
     */
}
