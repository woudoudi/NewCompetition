package com.example.administrator.competition.fragment.videoplay;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.home.HomeVideoPlayActivity;
import com.example.administrator.competition.adapter.BetRecordAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetKindRecordRequestBean;
import com.example.http_lib.response.BetRecordBean;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoPlayLandBetRecordFragment extends BaseUrlFragment {


    @BindView(R.id.scan_land_bet)
    ScanVideoPlayView scanLandBet;

    private BetRecordAdapter mRecordAdapter;

    private int mCurrentPage = 1;

    private List<BetRecordBean> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_video_play_land_bet_record;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {

        mPresenter.getKindRecord("not LIVE", mCurrentPage);

        for(int i=0;i<3;i++){
            mList.add(new BetRecordBean());
        }

        mRecordAdapter = new BetRecordAdapter(getContext(),mList);
        scanLandBet.initPlayListView(mRecordAdapter,1,0,false);
        scanLandBet.setOnRefresh(false);

    }

    @OnClick(R.id.tv_land_close)
    public void onViewClicked() {
        if(getAty() instanceof HomeVideoPlayActivity){
            ((HomeVideoPlayActivity)getAty()).showLandFragment(0);
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetKindRecordRequestBean.class){
                List<BetRecordBean> betRecordBeans = JSON.parseArray(responseBean.getListData(), BetRecordBean.class);
                scanLandBet.refreshVideoList(betRecordBeans);
            }
        }
    }
}
