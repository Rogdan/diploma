package ua.rogdan.trag.core;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

public abstract class BaseFragmentWithPresenter extends BaseFragment {
    protected abstract void providePresenter();

    protected abstract void unbindPresenter();


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        providePresenter();
    }

    @Override
    public void onDestroyView() {
        unbindPresenter();
        super.onDestroyView();
    }


    public void onUnsuccessServerResponse(int code, String title, String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public boolean isActive() {
        return getActivity() != null && isAdded() && !isDetached()
                && getView() != null && !isRemoving();
    }
}
