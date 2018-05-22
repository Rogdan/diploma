package ua.rogdan.trag.ui.map;

import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import ua.rogdan.trag.core.Presenter;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.data.task.TaskPoint;

public class RoadBuilderPresenter extends Presenter<RoadBuilderContract.IRoadBuilderView> implements RoadBuilderContract.IRoadBuilderPresenter {
    @Override
    public void buildDirection(Task task, @Nullable LatLng myPosition) {
        view().showProgress();

        Subscription subscription = Observable.fromCallable(() -> {
            DateTime now = new DateTime();

            String wayPoints = task.parseWayPoints();
            LatLng startPoint, endPoint;
            ArrayList<TaskPoint> taskPoints = task.getTaskPoints();
            if (myPosition == null) {
                startPoint = taskPoints.get(0).getCoordinates();
            } else {
                startPoint = myPosition;
            }

            endPoint = taskPoints.get(taskPoints.size() - 1).getCoordinates();

            return DirectionsApi.
                    newRequest(view().getGeoApiContext())
                    .mode(TravelMode.DRIVING)
                    .origin(startPoint.latitude + "," + startPoint.longitude)
                    .waypoints(wayPoints)
                    .destination(endPoint.latitude + "," + endPoint.longitude)
                    .optimizeWaypoints(true)
                    .departureTime(now)
                    .await();
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                            if (!isUnbinded() && view().isActive()) {
                                view().onDirectionLoaded(response, task);
                                view().hideProgress();
                            }
                        },
                        throwable -> {
                            if (view().isActive()) {
                                view().hideProgress();
                                view().onLoadingError();
                            }
                        });

        subscriptionsToUnbind.add(subscription);
    }

    public String getMapUri(Task task, @Nullable LatLng myPosition) {
        StringBuilder result = new StringBuilder(BASE_MAP_URL);
        ArrayList<TaskPoint> taskPoints = task.getTaskPoints();
        LatLng startPoint;
        if (myPosition == null) {
            startPoint = taskPoints.get(0).getCoordinates();
        } else {
            startPoint = myPosition;
        }

        LatLng endPoint = taskPoints.get(taskPoints.size() - 1).getCoordinates();
        result.append("&origin=").append(formatLatLng(startPoint))
                .append("&destination=").append(formatLatLng(endPoint))
                .append("&travelmode=driving")
                .append("&waypoints=");

        LatLng firstPoint = taskPoints.get(0).getCoordinates();
        result.append(formatLatLng(firstPoint));

        for (int i = 1; i < taskPoints.size() - 1; i++) {
            LatLng coordinates = taskPoints.get(i).getCoordinates();
            result.append("|").append(formatLatLng(coordinates));
        }
        return result.toString();
    }

    private String formatLatLng(LatLng latLng) {
        return latLng.latitude + "," + latLng.longitude;
    }

    private static final String BASE_MAP_URL = "https://www.google.com/maps/dir/?api=1";
}
