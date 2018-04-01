package ua.rogdan.trag.ui.splash;

import java.util.Random;

import ua.rogdan.trag.core.Presenter;

public class SplashPresenter extends Presenter<SplashContract.ISplashView> implements SplashContract.ISplashPresenter {

    @Override
    public void checkIsUserLogin() {
        //todo check is user really logged in
        Random r = new Random();
        view().onUserStateLoaded(r.nextBoolean());
    }
}