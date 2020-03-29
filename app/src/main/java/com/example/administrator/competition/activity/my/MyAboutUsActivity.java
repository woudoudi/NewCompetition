package com.example.administrator.competition.activity.my;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;

public class MyAboutUsActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    protected int getView() {
        return R.layout.activity_my_about_us;
    }


    @Override
    public void init() {
        tvTitle.setText("关于我们");
        tvVersion.setText(getString(R.string.app_name)+" V"+packageCodeName());
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        ViewManager.getInstance().finishView();
    }

    private String packageCodeName() {
        PackageManager manager = getPackageManager();
        String codeName = "1.0.0";
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            codeName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return codeName;
    }
}
