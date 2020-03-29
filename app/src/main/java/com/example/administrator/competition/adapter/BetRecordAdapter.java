package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.http_lib.response.BetRecordBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class BetRecordAdapter extends ScanBaseRecyclerViewAdapter<BetRecordBean> {


    public BetRecordAdapter(Context context, List<BetRecordBean> list) {
        super(context, list, R.layout.item_bet_record);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final BetRecordBean item, final int position) {
        TextView tvBetType = (TextView) holder.getView(R.id.tv_bet_type);
        TextView tvBetTypeDetail = (TextView) holder.getView(R.id.tv_bet_type_detail);
        TextView tvBetSingleCent = (TextView) holder.getView(R.id.tv_bet_single_cent);
        TextView tvBetCent = (TextView) holder.getView(R.id.tv_bet_cent);
        TextView tvBetTotal = (TextView) holder.getView(R.id.tv_bet_total);
        TextView tvBetState = (TextView) holder.getView(R.id.tv_bet_state);

        TextView tvBetTime = (TextView) holder.getView(R.id.tv_bet_time);
        TextView tvBetNumber = (TextView) holder.getView(R.id.tv_bet_number);

        LinearLayout container = (LinearLayout) holder.getView(R.id.ll_container);

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_bet_detail,container,false);

        container.addView(view,1);



    }
}
