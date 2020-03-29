package com.example.administrator.competition.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;

import com.example.administrator.competition.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class RuleDialog extends BaseDialog {


    public RuleDialog(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setContentView(R.layout.dialog_rule);
        ButterKnife.bind(this);
        getWindow().setWindowAnimations(R.style.dialog_style);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_rule;
    }

    @Override
    protected void initPress() {

    }

    @OnClick({R.id.tv_close})
    public void onViewClicked() {
        dismiss();
    }
}
