package ua.rogdan.trag.ui.employees;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.adapters.EmployeesAdapter;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.data.user.User;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class EmployeesFragment extends BaseFragment implements EmployeesContract.IEmployeesView {
    private EmployeesAdapter adapter;

    @Inject
    protected EmployeesPresenter presenter;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.employees_rv)
    protected RecyclerView recyclerView;
    @BindView(R.id.title_tv)
    protected TextView titleTV;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_employees;
    }

    @Override
    protected void initView() {
        adapter = new EmployeesAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        titleTV.setText(R.string.employees);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new EmployeesModule()).inject(this);
        presenter.bindView(this);
        presenter.loadEmployees();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onEmployeesLoaded(ArrayList<User> employees) {
        adapter.setItemsList(employees);
    }

    @Subcomponent(modules = EmployeesModule.class)
    @ActivityScope
    public interface EmployeesComponent {
        void inject(@NonNull EmployeesFragment fragment);
    }

    @Module
    public static class EmployeesModule {
        @Provides
        @ActivityScope
        @NonNull
        public EmployeesPresenter provideEmployeesPresenter() {
            return new EmployeesPresenter();
        }
    }
}
