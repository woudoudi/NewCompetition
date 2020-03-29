package com.example.administrator.competition.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.entity.CommonConfig;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeKindActivity extends BaseView {

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
    @BindView(R.id.tv_free_title)
    TextView tvFreeTitle;
    @BindView(R.id.tv_free_content)
    TextView tvFreeContent;

    @Override
    protected int getView() {
        return R.layout.activity_home_king;
    }


    @Override
    public void init() {
        tvTitle.setText("王者专场");
    }


    @OnClick({R.id.iv_back, R.id.rl_king_live, R.id.rl_record, R.id.rl_free_confrontation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.rl_king_live:
                Bundle bundle = new Bundle();
                bundle.putInt(CommonConfig.recordAndLiveKey,CommonConfig.LiveValue);
                skipActivity(HomeRecordActivity.class,bundle);
                break;
            case R.id.rl_record:
                bundle = new Bundle();
                bundle.putInt(CommonConfig.recordAndLiveKey,CommonConfig.recordValue);
                skipActivity(HomeRecordActivity.class,bundle);
                break;
            case R.id.rl_free_confrontation:
                skipActivity(HomeFreeConfrontationActivity.class);
                break;
        }
    }
}
