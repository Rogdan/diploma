package ua.rogdan.trag.ui.account;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;
import ua.rogdan.trag.ui.account.about_app.AboutAppActivity;
import ua.rogdan.trag.ui.account.my_data.MyDataActivity;
import ua.rogdan.trag.ui.account.settings.SettingsActivity;

public class AccountFragment extends BaseFragment implements AccountContract.IAccountView {
    @Inject
    protected AccountPresenter presenter;

    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.title_right_iv)
    protected ImageView exitIV;
    @BindView(R.id.screen_content)
    protected View screenContent;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_account;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.driver);
        exitIV.setImageResource(R.drawable.exit_icon);
        exitIV.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.title_right_iv)
    protected void exitRequest() {
        Toast.makeText(getContext(), "Exit", Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.my_data_layout, R.id.settings_layout, R.id.about_app_layout})
    public void gotoMenuItem(View item) {
        switch (item.getId()) {
            case R.id.my_data_layout:
                Intent myData = new Intent(getActivity(), MyDataActivity.class);
                startActivity(myData);
                break;
            case R.id.settings_layout:
                Intent settings = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settings);
                break;
            case  R.id.about_app_layout:
                Intent aboutApp = new Intent(getActivity(), AboutAppActivity.class);
                startActivity(aboutApp);
                break;
        }
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new AccountModule()).inject(this);
        presenter.bindView(this);
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void showProgress() {
        screenContent.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        screenContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Subcomponent(modules = AccountModule.class)
    @ActivityScope
    public interface AccountComponent {
        void inject(@NonNull AccountFragment fragment);
    }

    @Module
    public static class AccountModule {
        @Provides
        @ActivityScope
        @NonNull
        public AccountPresenter provideAccountPresenter() {
            return new AccountPresenter();
        }
    }
}