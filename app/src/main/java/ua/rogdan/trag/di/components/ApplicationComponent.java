package ua.rogdan.trag.di.components;

import dagger.Component;
import ua.rogdan.trag.di.modules.DataModule;
import ua.rogdan.trag.ui.account.AccountFragment;
import ua.rogdan.trag.ui.account.my_data.MyDataFragment;
import ua.rogdan.trag.ui.account.settings.SettingsFragment;
import ua.rogdan.trag.ui.employees.EmployeesFragment;
import ua.rogdan.trag.ui.map.RoadBuilderFragment;
import ua.rogdan.trag.ui.splash.SplashActivity;
import ua.rogdan.trag.ui.tasks.active.ActiveTasksFragment;
import ua.rogdan.trag.ui.tasks.archive.ArchiveTasksFragment;

@Component(modules = {DataModule.class})
public interface ApplicationComponent {
    SplashActivity.SplashComponent plus(SplashActivity.SplashModule splashModule);
    RoadBuilderFragment.RoadBuilderComponent plus(RoadBuilderFragment.RoadBuilderModule roadBuilderModule);

    EmployeesFragment.EmployeesComponent plus(EmployeesFragment.EmployeesModule employeesModule);

    AccountFragment.AccountComponent plus(AccountFragment.AccountModule accountModule);

    ActiveTasksFragment.ActiveTasksComponent plus(ActiveTasksFragment.ActiveTasksModule activeTasksModule);

    ArchiveTasksFragment.ArchiveTasksComponent plus(ArchiveTasksFragment.ArchiveTasksModule archiveTasksModule);

    SettingsFragment.SettingsComponent plus(SettingsFragment.SettingsModule settingsModule);

    MyDataFragment.MyDataComponent plus(MyDataFragment.MyDataModule myDataModule);
}
