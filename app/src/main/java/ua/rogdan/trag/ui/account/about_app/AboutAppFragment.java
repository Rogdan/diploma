package ua.rogdan.trag.ui.account.about_app;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import ua.rogdan.trag.R;
import ua.rogdan.trag.core.BaseFragment;

public class AboutAppFragment extends BaseFragment {
    @BindView(R.id.title_tv)
    protected TextView titleTV;
    @BindView(R.id.title_left_iv)
    protected ImageView backIV;
    @BindView(R.id.app_version_tv)
    protected TextView appVersionTV;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_about_app;
    }

    @Override
    protected void initView() {
        titleTV.setText(R.string.about_app);
        backIV.setImageResource(R.drawable.back_arrow);
        backIV.setVisibility(View.VISIBLE);

        try {
            PackageInfo pInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            String version = pInfo.versionName;
            String versionFormat = getString(R.string.version_format, version);
            appVersionTV.setText(String.format(versionFormat, version));

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.instagram_layout)
    protected void gotoInstagram() {
        String link = getString(R.string.insta_link_https);
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(browserIntent);
    }

    @OnClick(R.id.title_left_iv)
    protected void goBack() {
        getActivity().onBackPressed();
    }

    @Override
    protected void providePresenter() {

    }

    @Override
    protected void unbindPresenter() {

    }
}
