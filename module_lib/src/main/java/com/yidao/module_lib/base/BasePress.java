package com.yidao.module_lib.base;


import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;

/**
 * Created by xiaochan on 2017/6/19.
 */

public class BasePress<T extends IBaseView> implements IBasePress {

    protected T view;

    public BasePress(T view) {
        this.view = view;
    }

    public T getView() {
        return view;
    }

    @Override
    public void success(ResponseBean responseBean) {
        getView().alertSuccess();
    }

    @Override
    public void failed(ResponseBean responseBean) {


    }
}
