package com.example.administrator.competition.fragment.guess;

import android.annotation.SuppressLint;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.BetAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.http_lib.bean.GetIntegralRecordRequestBean;
import com.example.http_lib.response.BetBean;
import com.yidao.module_lib.base.http.ResponseBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class BetFragment extends BaseUrlFragment {

    @BindView(R.id.scan_bet)
    ScanVideoPlayView scanBet;

    private BetAdapter mAdapter;

    private List<BetBean> mList = new ArrayList<>();

    private int mCurrentPage = 1;

    public static int Primary_Mode = 100;
    public static int Middle_Mode = 101;
    public static int High_Mode = 102;

    private int mType;

    @SuppressLint("ValidFragment")
    public BetFragment(int type){
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
            mList.add(new BetBean());
        }

        mAdapter = new BetAdapter(getCtx(),mList);
        scanBet.initPlayListView(mAdapter,1,0,false);
        scanBet.setOnRefresh(false);
    }

    private void request(){
        if(mType==Primary_Mode){

        } else if(mType==Middle_Mode){

        } else if(mType==High_Mode){

        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetIntegralRecordRequestBean.class) {
                List<BetBean> recordBeans = JSON.parseArray(responseBean.getData(), BetBean.class);
                scanBet.refreshVideoList(recordBeans);
            }
        }
    }
}
