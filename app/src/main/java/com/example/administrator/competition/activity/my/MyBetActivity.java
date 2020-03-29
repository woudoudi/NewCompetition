package com.example.administrator.competition.activity.my;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.fragment.guess.BetRecordFragment;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBetActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_king)
    TextView tvKing;
    @BindView(R.id.tv_all_guess)
    TextView tvAllGuess;


    BetRecordFragment mHomeFragment = new BetRecordFragment(BetRecordFragment.Kind_Mode);
    BetRecordFragment mGuessFragment = new BetRecordFragment(BetRecordFragment.Kind_Not_Mode);

    BaseFragment[] mBaseFragments = new BaseFragment[]{mHomeFragment, mGuessFragment};
    @BindView(R.id.rl_bet_title)
    RelativeLayout rlBetTitle;

    @Override
    protected int getView() {
        return R.layout.activity_my_bet;
    }


    @Override
    public void init() {
        tvTitle.setText("押注记录");

        int betValue = getIntent().getIntExtra(CommonConfig.betRecordKey, 0);
        if (betValue == CommonConfig.betNotKindValue) {
            rlBetTitle.setVisibility(View.GONE);
            showFragment(1);
        } else {
            showFragment(0);
        }

    }

    @OnClick({R.id.iv_back, R.id.tv_king, R.id.tv_all_guess})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_king:
                showFragment(0);
                break;
            case R.id.tv_all_guess:
                showFragment(1);
                break;
        }
    }

    public void showFragment(int position) {

        tvKing.setTextColor(getResources().getColor(position == 0 ? R.color.white : R.color.color_999999));
        tvKing.setBackgroundResource(position == 0 ? R.drawable.bet_text_bg_press : R.drawable.bet_text_bg);

        tvAllGuess.setTextColor(getResources().getColor(position == 1 ? R.color.white : R.color.color_999999));
        tvAllGuess.setBackgroundResource(position == 1 ? R.drawable.bet_text_bg_press : R.drawable.bet_text_bg);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mBaseFragments[position].isAdded()) {
            transaction.add(R.id.frameLayout_bet_record, mBaseFragments[position], position + "");
        }
        for (int i = 0; i < mBaseFragments.length; i++) {
            if (position == i) {
                transaction.show(mBaseFragments[i]);
            } else {
                transaction.hide(mBaseFragments[i]);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
