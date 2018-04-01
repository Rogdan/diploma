package ua.rogdan.trag.di.components;

import dagger.Component;
import ua.rogdan.trag.di.modules.DataModule;
import ua.rogdan.trag.ui.splash.SplashActivity;

@Component(modules = {DataModule.class})
public interface ApplicationComponent {

    SplashActivity.SplashComponent plus(SplashActivity.SplashModule splashModule);
}
