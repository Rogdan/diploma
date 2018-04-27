package ua.rogdan.trag.ui;

import android.support.v4.content.ContextCompat;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

import java.util.IllegalFormatCodePointException;

import butterknife.BindView;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseActivity;
import ua.rogdan.trag.core.BaseFragment;

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
                if (!wasSelected) {
                    BaseFragment fragment = null;

                    switch (position) {
                        case POSITION_HUMANS:

                            break;
                        case POSITION_TASKS:

                            break;
                        case POSITION_MAP:

                            break;
                        case POSITION_ACCOUNT:

                            break;
                    }

                    if (fragment != null) {

                    }

                    return true;
                }

                return false;
            }
        });
    }

    private static final int POSITION_HUMANS = 0;
    private static final int POSITION_TASKS = 1;
    private static final int POSITION_MAP = 2;
    private static final int POSITION_ACCOUNT = 3;
}
