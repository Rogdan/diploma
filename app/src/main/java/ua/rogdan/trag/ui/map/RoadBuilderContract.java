package ua.rogdan.trag.ui.map;

import android.support.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.task.Task;

public interface RoadBuilderContract {
    interface IRoadBuilderView extends IBaseView {
        GeoApiContext getGeoApiContext();
        void onDirectionLoaded(DirectionsResult response, Task task);
        void onLoadingError();
    }

    interface IRoadBuilderPresenter {
        void buildDirection(Task task, @Nullable LatLng myPosition);
    }
}
