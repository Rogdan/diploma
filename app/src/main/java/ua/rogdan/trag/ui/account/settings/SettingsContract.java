package ua.rogdan.trag.ui.account.settings;

import ua.rogdan.trag.core.IBaseView;
import ua.rogdan.trag.data.another.Settings;

public interface SettingsContract {
    interface ISettingsView extends IBaseView {
        void onLanguageLoaded(String language);
        void onSwitcherChangingError();
        void onSwitcherChangedSuccessfully();
        void onPushStateLoaded(Settings settings);
    }

    interface ISettingsPresenter {
        void setPushEnabled(Settings settings);
        void loadData();
    }
}
