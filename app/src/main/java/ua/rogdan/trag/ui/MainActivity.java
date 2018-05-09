package ua.rogdan.trag.ui;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

import butterknife.BindView;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseActivity;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.ui.employees.EmployeesFragment;
import ua.rogdan.trag.ui.map.RoadBuilderFragment;

public class MainActivity extends BaseActivity {
    @BindView(R.id.bottom_navigation)
    protected AHBottomNavigation bottomNavigation;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initBottomMenu();
    }

    private void initBottomMenu() {
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.menu_main);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        // Change colors
        bottomNavigation.setAccentColor(ContextCompat.getColor(this, R.color.dark_mint_green));
        bottomNavigation.setInactiveColor(ContextCompat.getColor(this, R.color.charcoal_alpha));
        navigationAdapter.setupWithBottomNavigation(bottomNavigation, null);

        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                selectTab(position, wasSelected);
                return !wasSelected;
            }
        });

        selectTab(POSITION_EMPLOYEES, false);
    }

    private void selectTab(int position, boolean wasSelected) {
        if (!wasSelected) {
            BaseFragment fragment = null;

            switch (position) {
                case POSITION_EMPLOYEES:
                    fragment = new EmployeesFragment();
                    break;
                case POSITION_TASKS:

                    break;
                case POSITION_MAP:
                    fragment = new RoadBuilderFragment();
                    break;
                case POSITION_ACCOUNT:

                    break;
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment, CURRENT_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_FRAGMENT_TAG);
        if(fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    public static final String CURRENT_FRAGMENT_TAG = "current_fragment";

    private static final int POSITION_EMPLOYEES = 0;
    private static final int POSITION_TASKS = 1;
    private static final int POSITION_MAP = 2;
    private static final int POSITION_ACCOUNT = 3;
}
