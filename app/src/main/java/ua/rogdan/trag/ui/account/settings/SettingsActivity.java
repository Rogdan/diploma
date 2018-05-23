package ua.rogdan.trag.ui.account.settings;

import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseActivity;

public class SettingsActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_with_fragment;
    }

    @Override
    protected void initView() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new SettingsFragment())
                .commit();
    }
}
