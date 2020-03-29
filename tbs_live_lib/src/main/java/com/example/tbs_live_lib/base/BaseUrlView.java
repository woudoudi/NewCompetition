package com.example.tbs_live_lib.base;

import com.example.tbs_live_lib.mvp.presenter.CommonPresenter;
import com.yidao.module_lib.base.BaseView;

public abstract class BaseUrlView extends BaseView {

    protected CommonPresenter mPresenter = new CommonPresenter(this);

}
