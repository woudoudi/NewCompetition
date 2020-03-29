package com.example.administrator.competition.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.HomeMessageAdapter;
import com.yidao.module_lib.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MorePopWindow {

    private Context mContext;
    private View mView;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    private OnItemClick mOnItemClick;

    public MorePopWindow(Context context, View view) {
        this.mContext=context;
        this.mView=view;
    }

    public void showPopWindow(){

        View contentView = LayoutInflater.from(mContext).inflate(R.layout.popwindow_more, null);
        final PopupWindow popWnd = new PopupWindow(mContext);
        popWnd.setContentView(contentView);
        popWnd.setWidth(DensityUtil.dip2px(mContext,80));
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        TextView tvBetRule = contentView.findViewById(R.id.tv_bet_rule);
        TextView tvBetRecord = contentView.findViewById(R.id.tv_bet_record);

        tvBetRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                if(mOnItemClick!=null){
                    mOnItemClick.onBetRule();
                }
            }
        });
        tvBetRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popWnd.dismiss();
                if(mOnItemClick!=null){
                    mOnItemClick.onBetRecord();
                }
            }
        });

        popWnd.setOutsideTouchable(true);
        popWnd.showAsDropDown(mView);
    }


    public interface OnItemClick{
        void onBetRule();
        void onBetRecord();
    }
}
