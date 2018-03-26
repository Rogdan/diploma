package ua.rogdan.trag.core;


import android.os.Bundle;

public abstract class BaseActivityWithPresenter extends BaseActivity {

    protected abstract void providePresenter();

    protected abstract void unbindPresenter();

    private boolean isActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        providePresenter();
    }

    @Override
    protected void onDestroy() {
        unbindPresenter();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }
}
