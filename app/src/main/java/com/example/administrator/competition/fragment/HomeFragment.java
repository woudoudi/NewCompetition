package com.example.administrator.competition.fragment;

import com.example.administrator.competition.R;
import com.yidao.module_lib.base.BaseFragment;

import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends BaseFragment {

    HomeFirstFragment mHomeFragment = new HomeFirstFragment();
    GuessFragment mGuessFragment = new GuessFragment();

    BaseFragment[] mBaseFragments = new BaseFragment[]{mHomeFragment, mGuessFragment};

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_home;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {
        showFragment(0);

        FragmentTransaction transaction = getAty().getSupportFragmentManager().beginTransaction();
        if (!mGuessFragment.isAdded()) {
            transaction.add(R.id.frameLayout_home_home, mGuessFragment, 1 + "").commit();
        }
    }

    public void setTitleType(int tableType){
        if(mGuessFragment!=null){
            mGuessFragment.setTitleType(tableType);
        }
    }

    public void showFragment(int position) {
        FragmentTransaction transaction = getAty().getSupportFragmentManager().beginTransaction();
        if (!mBaseFragments[position].isAdded()) {
            transaction.add(R.id.frameLayout_home_home, mBaseFragments[position], position + "");
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
}
