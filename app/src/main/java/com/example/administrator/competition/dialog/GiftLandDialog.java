package com.example.administrator.competition.dialog;

import android.content.Context;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.home.HomeVideoPlayActivity;
import com.example.administrator.competition.adapter.HomeLandGiftAdapter;
import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.example.http_lib.bean.GetGiftListRequestBean;
import com.example.http_lib.bean.GetIntegralInfoRequestBean;
import com.example.http_lib.response.GiftBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.base.ievent.video.ICommonEvent;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


public class GiftLandDialog extends BaseDialog {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.tv_send)
    TextView tvSend;

    private CommonPresenter mPresenter;

    private HomeLandGiftAdapter mGiftAdapter;

    private List<GiftBean> mGiftBeans = new ArrayList<>();

    private GiftBean mCurrentGift;

    public GiftLandDialog(Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_land_gift;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {
        super.init();

        mPresenter = new CommonPresenter(this);

        mPresenter.getIntegralInfo();

        mPresenter.getGiftList();

        mGiftAdapter = new HomeLandGiftAdapter(getCtx(), mGiftBeans);
        recyclerView.setAdapter(mGiftAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getCtx(), RecyclerView.HORIZONTAL, false));
        mGiftAdapter.setOnItemClick(new HomeLandGiftAdapter.OnItemClick() {
            @Override
            public void onItemClick(int position, GiftBean giftBean) {
                tvSend.setSelected(giftBean.isSecelet());
                mCurrentGift = giftBean;
            }
        });
    }

    @OnClick(R.id.tv_send)
    public void onViewClicked() {
        if (tvSend.isSelected()) {
            mPresenter.sendGift(mCurrentGift.getId());
        } else {
            ToastUtil.showShortToast("请先选择礼物");
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        if (success) {
            if (requestCls == GetGiftListRequestBean.class) {
                List<GiftBean> giftBeans = JSON.parseArray(responseBean.getListData(), GiftBean.class);
                mGiftAdapter.notifyDataChange(giftBeans);
                mPresenter.getIntegralInfo();
            }
            if (requestCls == GetIntegralInfoRequestBean.class) {
                JSONObject object = JSON.parseObject(responseBean.getData());
                Double score = object.getDouble("score");
                tvIntegral.setText(score + "");
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}
