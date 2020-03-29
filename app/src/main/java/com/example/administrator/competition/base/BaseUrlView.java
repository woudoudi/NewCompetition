package com.example.administrator.competition.base;

import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseView;

public abstract class BaseUrlView extends BaseView {
    protected CommonPresenter mPresenter = new CommonPresenter(this);
}
