package ua.rogdan.trag.ui.splash;

import android.content.Intent;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseActivity;
import ua.rogdan.trag.core.BaseActivityWithPresenter;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;
import ua.rogdan.trag.ui.MainActivity;

public class SplashActivity extends BaseActivityWithPresenter implements SplashContract.ISplashView{
    @Inject
    protected SplashPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new SplashModule()).inject(this);
        presenter.bindView(this);
        presenter.checkIsUserLogin();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void onUserStateLoaded(boolean isLogin) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Subcomponent(modules = SplashModule.class)
    @ActivityScope
    public interface SplashComponent {
        void inject(@NonNull SplashActivity activity);
    }

    @Module
    public static class SplashModule {
        @Provides
        @ActivityScope
        @NonNull
        public SplashPresenter provideSplashPresenter() {
            return new SplashPresenter();
        }
    }
}
