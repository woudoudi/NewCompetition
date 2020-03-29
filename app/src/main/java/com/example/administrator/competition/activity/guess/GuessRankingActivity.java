package com.example.administrator.competition.activity.guess;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.home.HomeGuessActivity;
import com.example.administrator.competition.adapter.GuessRankingAdapter;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetBordTopListRequestBean;
import com.example.http_lib.response.RankTopBean;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuessRankingActivity extends BaseUrlView {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_second_head)
    ImageView ivSecondHead;
    @BindView(R.id.tv_second_name)
    TextView tvSecondName;
    @BindView(R.id.tv_second_total_time)
    TextView tvSecondTotalTime;
    @BindView(R.id.tv_second_right_number)
    TextView tvSecondRightNumber;
    @BindView(R.id.iv_first_head)
    ImageView ivFirstHead;
    @BindView(R.id.tv_first_name)
    TextView tvFirstName;
    @BindView(R.id.tv_first_total_time)
    TextView tvFirstTotalTime;
    @BindView(R.id.tv_first_right_number)
    TextView tvFirstRightNumber;
    @BindView(R.id.tv_third_head)
    ImageView tvThirdHead;
    @BindView(R.id.tv_third_name)
    TextView tvThirdName;
    @BindView(R.id.tv_third_total_time)
    TextView tvThirdTotalTime;
    @BindView(R.id.tv_third_right_number)
    TextView tvThirdRightNumber;
    @BindView(R.id.scan_ranking)
    ScanVideoPlayView scanRanking;

    private GuessRankingAdapter mRankingAdapter;

    private List<RankTopBean> mList = new ArrayList();

    @Override
    protected int getView() {
        return R.layout.activity_guess_ranking;
    }

    @Override
    public void init() {
        tvTitle.setText("竞猜排名");
        tvRight.setText("再来一把");
        tvRight.setTextColor(getResources().getColor(R.color.color_BCA081));

        mPresenter.getBordTopList();

        for (int i=0;i<7;i++){
            mList.add(new RankTopBean());
        }

        mRankingAdapter = new GuessRankingAdapter(getCtx(),mList);
        scanRanking.initPlayListView(mRankingAdapter,1,0,false);
        scanRanking.setOnRefresh(false);
    }

    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_right:
                ViewManager.getInstance().finishView(GuessCompetitionActivity.class);
                ViewManager.getInstance().finishView(GuessKingComonSenceActivity.class);
                skipActivityByFinish(HomeGuessActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetBordTopListRequestBean.class){
                List<RankTopBean> rankTopBeans = JSON.parseArray(responseBean.getData(), RankTopBean.class);
                scanRanking.refreshVideoList(rankTopBeans);
            }
        }
    }
}
