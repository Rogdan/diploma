package ua.rogdan.trag.ui.map;

import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.DirectionsApi;
import com.google.maps.model.DirectionsResult;
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
import ua.rogdan.trag.data.Task;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;
import ua.rogdan.trag.tools.view.BaseGoogleMapFragment;

public class RoadBuilderFragment extends BaseGoogleMapFragment implements RoadBuilderContract.IRoadBuilderView {
    @Inject
    protected RoadBuilderPresenter presenter;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_road_builder;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new RoadBuilderModule()).inject(this);
        presenter.bindView(this);

    }

    @Override
    protected int getMapFragmentID() {
        return R.id.map_fragment;
    }

    @Override
    protected void onMapReady() {
        presenter.test();
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

    @Override
    public void onTaskLoaded(Task task) {
        DateTime now = new DateTime();
        try {
            String wayPoints = task.parseWayPoints();

            DirectionsResult result = DirectionsApi.
                    newRequest(getGeoApiContext())
                    .mode(TravelMode.DRIVING)
                    .origin(myPosition.latitude + "," + myPosition.longitude)
                    .waypoints(wayPoints)
                    //.optimizeWaypoints(true)
                    .destination(myPosition.latitude + "," + myPosition.longitude)
                    .departureTime(now)
                    .await();

            addMarkersToMap(result);
            ArrayList<MarkerOptions> markers = task.parseToMarkers();
            for (MarkerOptions markerOptions : markers) {
                googleMap.addMarker(markerOptions);
            }
            addPolyline(result);
        } catch (Exception e) {
            Log.e("Rogdan", e.toString());
            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        }
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
