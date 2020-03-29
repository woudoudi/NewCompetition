package com.example.administrator.competition.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.base.scanlistlibrary.base.ScanBaseRecyclerViewAdapter;
import com.base.scanlistlibrary.base.ScanRecyclerViewHolder;
import com.example.administrator.competition.R;
import com.example.http_lib.response.GiftBean;
import com.yidao.module_lib.utils.CommonGlideUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/9/5
 */
public class HomeLandGiftAdapter extends RecyclerView.Adapter<HomeLandGiftAdapter.ViewHolder> {

    private OnItemClick mOnItemClick;

    private Context mContext;
    private List<GiftBean> mList;

    public void setOnItemClick(OnItemClick onGuessItemClick) {
        mOnItemClick = onGuessItemClick;
    }

    public HomeLandGiftAdapter(Context context, List<GiftBean> list) {
        this.mContext = context;
        this.mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_land_gift, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        GiftBean giftBean = mList.get(position);

        CommonGlideUtils.showImageView(mContext,giftBean.getCover(),holder.ivGift);
        holder.tvGiftNumber.setText(giftBean.getScorePrice()+"");

        holder.rlGiftContainer.setSelected(giftBean.isSecelet());

        holder.rlGiftContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rlGiftContainer.setSelected(!holder.rlGiftContainer.isSelected());
                if (mOnItemClick != null) {

                    giftBean.setSecelet(!giftBean.isSecelet());
                    if (giftBean.isSecelet()) {
                        for (int i = 0; i < getItemCount(); i++) {
                            if (i != position) {
                                mList.get(i).setSecelet(false);
                            }
                        }
                    }
                    mOnItemClick.onItemClick(position, giftBean);
                    notifyDataSetChanged();
                }
            }
        });

    }

    public void notifyDataChange(List<GiftBean> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGift;
        private TextView tvGiftNumber;
        private LinearLayout rlGiftContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            ivGift = itemView.findViewById(R.id.iv_gift);
            tvGiftNumber = itemView.findViewById(R.id.tv_gift_number);
            rlGiftContainer = itemView.findViewById(R.id.rl_gift_container);

        }
    }


    public interface OnItemClick {
        void onItemClick(int position, GiftBean bean);
    }
}
