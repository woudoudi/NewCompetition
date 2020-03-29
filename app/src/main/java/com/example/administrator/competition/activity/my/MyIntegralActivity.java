package com.example.administrator.competition.activity.my;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.IntegralAdapter;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetIntegralInfoRequestBean;
import com.example.http_lib.bean.GetIntegralRecordRequestBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyIntegralActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.scan_bet)
    ScanVideoPlayView scanBet;

    private IntegralAdapter mIntegralAdapter;
    private List<Object> mList = new ArrayList<>();

    @Override
    protected int getView() {
        return R.layout.activity_my_integral;
    }


    @Override
    public void init() {
        tvTitle.setText("积分");
        mPresenter.getIntegralInfo();
        mPresenter.getIntegralRecord(1);

        for (int i = 0; i < 3; i++) {
            mList.add(i);
        }

        mIntegralAdapter = new IntegralAdapter(getCtx(), mList);
        scanBet.initPlayListView(mIntegralAdapter,1,0,false);
        scanBet.setOnRefresh(false);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        ViewManager.getInstance().finishView();
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetIntegralInfoRequestBean.class) {
                JSONObject object = JSON.parseObject(responseBean.getData());
                Double score = object.getDouble("score");
                tvIntegral.setText(score + "");
            }
            if (requestCls == GetIntegralRecordRequestBean.class) {

            }
        }
    }
}
