package ua.rogdan.trag.data;

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
}
