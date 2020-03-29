package com.example.http_lib.model;

import android.util.Log;

import com.example.http_lib.HttpClient;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created by xiaochan on 2017/6/19.
 */

public abstract class BaseModel<T extends IBasePress> implements IBaseModel<T> {
    private T  mPress;
    protected BaseBean bean;

    public BaseModel(T press) {
        this.mPress = press;
    }

    @Override
    public T getPress() {
        return this.mPress;
    }

    @Override
    public BaseBean getBean() {
        return bean;
    }

    @Override
    public void setBean(BaseBean bean) {
        this.bean = bean;
    }


    @Override
    public void request() {
        HttpClient.request(getBean(),this);
    }

    @Override
    public void request(boolean isEncrypt) {
        HttpClient.request(getBean(),isEncrypt,this);
    }

    @Override
    public void success(ResponseBean responseBean) {
        getPress().success(responseBean);
        Log.d("HttpClient:success",""+responseBean.toString());
    }

    @Override
    public void failed(ResponseBean responseBean) {
        getPress().failed(responseBean);
        Log.d("HttpClient:fail",responseBean.toString());

    }

}
