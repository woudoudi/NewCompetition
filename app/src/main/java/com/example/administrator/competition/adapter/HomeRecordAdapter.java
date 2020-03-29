package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.response.LiveAndRecordBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class HomeRecordAdapter extends ScanBaseRecyclerViewAdapter<LiveAndRecordBean> {

    private int mType;

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        mOnItemClick = onItemClick;
    }

    public HomeRecordAdapter(Context context, List<LiveAndRecordBean> list,int type) {
        super(context, list, R.layout.item_home_record);
        this.mContext = context;
        this.mType = type;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final LiveAndRecordBean item, final int position) {
        ImageView ivCover = (ImageView) holder.getView(R.id.iv_cover);
        TextView tvName = (TextView) holder.getView(R.id.tv_name);
        TextView tvRecordTitle = (TextView) holder.getView(R.id.tv_record_title);
        TextView tvRecordTime = (TextView) holder.getView(R.id.tv_record_time);

        TextView tvpopular = (TextView) holder.getView(R.id.tv_popular);

        if(mType == CommonConfig.recordValue){
            tvpopular.setVisibility(View.GONE);
        } else if(mType == CommonConfig.LiveValue){
            tvRecordTime.setVisibility(View.GONE);
        }

        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClick!=null){
                    mOnItemClick.onItemClick(item);
                }
            }
        });
    }

    public interface OnItemClick{
        void onItemClick(LiveAndRecordBean bean);
    }
}
