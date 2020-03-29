package com.example.administrator.competition.fragment.guess;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.BetRecordAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.http_lib.bean.GetIntegralRecordRequestBean;
import com.example.http_lib.response.BetRecordBean;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class BetRecordFragment extends BaseUrlFragment {

    @BindView(R.id.scan_bet)
    ScanVideoPlayView scanBet;

    private BetRecordAdapter mAdapter;

    private List<BetRecordBean> mList = new ArrayList<>();

    private int mCurrentPage = 1;

    public static int Kind_Mode = 100;
    public static int Kind_Not_Mode = 101;

    private int mType;

    @SuppressLint("ValidFragment")
    public BetRecordFragment(int type){
        this.mType = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_guess_one_to_one;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {

        request();

        for(int i=0;i<3;i++){
            mList.add(new BetRecordBean());
        }

        mAdapter = new BetRecordAdapter(getCtx(),mList);
        scanBet.initPlayListView(mAdapter,1,0,false);
        scanBet.setOnRefresh(false);
    }

    private void request(){
        if(mType==Kind_Mode){
            mPresenter.getKindRecord("LIVE", mCurrentPage);
        } else if(mType==Kind_Not_Mode){
            mPresenter.getKindRecord("not LIVE", mCurrentPage);
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetIntegralRecordRequestBean.class) {
                List<BetRecordBean> betRecordBeans = JSON.parseArray(responseBean.getListData(), BetRecordBean.class);
                scanBet.refreshVideoList(betRecordBeans);
            }
        }
    }
}
