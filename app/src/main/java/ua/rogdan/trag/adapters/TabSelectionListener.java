package ua.rogdan.trag.adapters;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import ua.rogdan.trag.R;

public class TabSelectionListener implements TabLayout.OnTabSelectedListener {
    private ViewPager viewPager;

    public TabSelectionListener(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        setTabTextColor(tab, true);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        setTabTextColor(tab, false);
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        setTabTextColor(tab, true);
    }

    private void setTabTextColor(TabLayout.Tab tab, boolean bold) {
        View customView = tab.getCustomView();
        if(customView != null) {
            TextView textView = customView.findViewById(R.id.tab_title_tv);
            if(bold) {
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            } else {
                textView.setTypeface(Typeface.DEFAULT);
            }
        }
    }
}
