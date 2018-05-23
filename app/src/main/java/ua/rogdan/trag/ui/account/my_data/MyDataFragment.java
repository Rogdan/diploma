package ua.rogdan.trag.ui.account.my_data;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
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

public class MyDataFragment extends BaseFragment implements MyDataContract.IMyDataView {
    @Inject
    protected MyDataPresenter presenter;

    @BindView(R.id.title_tv)
    protected TextView tvTitle;
    @BindView(R.id.phone_number_tv)
    protected EditText phoneNumberTV;
    @BindView(R.id.my_name_tv)
    protected TextView myNameTV;
    @BindView(R.id.email_tv)
    protected TextView emailTV;
    @BindView(R.id.cancel_for_all_tv)
    protected TextView cancelForAllTV;
    @BindView(R.id.enter_mail_tv)
    protected TextView enterMailTV;
    @BindView(R.id.enter_your_name_tv)
    protected TextView enterYourNameTV;
    @BindView(R.id.edit_email_layout)
    protected View editEmailLayout;
    @BindView(R.id.edit_my_name_layout)
    protected View editNameLayout;
    @BindView(R.id.safety_layout)
    protected View safetyLayout;
    @BindView(R.id.screen_content_layout)
    protected View screenContentView;
    @BindView(R.id.progress_bar)
    protected ProgressBar progressBar;
    @BindView(R.id.top_snack_coordinator)
    protected View snakeCoordinator;
    @BindView(R.id.title_left_iv)
    protected ImageView backIV;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my_data;
    }

    @Override
    protected void initView() {
        tvTitle.setText(R.string.my_data);
        backIV.setVisibility(View.VISIBLE);
        backIV.setOnClickListener(view -> getActivity().onBackPressed());
    }

    @OnClick(R.id.my_phone_layout)
    protected void onPhoneClicked() {
        Toast.makeText(getActivity(), R.string.you_cant_edit_phone, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.edit_my_name_layout, R.id.enter_your_name_tv})
    protected void editName() {
        String name = myNameTV.getText().toString();
        String title = getString(R.string.my_name);
        startDataEdit(title, title, name, "", EDIT_NAME_REQUEST_CODE);
    }

    @OnClick({R.id.edit_email_layout, R.id.enter_mail_tv})
    protected void editEmail() {
        String email = emailTV.getText().toString();
        String title = getString(R.string.email);
        String hint = getString(R.string.for_callback_with_us);

        startDataEdit(title, title, email, hint, EDIT_EMAIL_REQUEST_CODE);
    }

    @OnClick(R.id.cancel_for_all_tv)
    protected void cancelAccessForAll() {

    }

    public void startDataEdit(String title, String sign, String textToEdit, String hint, int requestCode) {

    }

    @Override
    public void onUserDataLoaded(String phoneNumber, String name, String email) {
        phoneNumberTV.setText(phoneNumber);

        switchViewText(name, enterYourNameTV, editNameLayout, myNameTV);
        switchViewText(email, enterMailTV, editEmailLayout, emailTV);
    }

    private void switchViewText(String text, View ifEmpty, View ifNotEmpty, TextView toSet) {
        if (text.isEmpty()) {
            ifEmpty.setVisibility(View.VISIBLE);
            ifNotEmpty.setVisibility(View.GONE);
        } else {
            ifEmpty.setVisibility(View.GONE);
            ifNotEmpty.setVisibility(View.VISIBLE);
        }

        toSet.setText(text);
    }

    @Override
    public void onLogOutSuccessfully() {
        Toast.makeText(getContext(), R.string.all_sessions_closed, Toast.LENGTH_SHORT).show();
        ;
        //restartLogin();
    }

    @Override
    public void showProgress() {
        screenContentView.setVisibility(View.INVISIBLE);
        safetyLayout.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        screenContentView.setVisibility(View.VISIBLE);
        safetyLayout.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void providePresenter() {
        Injector.getApplicationComponent().plus(new MyDataModule()).inject(this);
        presenter.bindView(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.loadUserData();
    }

    @Override
    protected void unbindPresenter() {
        presenter.unbindView(this);
    }

    @Subcomponent(modules = MyDataModule.class)
    @ActivityScope
    public interface MyDataComponent {
        void inject(@NonNull MyDataFragment fragment);
    }

    @Module
    public static class MyDataModule {
        @Provides
        @ActivityScope
        @NonNull
        public MyDataPresenter provideMyDataPresenter() {
            return new MyDataPresenter();
        }
    }

    public static final int EDIT_NAME_REQUEST_CODE = 23;
    public static final int EDIT_EMAIL_REQUEST_CODE = 24;
    public static final String EDITED_TEXT_EXTRA = "edited_text";
}
