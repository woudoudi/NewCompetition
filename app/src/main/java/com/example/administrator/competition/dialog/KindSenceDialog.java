package com.example.administrator.competition.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.example.administrator.competition.R;

import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class KindSenceDialog extends BaseDialog {

    private OnFillCheckListener mCheckListener;

    public void setCheckListener(OnFillCheckListener checkListener) {
        mCheckListener = checkListener;
    }

    public KindSenceDialog(Context context) {
        super(context);
        getWindow().setGravity(Gravity.CENTER);
    }

    public KindSenceDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @OnClick({R.id.tv_cancel,R.id.tv_exit})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_exit:
                if(mCheckListener!=null){
                    mCheckListener.onFillCheck();
                }
                break;

        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_kind_sence;
    }

    @Override
    protected void initPress() {

    }


    public interface OnFillCheckListener{
        void onFillCheck();
    }
}
