package ua.rogdan.trag.data;

import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.model.LatLng;

import java.util.ArrayList;

public class Task {
    private int id;
    private ArrayList<TaskPoint> taskPoints;
    private User executor;
    private User customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<TaskPoint> getTaskPoints() {
        return taskPoints;
    }

    public void setTaskPoints(ArrayList<TaskPoint> taskPoints) {
        this.taskPoints = taskPoints;
    }

    public User getExecutor() {
        return executor;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String parseWayPoints() {
        StringBuilder wayPoints = new StringBuilder();
        for (TaskPoint taskPoint : taskPoints) {
            wayPoints.append(taskPoint.getLatitude()).append(",").append(taskPoint.getLongitude()).append("|");
        }

        wayPoints.replace(wayPoints.length() - 1 , wayPoints.length(), "");

        return wayPoints.toString();
    }

    public ArrayList<MarkerOptions> parseToMarkers() {
        ArrayList<MarkerOptions> markerOptionsList = new ArrayList<>();
        for (TaskPoint taskPoint : taskPoints) {
            MarkerOptions options = new MarkerOptions();
            options.position(taskPoint.getCoordinates());
            options.title(taskPoint.getDescription());

            markerOptionsList.add(options);
        }
        return markerOptionsList;
    }
}
