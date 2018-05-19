package ua.rogdan.trag.ui.tasks;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import ua.rogdan.trag.R;
import ua.rogdan.trag.adapters.TabSelectionListener;
import ua.rogdan.trag.adapters.TasksPagerAdapter;
import ua.rogdan.trag.core.BaseFragment;

public class TasksContainerFragment extends BaseFragment {
    @BindView(R.id.tab_layout)
    protected TabLayout tabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_tasks_container;
    }

    @Override
    protected void initView() {
        String[] tabTitles = getResources().getStringArray(R.array.tasks_tabs);
        TasksPagerAdapter adapter = new TasksPagerAdapter(getChildFragmentManager(), tabTitles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addOnTabSelectedListener(new TabSelectionListener(viewPager));
        setupTypeface(tabTitles);
    }

    private void setupTypeface(String[] tabTitles) {
        for (int i = 0; i < tabTitles.length; i++) {
            String title = tabTitles[i];
            View view = LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.item_tab_title, null);
            TextView textView = view.findViewById(R.id.tab_title_tv);
            textView.setText(title);
            if(i == 0) {
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            }else {
                textView.setTypeface(Typeface.DEFAULT);
            }
            tabLayout.getTabAt(i).setCustomView(view);
            LinearLayout layout = ((LinearLayout) ((LinearLayout) tabLayout.getChildAt(0)).getChildAt(i));
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) layout.getLayoutParams();
            layoutParams.weight = title.length();
            layout.setLayoutParams(layoutParams);
        }
    }


    @OnClick(R.id.add_task_iv)
    protected void addTask() {
        Toast.makeText(getContext(), "click", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
