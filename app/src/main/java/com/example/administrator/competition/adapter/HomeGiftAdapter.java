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
import com.example.http_lib.response.GiftBean;
import com.yidao.module_lib.utils.CommonGlideUtils;

import java.util.List;

import butterknife.BindView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class HomeGiftAdapter extends ScanBaseRecyclerViewAdapter<GiftBean> {

    private OnItemClick mOnItemClick;

    public void setOnGuessItemClick(OnItemClick onGuessItemClick) {
        mOnItemClick = onGuessItemClick;
    }

    public HomeGiftAdapter(Context context, List<GiftBean> list) {
        super(context, list, R.layout.item_gift);
        this.mContext = context;
    }

    @Override
    protected void onBindData(ScanRecyclerViewHolder holder, final GiftBean item, final int position) {
        ImageView ivGift = (ImageView) holder.getView(R.id.iv_gift);
        TextView tvGiftNumber = (TextView) holder.getView(R.id.tv_gift_number);
        final LinearLayout rlGiftContainer = (LinearLayout) holder.getView(R.id.rl_gift_container);

        CommonGlideUtils.showImageView(mContext,item.getCover(),ivGift);
        tvGiftNumber.setText(item.getScorePrice()+"");

        rlGiftContainer.setSelected(item.isSecelet());

        rlGiftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClick != null) {
                    item.setSecelet(!item.isSecelet());

                    if(item.isSecelet()){
                        for (int i=0;i<getItemCount();i++){
                            if(i!=position){
                                getData().get(i).setSecelet(false);
                            }
                        }
                    }

                    mOnItemClick.onItemClick(position,item);

                    notifyDataSetChanged();
                }
            }
        });
    }

    public interface OnItemClick {
        void onItemClick(int position, GiftBean bean);
    }
}
