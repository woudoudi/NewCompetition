package com.example.administrator.competition.activity.home;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.dialog.QuickResponsePopWindow;
import com.example.administrator.competition.entity.CommonConfig;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeGuessActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_king_title)
    TextView tvKingTitle;
    @BindView(R.id.tv_king_content)
    TextView tvKingContent;
    @BindView(R.id.tv_trumpet_title)
    TextView tvTrumpetTitle;
    @BindView(R.id.tv_trumpet_content)
    TextView tvTrumpetContent;
    @BindView(R.id.ll_guess_rootView)
    LinearLayout rootView;

    @Override
    protected int getView() {
        return R.layout.activity_home_guess;
    }

    @Override
    public void init() {
        tvTitle.setText("全民竞猜");
    }

    @OnClick({R.id.iv_back, R.id.rl_number_race, R.id.rl_king_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.rl_number_race:
                CommonConfig.KindAndNumberMode = CommonConfig.NumberType;
                QuickResponsePopWindow mPopWindow = new QuickResponsePopWindow(getCtx(),CommonConfig.NumberType);
                mPopWindow.show();
                break;
            case R.id.rl_king_mode:
                CommonConfig.KindAndNumberMode = CommonConfig.KindType;
                mPopWindow = new QuickResponsePopWindow(getCtx(),CommonConfig.KindType);
                mPopWindow.show();
                break;
        }
    }
}
