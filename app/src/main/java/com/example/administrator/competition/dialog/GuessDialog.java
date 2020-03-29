package com.example.administrator.competition.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import com.example.administrator.competition.R;

import butterknife.OnClick;

@SuppressLint("ValidFragment")
public class GuessDialog extends BaseDialog {

    private OnItemClick mCheckListener;

    public void setCheckListener(OnItemClick checkListener) {
        mCheckListener = checkListener;
    }

    public GuessDialog(Context context) {
        super(context);
        getWindow().setGravity(Gravity.CENTER);
    }

    public GuessDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @OnClick({R.id.tv_lose,R.id.tv_win})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_lose:
                if(mCheckListener!=null){
                    mCheckListener.lose();
                }
                break;
            case R.id.tv_win:
                if(mCheckListener!=null){
                    mCheckListener.win();
                }
                break;
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_guess;
    }

    @Override
    protected void initPress() {

    }


    public interface OnItemClick{
        void win();
        void lose();
    }
}
