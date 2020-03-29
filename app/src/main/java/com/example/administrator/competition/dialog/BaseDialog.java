package com.example.administrator.competition.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;

import com.example.administrator.competition.R;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ibase.IBaseEventPlus;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;
import com.yidao.module_lib.base.ievent.video.ICommonEvent;
import com.yidao.module_lib.widget.LoadingAlertDialog;

import butterknife.ButterKnife;

public abstract class BaseDialog<T extends IBasePress> extends Dialog implements IBaseView,IBaseEventPlus {

    protected T mPress;

    protected void setPress(T press) {
        mPress = press;
    }

    protected T getPress() {
        return mPress;
    }

    private Context mContext;

    public BaseDialog(Context context) {
        super(context);
        this.mContext = context;
        init();
        initPress();
    }



    public BaseDialog(Context context, int themeResId) {
        super(context,themeResId);
        init();
        initPress();
    }

    protected void init(){
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        getWindow().setWindowAnimations(R.style.dialog_style);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    protected abstract int getLayoutId();
    protected abstract void initPress();

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {

    }

    @Override
    public Activity getAty() {
        return (Activity) mContext;
    }

    @Override
    public Context getCtx() {
        return mContext;
    }

    @Override
    public void alertSuccess() {

    }

    @Override
    public void alertFailed() {

    }

    @Override
    public void showToast(String content) {

    }

    @Override
    public void showToast(int resId) {

    }

    @Override
    public void showLongToast(String content) {

    }

    @Override
    public void showLongToast(int resId) {

    }

    @Override
    public void cancelToast() {

    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view) {
        Intent intent = new Intent(getAty(), view);
        getCtx().startActivity(intent);
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view, Bundle bundle) {
        Intent intent = new Intent(getAty(), view);
        intent.putExtras(bundle);
        getCtx().startActivity(intent);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view) {
        Intent intent = new Intent(getAty(), view);
        getCtx().startActivity(intent);
        getAty().finish();
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view, Bundle bundle) {
        Intent intent = new Intent(getAty(), view);
        intent.putExtras(bundle);
        getCtx().startActivity(intent);
        getAty().finish();
    }

    @Override
    public void dismissLoaddingDialog() {

    }

    @Override
    public void showLoaddingDialog() {

    }

    @Override
    public void setLoaddingMsg(String msg) {

    }

    @Override
    public LoadingAlertDialog getLoadingDialog() {
        return null;
    }
}
