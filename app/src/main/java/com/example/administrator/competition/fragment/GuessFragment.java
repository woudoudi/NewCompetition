package com.example.administrator.competition.fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.guess.GuessCompetitionActivity;
import com.example.administrator.competition.activity.my.MyBetActivity;
import com.example.administrator.competition.adapter.GuessAdapter;
import com.example.administrator.competition.dialog.MorePopWindow;
import com.example.administrator.competition.dialog.RuleDialog;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.fragment.guess.GuessPrimaryFragment;
import com.google.android.material.tabs.TabLayout;
import com.yidao.module_lib.base.BaseFragment;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;

public class GuessFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewPage_guess)
    ViewPager viewPageGuess;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.iv_detail)
    ImageView ivDetail;

    /**
     * 当前页面处于kind 还是 number 类型
     */
    private int mCurrentPageMode;
    private GuessAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_guess;
    }

    @Override
    protected void initPress() {
    }

    public void setTitleType(int tableType) {

        String suffix = tableType == CommonConfig.TenPersonTable ? "(十人桌)" : tableType == CommonConfig.OneVOneTable ? "(1v1)" : "";

        String title = CommonConfig.KindAndNumberMode == CommonConfig.NumberType ? "数字抢答" : CommonConfig.KindAndNumberMode == CommonConfig.KindType ? "王者常识" : "";

        if (tvTitle != null) {
            tvTitle.setText(title+suffix);
        }
    }

    @Override
    protected void init() {
        mCurrentPageMode = CommonConfig.KindAndNumberMode;

        mAdapter = new GuessAdapter(getFragmentManager());
        viewPageGuess.setAdapter(mAdapter);
        tabLayout.setupWithViewPager(viewPageGuess);
        viewPageGuess.setOffscreenPageLimit(2);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            if(mCurrentPageMode != CommonConfig.KindAndNumberMode){
                if(mAdapter!=null){
                    mAdapter.sync();
                }
                mCurrentPageMode = CommonConfig.KindAndNumberMode;
            }
        }
    }

    @OnClick(R.id.iv_detail)
    public void onViewClicked() {
        MorePopWindow popWindow = new MorePopWindow(getCtx(), ivDetail);
        popWindow.setOnItemClick(new MorePopWindow.OnItemClick() {
            @Override
            public void onBetRule() {
                RuleDialog ruleDialog = new RuleDialog(getCtx());
                ruleDialog.show();
            }
            @Override
            public void onBetRecord() {
                Bundle bundle = new Bundle();
                bundle.putInt(CommonConfig.betRecordKey,CommonConfig.betNotKindValue);
                skipActivity(MyBetActivity.class,bundle);
            }
        });
        popWindow.showPopWindow();
    }
}
