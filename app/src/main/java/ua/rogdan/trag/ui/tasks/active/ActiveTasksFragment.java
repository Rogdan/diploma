package ua.rogdan.trag.ui.tasks.active;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class ActiveTasksFragment extends BaseFragment implements ActiveTasksContract.IActiveTasksView{
    @Inject
    protected ActiveTasksPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_tasks;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new ActiveTasksModule()).inject(this);
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
