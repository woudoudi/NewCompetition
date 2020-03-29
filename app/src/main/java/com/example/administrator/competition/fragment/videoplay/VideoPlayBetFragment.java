package com.example.administrator.competition.fragment.videoplay;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.fragment.guess.BetFragment;
import com.yidao.module_lib.base.BaseFragment;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class VideoPlayBetFragment extends BaseFragment {

    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_primary)
    TextView tvPrimary;
    @BindView(R.id.tv_middle)
    TextView tvMiddle;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.ll_rule_container)
    LinearLayout llRuleContainer;

    private BetFragment mBetFragment = new BetFragment(BetFragment.Primary_Mode);
    private BetFragment mBetFragment1 = new BetFragment(BetFragment.Middle_Mode);
    private BetFragment mBetFragment2 = new BetFragment(BetFragment.Middle_Mode);

    BaseFragment[] mBaseFragments = new BaseFragment[]{mBetFragment, mBetFragment1, mBetFragment2};


    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_video_play_bet;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {
        showFragment(0);
    }

    @OnClick({R.id.tv_rule, R.id.tv_primary, R.id.tv_middle, R.id.tv_high, R.id.tv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_rule:
                if(llRuleContainer.getVisibility()==View.GONE){
                    llRuleContainer.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv_primary:
                showFragment(0);
                break;
            case R.id.tv_middle:
                showFragment(1);
                break;
            case R.id.tv_high:
                showFragment(2);
                break;
            case R.id.tv_close:
                llRuleContainer.setVisibility(View.GONE);
                break;
        }
    }



    /**
     * 控制竖屏时押注初级、中级和高级的切换
     *
     * @param position
     */
    private void showFragment(int position) {

        tvPrimary.setTextColor(getResources().getColor(position == 0 ? R.color.white : R.color.color_999999));
        tvPrimary.setBackgroundResource(position == 0 ? R.drawable.bet_text_bg_press : R.drawable.bet_text_bg);

        tvMiddle.setTextColor(getResources().getColor(position == 1 ? R.color.white : R.color.color_999999));
        tvMiddle.setBackgroundResource(position == 1 ? R.drawable.bet_text_bg_press : R.drawable.bet_text_bg);

        tvHigh.setTextColor(getResources().getColor(position == 2 ? R.color.white : R.color.color_999999));
        tvHigh.setBackgroundResource(position == 2 ? R.drawable.bet_text_bg_press : R.drawable.bet_text_bg);

        FragmentTransaction transaction = getAty().getSupportFragmentManager().beginTransaction();
        if (!mBaseFragments[position].isAdded()) {
            transaction.add(R.id.frameLayout_video_bet, mBaseFragments[position], position + "");
        }
        for (int i = 0; i < mBaseFragments.length; i++) {
            if (position == i) {
                transaction.show(mBaseFragments[i]);
            } else {
                transaction.hide(mBaseFragments[i]);
            }
        }
        transaction.commit();
    }
}
