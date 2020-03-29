package com.example.administrator.competition.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class IntegralAdapter extends ScanBaseRecyclerViewAdapter<Object> {


    @BindView(R.id.tv_view1)
    TextView tvView1;
    @BindView(R.id.tv_recharge_time)
    TextView tvRechargeTime;
    @BindView(R.id.tv_recharge_total)
    TextView tvRechargeTotal;

    public IntegralAdapter(Context context, List<Object> list) {
        super(context, list, R.layout.item_intergral);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final Object item, final int position) {
        TextView tvView1 = (TextView) holder.getView(R.id.tv_view1);
        TextView tvRechargeTime = (TextView) holder.getView(R.id.tv_recharge_time);
        TextView tvRechargeTotal = (TextView) holder.getView(R.id.tv_recharge_total);

    }
}
