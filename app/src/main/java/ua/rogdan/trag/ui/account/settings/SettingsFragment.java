package ua.rogdan.trag.ui.account.settings;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;
import ua.rogdan.trag.data.another.Settings;
import ua.rogdan.trag.di.Injector;
import ua.rogdan.trag.di.scope.ActivityScope;

public class SettingsFragment extends BaseFragment implements SettingsContract.ISettingsView{
    @Inject
    protected SettingsPresenter presenter;

    @BindView(R.id.title_left_iv)
    protected ImageView backIV;
    @BindView(R.id.title_right_iv)
    protected ImageView helpIV;
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.talons_switcher)
    protected Switch talonsSwitcher;
    @BindView(R.id.partners_switcher)
    protected Switch partnersSwitcher;
    @BindView(R.id.russian_radio)
    protected RadioButton russianRadio;
    @BindView(R.id.ukrainian_radio)
    protected RadioButton ukrainianRadio;
    @BindView(R.id.app_language_tv)
    protected TextView appLanguageTV;
    @BindView(R.id.receive_message_tv)
    protected TextView receiveMessageTV;
    @BindView(R.id.use_terms_tv)
    protected TextView useTermsTV;
    @BindView(R.id.top_snack_coordinator)
    protected View topSnackCoordinator;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.screen_content_layout)
    protected View screenContentView;

    private Switch lastPickedSwitcher;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings;
    }

    @Override
    protected void initView() {
        backIV.setImageResource(R.drawable.back_arrow);
        backIV.setVisibility(View.VISIBLE);
        titleTV.setText(R.string.settings);

        talonsSwitcher.setOnCheckedChangeListener(switcherListener);
        partnersSwitcher.setOnCheckedChangeListener(switcherListener);
    }

    private CompoundButton.OnCheckedChangeListener switcherListener = (compoundButton, b) -> {
        Settings settings = new Settings();
        settings.setTicketNotificationOn(talonsSwitcher.isChecked());
        settings.setReferalNotificationOn(partnersSwitcher.isChecked());

        switch (compoundButton.getId()) {
            case R.id.talons_switcher:
                lastPickedSwitcher = talonsSwitcher;
                break;
            case R.id.partners_switcher:
                lastPickedSwitcher = partnersSwitcher;
                break;
        }

        presenter.setPushEnabled(settings);
    };

    private void quitSwitch(Switch switcher, boolean isChecked) {
        switcher.setOnCheckedChangeListener(null);
        switcher.setChecked(isChecked);
        switcher.setOnCheckedChangeListener(switcherListener);
    }

    @OnClick(R.id.title_left_iv)
    protected void goBack() {
        getActivity().onBackPressed();
    }

    @OnClick(R.id.title_right_iv)
    protected void openHelp() {
    }

    @OnCheckedChanged({R.id.russian_radio, R.id.ukrainian_radio})
    protected void onRadioSelected(CompoundButton button, boolean isChecked) {
        int textColor;

        if (isChecked) {
            textColor = ContextCompat.getColor(getContext(), R.color.white);
        } else {
            textColor = ContextCompat.getColor(getContext(), R.color.black);
        }

        switch (button.getId()) {
            case R.id.ukrainian_radio:
                ukrainianRadio.setTextColor(textColor);
                break;
            case R.id.russian_radio:
                russianRadio.setTextColor(textColor);
                break;
        }
    }

    @OnClick(R.id.use_terms_tv)
    protected void openTermOfUse() {
        Toast.makeText(getContext(), "А их пока нету...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        setButtonsEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        setButtonsEnabled(true);
        progressBar.setVisibility(View.GONE);
    }

    private void setButtonsEnabled(boolean isEnabled) {
        talonsSwitcher.setEnabled(isEnabled);
        partnersSwitcher.setEnabled(isEnabled);
        ukrainianRadio.setEnabled(isEnabled);
        russianRadio.setEnabled(isEnabled);
        useTermsTV.setEnabled(isEnabled);
        helpIV.setEnabled(isEnabled);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new SettingsModule()).inject(this);
        presenter.bindView(this);
        presenter.loadData();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Override
    public void onLanguageLoaded(String language) {

    }

    @Override
    public void onSwitcherChangingError() {

        quitSwitch(lastPickedSwitcher, !lastPickedSwitcher.isChecked());
    }

    @Override
    public void onSwitcherChangedSuccessfully() {

    }

    @Override
    public void onPushStateLoaded(Settings settings) {
        quitSwitch(partnersSwitcher, settings.isReferalNotificationOn());
        quitSwitch(talonsSwitcher, settings.isTicketNotificationOn());
    }

    @Subcomponent(modules = SettingsModule.class)
    @ActivityScope
    public interface SettingsComponent {
        void inject(@NonNull SettingsFragment fragmenta);
    }

    @Module
    public static class SettingsModule {
        @Provides
        @ActivityScope
        @NonNull
        public SettingsPresenter provideSettingsPresenter() {
            return new SettingsPresenter();
        }
    }
}
