package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.http_lib.response.InviteBean;
import com.yidao.module_lib.utils.DensityUtil;
import com.yidao.module_lib.utils.ImmerUtils;

import java.util.List;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class HomeFreeConfrontationAdapter extends ScanBaseRecyclerViewAdapter<InviteBean> {

    private OnFreeConfrontationClick mConfrontationClick;

    public void setConfronttationClick(OnFreeConfrontationClick confrontationClick) {
        mConfrontationClick = confrontationClick;
    }

    private int mScreenWidth;


    public HomeFreeConfrontationAdapter(Context context, List<InviteBean> list) {
        super(context, list, R.layout.item_home_free_confrontation);
        this.mContext = context;
        this.mScreenWidth = ImmerUtils.getScreenWidth(context);
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final InviteBean item, final int position) {
        TextView tvName = (TextView) holder.getView(R.id.tv_name);
        TextView tvTime = (TextView) holder.getView(R.id.tv_time);
        TextView tvBetDetail = (TextView) holder.getView(R.id.tv_bet_detail);
        TextView tvAccept = (TextView) holder.getView(R.id.tv_accept);
        View viewGap =  holder.getView(R.id.view_gap);
        TextView tvRefuse = (TextView) holder.getView(R.id.tv_refuce);
        ImageView ivBetState = (ImageView) holder.getView(R.id.iv_bet_state);
        LinearLayout llFreeContainer = (LinearLayout) holder.getView(R.id.ll_free_container);

        ViewGroup.LayoutParams params = llFreeContainer.getLayoutParams();
        params.width = (mScreenWidth - DensityUtil.dip2px(mContext,15*2))/2;
        llFreeContainer.setLayoutParams(params);

        tvName.setText(item.getNickInviter());
        tvTime.setText(item.getCreateAt());
        tvBetDetail.setText(String.format("甲方:%s     乙方:%s",item.getFscoreA(),item.getFscoreB()));
        // 0 收到
        if(item.getStatus()==0){

        }


        tvAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConfrontationClick!=null){
                    mConfrontationClick.onAcceptClick(position,item);
                }
            }
        });

        tvRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConfrontationClick!=null){
                    mConfrontationClick.onRefuseClick(position,item);
                }
            }
        });

        llFreeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mConfrontationClick!=null){
                    mConfrontationClick.onItemClick(position,item);
                }
            }
        });
    }

    public interface OnFreeConfrontationClick{
        void onAcceptClick(int position,InviteBean inviteBean);
        void onRefuseClick(int position,InviteBean inviteBean);
        void onItemClick(int position,InviteBean inviteBean);
    }
}
