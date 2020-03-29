package com.example.administrator.competition.base;

import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseFragment;

public abstract class BaseUrlFragment extends BaseFragment {
    protected CommonPresenter mPresenter = new CommonPresenter(this);
}
