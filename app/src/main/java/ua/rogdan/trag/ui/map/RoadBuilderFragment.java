package ua.rogdan.trag.ui.map;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsResult;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.model.TravelMode;

import org.joda.time.DateTime;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.adapters.ItemClickListener;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.data.task.TaskPoint;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;
import ua.rogdan.trag.tools.SuffixFormatter;
import ua.rogdan.trag.tools.view.BaseGoogleMapFragment;
import ua.rogdan.trag.ui.tasks.active.ActiveTasksFragment;

public class RoadBuilderFragment extends BaseGoogleMapFragment implements RoadBuilderContract.IRoadBuilderView {
    private ActiveTasksFragment activeTasksFragment;
    private Task lastSelectedTask;

    @Inject
    protected RoadBuilderPresenter presenter;

    @BindView(R.id.task_picker_frame)
    protected FrameLayout pickerFrame;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.arrow_iv)
    protected ImageView arrowIV;
    @BindView(R.id.task_name_tv)
    protected TextView taskNameTV;
    @BindView(R.id.go_tv)
    protected TextView goTV;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_road_builder;
    }

    @Override
    protected void initView() {
        updateTaskTVs();
    }

    @OnClick(R.id.go_tv)
    protected void startRoad() {
        Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new RoadBuilderModule()).inject(this);
        presenter.bindView(this);
    }

    @OnClick(R.id.selected_task_layout)
    protected void switchTaskSelector() {
        if (activeTasksFragment != null) {
            arrowIV.animate().rotation(0).start();

            pickerFrame.setVisibility(View.INVISIBLE);
            getChildFragmentManager()
                    .beginTransaction()
                    .remove(activeTasksFragment)
                    .commit();

            activeTasksFragment = null;
        } else {
            arrowIV.animate().rotation(180).start();

            activeTasksFragment = new ActiveTasksFragment();
            activeTasksFragment.setClickListener(clickListener);
            pickerFrame.setVisibility(View.VISIBLE);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.task_picker_frame, activeTasksFragment)
                    .addToBackStack(activeTasksFragment.getClass().getCanonicalName())
                    .commit();
        }
    }

    private ItemClickListener<Task> clickListener = (task, position) -> {
        lastSelectedTask = task;
        updateTaskTVs();
        switchTaskSelector();
        goTV.setVisibility(View.GONE);

        presenter.buildDirection(task, myPosition);
        googleMap.clear();
    };

    private void updateTaskTVs() {
        if (lastSelectedTask == null) {
            taskNameTV.setText(R.string.choose_task);
        } else {
            String numberFormat = getContext().getString(R.string.number_format);
            String kmFormat = getContext().getString(R.string.km_format);
            String km = String.format(kmFormat, lastSelectedTask.getTravelKM());
            String stations = SuffixFormatter.formatWithSuffix(lastSelectedTask.getTaskPoints().size(), getContext().getResources().getStringArray(R.array.station_format));

            String result = String.format(numberFormat, lastSelectedTask.getId()) + ", " + km + ", " + stations;
            taskNameTV.setText(result);
        }
    }

    @Override
    protected int getMapFragmentID() {
        return R.id.map_fragment;
    }

    @Override
    protected void onMapReady() {

    }

    @Override
    @OnClick(R.id.location_iv)
    protected void tryFindLocation() {
        super.tryFindLocation();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    public void scaleToBounds(Task task) {
        LatLng startPoint;
        ArrayList<TaskPoint> taskPoints = task.getTaskPoints();
        if (myPosition == null) {
            startPoint = taskPoints.get(0).getCoordinates();
        } else {
            startPoint = myPosition;
        }

        LatLngBounds.Builder builder = LatLngBounds.builder();
        for (TaskPoint item : taskPoints) {
            builder.include(item.getCoordinates());
        }
        builder.include(startPoint);

        final LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onDirectionLoaded(DirectionsResult result, Task task) {
        addMarkersToMap(result);
        ArrayList<MarkerOptions> markers = task.parseToMarkers();
        for (MarkerOptions markerOptions : markers) {
            googleMap.addMarker(markerOptions);
        }

        addPolyline(result);
        scaleToBounds(task);
        goTV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingError() {
        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Subcomponent(modules = RoadBuilderModule.class)
    @ActivityScope
    public interface RoadBuilderComponent {
        void inject(@NonNull RoadBuilderFragment activity);
    }

    @Module
    public static class RoadBuilderModule {
        @Provides
        @ActivityScope
        @NonNull
        public RoadBuilderPresenter provideRoadBuilderPresenter() {
            return new RoadBuilderPresenter();
        }
    }
}
