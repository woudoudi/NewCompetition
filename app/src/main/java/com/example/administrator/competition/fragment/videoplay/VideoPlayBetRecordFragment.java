package com.example.administrator.competition.fragment.videoplay;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.BetRecordAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.http_lib.bean.GetKindRecordRequestBean;
import com.example.http_lib.response.BetRecordBean;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoPlayBetRecordFragment extends BaseUrlFragment {

    @BindView(R.id.scan_bet)
    ScanVideoPlayView scanBet;
    private BetRecordAdapter mRecordAdapter;

    private List<BetRecordBean> mList = new ArrayList<>();

    private int mCurrentPage = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_video_play_bet_record;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {

        mPresenter.getKindRecord("not LIVE", mCurrentPage);

        for (int i = 0; i < 3; i++) {
            mList.add(new BetRecordBean());
        }

        mRecordAdapter = new BetRecordAdapter(getCtx(), mList);
        scanBet.initPlayListView(mRecordAdapter,1,0,false);
        scanBet.setOnRefresh(false);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            mPresenter.getKindRecord("not LIVE", mCurrentPage);
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetKindRecordRequestBean.class){
                List<BetRecordBean> betRecordBeans = JSON.parseArray(responseBean.getListData(), BetRecordBean.class);
                scanBet.refreshVideoList(betRecordBeans);
            }
        }
    }
}
