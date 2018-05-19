package ua.rogdan.trag.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ua.rogdan.trag.ui.account.AccountFragment;
import ua.rogdan.trag.ui.account.about_app.AboutAppFragment;

public class TasksPagerAdapter extends FragmentStatePagerAdapter {
    private String[] titles;

    public TasksPagerAdapter(FragmentManager fm, String[] titles) {
        super(fm);
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment result = null;
        switch (position) {
            case ACTIVE_TASKS_POSITION:
                result = new AccountFragment();
                break;
            case ARCHIVE_TASKS_POSITION:
                result = new AboutAppFragment();
                break;
        }

        return result;
    }

    @Override
    public int getCount() {
        return TABS_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    private static final int TABS_COUNT = 2;
    private static final int ACTIVE_TASKS_POSITION = 0;
    private static final int ARCHIVE_TASKS_POSITION = 1;
}
