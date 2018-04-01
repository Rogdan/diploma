package ua.rogdan.trag;

import android.app.Application;

import ua.rogdan.trag.di.Injector;

public class TragApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Injector.initInjector(this);
    }
}
