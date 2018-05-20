package ua.rogdan.trag.ui.tasks.active;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.adapters.ItemClickListener;
import ua.rogdan.trag.adapters.TaskListAdapter;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class ActiveTasksFragment extends BaseFragment implements ActiveTasksContract.IActiveTasksView{
    private TaskListAdapter adapter;
    private ItemClickListener<Task> clickListener;

    @Inject
    protected ActiveTasksPresenter presenter;

    @BindView(R.id.active_tasks_rv)
    protected RecyclerView tasksRV;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_tasks;
    }

    @Override
    protected void initView() {
        adapter = new TaskListAdapter(getContext());
        tasksRV.setLayoutManager(new LinearLayoutManager(getContext()));
        tasksRV.setAdapter(adapter);

        adapter.setClickListener((task, position) -> {
            if (clickListener == null) {
                Toast.makeText(getContext(), "Click", Toast.LENGTH_SHORT).show();
            } else {
                clickListener.onItemSelected(task, position);
            }
        });
    }

    public void setClickListener(ItemClickListener<Task> clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new ActiveTasksModule()).inject(this);
        presenter.bindView(this);

        presenter.loadTasks();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        tasksRV.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        tasksRV.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTaskListLoaded(ArrayList<Task> taskList) {
        adapter.setItems(taskList);
    }

    @Subcomponent(modules = ActiveTasksModule.class)
    @ActivityScope
    public interface ActiveTasksComponent {
        void inject(@NonNull ActiveTasksFragment fragment);
    }

    @Module
    public static class ActiveTasksModule {
        @Provides
        @ActivityScope
        @NonNull
        public ActiveTasksPresenter provideActiveTasksPresenter() {
            return new ActiveTasksPresenter();
        }
    }
}
