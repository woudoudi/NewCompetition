package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;

import java.util.List;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class HomeMessageAdapter extends ScanBaseRecyclerViewAdapter<Object> {

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onGuessItemClick) {
        mOnItemClick = onGuessItemClick;
    }

    public HomeMessageAdapter(Context context, List<Object> list) {
        super(context, list, R.layout.item_message);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final Object item, final int position) {

        TextView tvMessage = (TextView) holder.getView(R.id.tv_message);


        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick();
                }
            }
        });
    }

    public interface OnItemClick {
        void onItemClick();
    }
}
