package ua.rogdan.trag.ui.tasks.archive;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class ArchiveTasksFragment extends BaseFragment implements ArchiveTasksContract.IArchiveTasksView{
    @Inject
    protected ArchiveTasksPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_archive_tasks;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new ArchiveTasksModule()).inject(this);
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

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
