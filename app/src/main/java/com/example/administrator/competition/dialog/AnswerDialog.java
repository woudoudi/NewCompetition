package com.example.administrator.competition.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.GuessRankingAdapter;
import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.example.http_lib.bean.GetBetHistoryListRequestBean;
import com.example.http_lib.response.RankTopBean;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class AnswerDialog extends BaseDialog {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.scanView)
    ScanVideoPlayView scanView;

    private CommonPresenter mPresenter = new CommonPresenter(this);

    private List<RankTopBean> mTopBeans = new ArrayList<>();

    public AnswerDialog(Context context) {
        super(context);
        getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_answer;
    }

    @Override
    protected void initPress() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPresenter.getQuestionTopList("9");

        for (int i = 0; i < 7; i++) {
            mTopBeans.add(new RankTopBean());
        }

        GuessRankingAdapter mRankingAdapter = new GuessRankingAdapter(getCtx(), mTopBeans);
        scanView.initPlayListView(mRankingAdapter, 1, 0, false);
        scanView.setOnRefresh(false);
    }

    @OnClick({R.id.tv_close})
    public void onViewClicked() {
        dismiss();
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(requestCls==GetBetHistoryListRequestBean.class){
            List<RankTopBean> rankTopBeans = JSON.parseArray(responseBean.getData(), RankTopBean.class);
            scanView.refreshVideoList(rankTopBeans);
        }
    }
}
