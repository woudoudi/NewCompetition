package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;

import java.util.List;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class NumberAdapter extends ScanBaseRecyclerViewAdapter<String> {

    private OnItemClick mOnItemClick;

    public void setOnItemClick(OnItemClick onGuessItemClick) {
        mOnItemClick = onGuessItemClick;
    }

    public NumberAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_spinner);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final String item, final int position) {

        TextView tvMessage = (TextView) holder.getView(R.id.tv_number);
        tvMessage.setText(item);


        tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    mOnItemClick.onItemClick(item);
                }
            }
        });
    }

    public interface OnItemClick {
        void onItemClick(String item);
    }
}
