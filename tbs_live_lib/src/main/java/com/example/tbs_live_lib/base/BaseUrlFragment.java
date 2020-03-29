package com.example.tbs_live_lib.base;


import com.example.tbs_live_lib.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseFragment;

public abstract class BaseUrlFragment extends BaseFragment {

    protected CommonPresenter mPresenter = new CommonPresenter(this);

}
