package com.example.administrator.competition.adapter;

import android.os.Bundle;

import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.fragment.guess.GuessPrimaryFragment;
import com.yidao.module_lib.base.BaseFragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class GuessAdapter extends FragmentPagerAdapter {

    private GuessPrimaryFragment mGuessPrimaryFragment = new GuessPrimaryFragment();
    private GuessPrimaryFragment mGuessMiddleFragment = new GuessPrimaryFragment();
    private GuessPrimaryFragment mGuessHighFragment = new GuessPrimaryFragment();


    private String[] mTitles = {"初级场", "中级场","高级场"};
    private BaseFragment[] mBaseFragments = new BaseFragment[]{mGuessPrimaryFragment, mGuessMiddleFragment,mGuessHighFragment};

    public GuessAdapter(FragmentManager fm) {
        super(fm);
        Bundle bundle = new Bundle();
        bundle.putInt(CommonConfig.guessGradeKey,CommonConfig.primaryValue);
        mGuessPrimaryFragment.setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt(CommonConfig.guessGradeKey,CommonConfig.middleValue);
        mGuessMiddleFragment.setArguments(bundle);

        bundle = new Bundle();
        bundle.putInt(CommonConfig.guessGradeKey,CommonConfig.highValue);
        mGuessHighFragment.setArguments(bundle);
    }

    @Override
    public BaseFragment getItem(int position) {
        return mBaseFragments[position];
    }

    @Override
    public int getCount() {
        return mBaseFragments.length;
    }

    //重写这个方法，将设置每个Tab的标题
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    public void sync(){
        if(mGuessPrimaryFragment!=null){
            mGuessPrimaryFragment.onSync();
        }
        if(mGuessMiddleFragment!=null){
            mGuessMiddleFragment.onSync();
        }
        if(mGuessHighFragment!=null){
            mGuessHighFragment.onSync();
        }
    }
}
