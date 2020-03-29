package com.example.administrator.competition.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.competition.R;
import com.example.administrator.competition.entity.CommonConfig;
import com.yidao.module_lib.entity.EventBusBean;
import com.yidao.module_lib.manager.ViewManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class QuickResponsePopWindow extends BaseDialog {

    private int type;

    public QuickResponsePopWindow(Context context,int type) {
        super(context);
        this.type = type;
    }

    protected void init() {
        setContentView(R.layout.dialog_quick_response);
        ButterKnife.bind(this);
        getWindow().setWindowAnimations(R.style.dialog_style);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_quick_response;
    }

    @Override
    protected void initPress() {

    }

    @OnClick({R.id.tv_ten, R.id.tv_1v1, R.id.tv_cancel})
    public void onViewClicked(View view) {
        dismiss();
        switch (view.getId()) {
            case R.id.tv_ten:
                CommonConfig.TenAndOneTableMode = CommonConfig.TenPersonTable;
                if(type==CommonConfig.NumberType){
                    ViewManager.getInstance().finishView();
                    EventBus.getDefault().post(new EventBusBean(EventBusBean.JumpToBetFragment).setTableType(CommonConfig.TenPersonTable));
                } else if(type==CommonConfig.KindType){
                    ViewManager.getInstance().finishView();
                    EventBus.getDefault().post(new EventBusBean(EventBusBean.JumpToBetFragment).setTableType(CommonConfig.TenPersonTable));

//                    Bundle bundle = new Bundle();
//                    bundle.putInt(CommonConfig.KindKey,CommonConfig.TenPersonTable);
//                    skipActivity(GuessKingComonSenceActivity.class,bundle);
                }
                break;
            case R.id.tv_1v1:
                CommonConfig.TenAndOneTableMode = CommonConfig.OneVOneTable;
                if(type==CommonConfig.NumberType){
                    ViewManager.getInstance().finishView();
                    EventBus.getDefault().post(new EventBusBean(EventBusBean.JumpToBetFragment).setTableType(CommonConfig.OneVOneTable));
                } else if(type==CommonConfig.KindType){
                    ViewManager.getInstance().finishView();
                    EventBus.getDefault().post(new EventBusBean(EventBusBean.JumpToBetFragment).setTableType(CommonConfig.OneVOneTable));

//                    Bundle bundle = new Bundle();
//                    bundle.putInt(CommonConfig.KindKey,CommonConfig.OneVOneTable);
//                    skipActivity(GuessKingComonSenceActivity.class);
                }
                break;
            case R.id.tv_cancel:
                break;
        }
    }
}
