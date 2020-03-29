package com.yidao.module_lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.yidao.module_lib.R;
import com.yidao.module_lib.base.ibase.IBasePress;
import com.yidao.module_lib.base.ibase.IBaseView;
import com.yidao.module_lib.utils.ToastUtil;
import com.yidao.module_lib.widget.LoadingAlertDialog;

import butterknife.ButterKnife;

/**
 * Created by dream on 2017/7/25.
 * 所有dialogFragment的基类
 */

public abstract class BaseDialog<T extends IBasePress> extends DialogFragment implements IBaseView {

    protected T mPress;

    protected LoadingAlertDialog mAlertDialog;

    protected Activity mActivity;

    protected abstract int getLayoutId();

    protected abstract void initPress();

    protected abstract void initArguments();

    protected abstract void init();

    protected void setPress(T press) {
        mPress = press;
    }

    protected T getPress() {
        return mPress;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = ((Activity) context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog);
        initArguments();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCancelable(true);
        getDialog().setCanceledOnTouchOutside(true);
        View rootView = inflater.inflate(getLayoutId(), container, false);
//        XFViewInject.inject(this, rootView);
        ButterKnife.bind(this,rootView);
        initPress();
        init();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        // cancelToast();
        super.onDestroyView();
    }

    @Override
    public void alertSuccess() {

    }

    @Override
    public void alertFailed() {

    }

    @Override
    public void showToast(String content) {
        ToastUtil.showShortToast(content);
    }

    @Override
    public void showToast(int resId) {
        ToastUtil.showShortToast(resId);
    }

    @Override
    public void showLongToast(String content) {
        ToastUtil.showLongToast(content);
    }

    @Override
    public void showLongToast(int resId) {
        ToastUtil.showLongToast(resId);
    }

    @Override
    public void cancelToast() {
        ToastUtil.dismiss();
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view) {
        Intent intent = new Intent(getActivity(), view);
        startActivity(intent);
    }

    @Override
    public void skipActivity(Class<? extends IBaseView> view, Bundle bundle) {
        Intent intent = new Intent(getActivity(), view);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view) {
        Intent intent = new Intent(getActivity(), view);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void skipActivityByFinish(Class<? extends IBaseView> view, Bundle bundle) {
        Intent intent = new Intent(getActivity(), view);
        intent.putExtras(bundle);
        startActivity(intent);
        getActivity().finish();
    }

    /**
     * 获取数据后，隐藏dialog
     */
    @Override
    public void dismissLoaddingDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }

    /**
     * 耗时任务开启时，显示dialog,由子类调用
     */
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


    @Override
    public Context getCtx() {
        return mActivity;
    }

    @Override
    public Activity getAty() {
        return mActivity;
    }
}
