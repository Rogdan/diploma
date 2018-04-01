package ua.rogdan.trag.di;

import android.content.Context;

import ua.rogdan.trag.di.components.ApplicationComponent;
import ua.rogdan.trag.di.components.DaggerApplicationComponent;
import ua.rogdan.trag.di.modules.DataModule;

public class Injector {
    private static ApplicationComponent applicationComponent;

    public static void initInjector(Context context) {
        if (applicationComponent == null) {
            DataModule dataModule = new DataModule();
            applicationComponent = DaggerApplicationComponent.builder()
                    .dataModule(dataModule)
                    .build();
        }
    }

    public static ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
