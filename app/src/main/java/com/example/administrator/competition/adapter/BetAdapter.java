package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.http_lib.response.BetBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class BetAdapter extends ScanBaseRecyclerViewAdapter<BetBean> {

    private OnLiveBetItemClick mItemClick;

    public void setItemClick(OnLiveBetItemClick itemClick) {
        mItemClick = itemClick;
    }

    public BetAdapter(Context context, List<BetBean> list) {
        super(context, list, R.layout.item_bet);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final BetBean item, final int position) {
        TextView tvBetType = (TextView) holder.getView(R.id.tv_bet_type);

        LinearLayout llRed1 = (LinearLayout) holder.getView(R.id.ll_red_1);
        TextView tvRed1 = (TextView) holder.getView(R.id.tv_red_1);
        TextView tvRedCent1 = (TextView) holder.getView(R.id.tv_redCent_1);

        LinearLayout llRed2 = (LinearLayout) holder.getView(R.id.ll_red_2);
        TextView tvRed2 = (TextView) holder.getView(R.id.tv_red_2);
        TextView tvRedCent2 = (TextView) holder.getView(R.id.tv_redCent_2);

        LinearLayout llRed3 = (LinearLayout) holder.getView(R.id.ll_red_3);
        TextView tvRed3 = (TextView) holder.getView(R.id.tv_red_3);
        TextView tvRedCent3 = (TextView) holder.getView(R.id.tv_redCent_3);

        LinearLayout llblue1 = (LinearLayout) holder.getView(R.id.ll_blue_1);
        TextView tvblue1 = (TextView) holder.getView(R.id.tv_blue_1);
        TextView tvblueCent1 = (TextView) holder.getView(R.id.tv_blueCent_1);

        LinearLayout llblue2 = (LinearLayout) holder.getView(R.id.ll_blue_2);
        TextView tvblue2 = (TextView) holder.getView(R.id.tv_blue_2);
        TextView tvblueCent2 = (TextView) holder.getView(R.id.tv_blueCent_2);

        LinearLayout llblue3 = (LinearLayout) holder.getView(R.id.ll_blue_3);
        TextView tvblue3 = (TextView) holder.getView(R.id.tv_blue_3);
        TextView tvblueCent3 = (TextView) holder.getView(R.id.tv_blueCent_3);

        RelativeLayout rlRed = (RelativeLayout) holder.getView(R.id.rl_red);
        RelativeLayout rlBlue = (RelativeLayout) holder.getView(R.id.rl_blue);

        List<View> mRedList = new ArrayList();
        List<View> mBlueList = new ArrayList();

        mRedList.add(llRed1);
        mRedList.add(llRed2);
        mRedList.add(llRed3);

        mBlueList.add(llblue1);
        mBlueList.add(llblue2);
        mBlueList.add(llblue3);

        llRed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mBlueList);
                betItemChoose(mRedList, 0);
                if(mItemClick!=null){
                    mItemClick.red1();
                }
            }
        });

        llRed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mBlueList);
                betItemChoose(mRedList, 1);
                if(mItemClick!=null){
                    mItemClick.red2();
                }
            }
        });
        llRed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mBlueList);
                betItemChoose(mRedList, 2);
                if(mItemClick!=null){
                    mItemClick.red3();
                }
            }
        });
        llblue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mRedList);
                betItemChoose(mBlueList, 0);
                if(mItemClick!=null){
                    mItemClick.blue1();
                }
            }
        });

        llblue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mRedList);
                betItemChoose(mBlueList, 1);
                if(mItemClick!=null){
                    mItemClick.blue2();
                }
            }
        });
        llblue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearListSelect(mRedList);
                betItemChoose(mBlueList, 2);
                if(mItemClick!=null){
                    mItemClick.blue3();
                }
            }
        });
    }

    private void betItemChoose(List<View> list, int index) {
        for (int i = 0; i < list.size(); i++) {
            if(list.get(i).isSelected()){
                list.get(i).setSelected(false);
            } else {
                list.get(i).setSelected(i == index);
            }
        }
    }

    private void clearListSelect(List<View> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
    }

    public interface OnLiveBetItemClick{
        void red1();
        void red2();
        void red3();

        void blue1();
        void blue2();
        void blue3();
    }
}
