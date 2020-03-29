package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.http_lib.response.RankTopBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class GuessRankingAdapter extends ScanBaseRecyclerViewAdapter<RankTopBean> {


    public GuessRankingAdapter(Context context, List<RankTopBean> list) {
        super(context, list, R.layout.item_guess_rank);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final RankTopBean item, final int position) {
        TextView tvNo = (TextView) holder.getView(R.id.tv_no);
        TextView tvName = (TextView) holder.getView(R.id.tv_name);
        TextView tvTotalTime = (TextView) holder.getView(R.id.tv_total_time);
        TextView tvNumber = (TextView) holder.getView(R.id.tv_right_number);
        LinearLayout llContainer = (LinearLayout) holder.getView(R.id.ll_container);

        tvNo.setText(position + 4 + "");

        llContainer.setBackgroundResource(position % 2 == 0 ? R.color.color_38364C : R.color.color_43455C);
    }
}
