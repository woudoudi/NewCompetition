package com.example.administrator.competition.activity.home;

import android.os.Bundle;
import android.widget.TextView;

import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.HomeRecordAdapter;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.response.LiveAndRecordBean;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeRecordActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.scan_record)
    ScanVideoPlayView scanRecord;

    HomeRecordAdapter mRecordAdapter;

    private int mRecordAndLiveValue;

    private List<LiveAndRecordBean> mBeans = new ArrayList<>();

    @Override
    protected int getView() {
        return R.layout.activity_home_record;
    }

    @Override
    public void init() {
        mRecordAndLiveValue = getIntent().getIntExtra(CommonConfig.recordAndLiveKey,0);

        if(mRecordAndLiveValue == CommonConfig.recordValue){
            tvTitle.setText("录播回放");
        } else if(mRecordAndLiveValue == CommonConfig.LiveValue){
            tvTitle.setText("王者专场");
        }

        for (int i=0;i<6;i++){
            mBeans.add(new LiveAndRecordBean());
        }

        mRecordAdapter = new HomeRecordAdapter(getCtx(),mBeans,mRecordAndLiveValue);
        mRecordAdapter.setOnItemClick(mOnItemClick);
        scanRecord.initPlayListView(mRecordAdapter,2,0,false);
    }

    HomeRecordAdapter.OnItemClick mOnItemClick = new HomeRecordAdapter.OnItemClick() {
        @Override
        public void onItemClick(LiveAndRecordBean bean) {
            if(mRecordAndLiveValue == CommonConfig.recordValue){

            } else if(mRecordAndLiveValue == CommonConfig.LiveValue){

            }

            Bundle bundle = new Bundle();
            bundle.putSerializable(CommonConfig.recordAndLiveBean,bean);
            bundle.putInt(CommonConfig.recordAndLiveKey,mRecordAndLiveValue);
            skipActivity(HomeVideoPlayActivity.class,bundle);
        }
    };

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        ViewManager.getInstance().finishView();
    }
}
