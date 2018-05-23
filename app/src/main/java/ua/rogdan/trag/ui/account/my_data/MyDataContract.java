package ua.rogdan.trag.ui.account.my_data;

import ua.rogdan.trag.core.IBaseView;

public interface MyDataContract {
    interface IMyDataView extends IBaseView {
        void onUserDataLoaded(String phoneNumber, String name, String email);
        void onLogOutSuccessfully();
    }

    interface IMyDataPresenter {
        void loadUserData();
        void disconnectAccessForAll();
        void logOut();
    }
}
