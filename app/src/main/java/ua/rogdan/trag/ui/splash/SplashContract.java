package ua.rogdan.trag.ui.splash;

import ua.rogdan.trag.core.IBaseView;

public interface SplashContract {
    interface ISplashView extends IBaseView {
        void onUserStateLoaded(boolean isLogin);
    }

    interface ISplashPresenter {
        void checkIsUserLogin();
    }
}
