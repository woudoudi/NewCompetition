package com.example.administrator.competition;

import android.view.animation.LinearInterpolator;

import com.example.administrator.competition.view.MultiScrollNumber;
import com.yidao.module_lib.anotation.FragmentId;
import com.yidao.module_lib.base.BaseView;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import jiguang.chat.activity.fragment.ChatFragment;

public class TestActivity extends BaseView {
    @BindView(R.id.scroll_number)
    MultiScrollNumber scrollNumber;

    ChatFragment mChatFragment = new ChatFragment();

    @Override
    protected int getView() {
        return R.layout.activity_test;
    }

    @Override
    public void init() {
        scrollNumber.setInterpolator(new LinearInterpolator());
        scrollNumber.setNumber(0, 9);

        showFragment(0);
    }

    @OnClick(R.id.btn_click)
    public void onViewClicked() {
        scrollNumber.setNumber(0, 9);
    }


    public void showFragment(@FragmentId int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mChatFragment.isAdded()) {
            transaction.add(R.id.frameLayout_test, mChatFragment, position + "");
        }
        transaction.show(mChatFragment).commitAllowingStateLoss();
    }

}
