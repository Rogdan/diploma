package ua.rogdan.trag.ui.account.about_app;

import android.app.Fragment;

import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseActivity;
import ua.rogdan.trag.core.BaseFragment;

public class AboutAppActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_fragment;
    }

    @Override
    protected void initView() {
        BaseFragment fragment = new AboutAppFragment();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
