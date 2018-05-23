package ua.rogdan.trag.ui.tasks.archive;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.adapters.TaskListAdapter;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.data.task.Task;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class ArchiveTasksFragment extends BaseFragment implements ArchiveTasksContract.IArchiveTasksView{
    private TaskListAdapter adapter;

    @Inject
    protected ArchiveTasksPresenter presenter;

    @BindView(R.id.archive_tasks_rv)
    protected RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_archive_tasks;
    }

    @Override
    protected void initView() {
        adapter = new TaskListAdapter(getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new ArchiveTasksModule()).inject(this);
        presenter.bindView(this);
        presenter.loadArchiveTasks();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTasksLoaded(List<Task> tasks) {
        adapter.setItems(tasks);
    }

    @Subcomponent(modules = ArchiveTasksModule.class)
    @ActivityScope
    public interface ArchiveTasksComponent {
        void inject(@NonNull ArchiveTasksFragment fragment);
    }

    @Module
    public static class ArchiveTasksModule {
        @Provides
        @ActivityScope
        @NonNull
        public ArchiveTasksPresenter provideArchiveTasksPresenter() {
            return new ArchiveTasksPresenter();
        }
    }
}
