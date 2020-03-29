package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.response.GetRoomListBean;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class GuessCommonAdapter extends ScanBaseRecyclerViewAdapter<GetRoomListBean> {


    private OnGuessItemClick mOnGuessItemClick;

    public void setOnGuessItemClick(OnGuessItemClick onGuessItemClick) {
        mOnGuessItemClick = onGuessItemClick;
    }

    public GuessCommonAdapter(Context context, List<GetRoomListBean> list) {
        super(context, list, R.layout.item_guess);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final GetRoomListBean item, final int position) {
        ImageView ivGuessState = (ImageView) holder.getView(R.id.iv_guess_state);
        TextView tvGuessState = (TextView) holder.getView(R.id.tv_guess_state);
        TextView tvPersonNumber = (TextView) holder.getView(R.id.tv_person_number);
        RelativeLayout rlGuessContainer = (RelativeLayout) holder.getView(R.id.rl_guess_container);

        ivGuessState.setSelected(item.getStatus()!=0);
        tvGuessState.setText(item.getStatus()!=0?"已开始":"未开始");
        int total = item.getPersonKe()+item.getPersonPang()+item.getPersonZhu();
        tvPersonNumber.setText(total+"");

        rlGuessContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnGuessItemClick!=null){
                    mOnGuessItemClick.onItemClick(item);
                }
            }
        });
    }

    public interface OnGuessItemClick{
        void onItemClick(GetRoomListBean roomListBean);
    }
}
