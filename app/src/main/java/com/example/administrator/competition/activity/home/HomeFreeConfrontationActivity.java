package com.example.administrator.competition.activity.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.base.ScanContact;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.HomeFreeConfrontationAdapter;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.bean.GetKingFightListRequestBean;
import com.example.http_lib.bean.ReceiveKingFightRequestBean;
import com.example.http_lib.response.InviteBean;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFreeConfrontationActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.scan_free_confrontation)
    ScanVideoPlayView scanFreeConfrontation;

    private int mCurrentPage = 1;
    private List<InviteBean> mList = new ArrayList<>();

    HomeFreeConfrontationAdapter mConfrontationAdapter;

    @Override
    protected int getView() {
        return R.layout.activity_home_free_confrontation;
    }

    @Override
    public void init() {
        tvTitle.setText("自由对抗");
        ivRight.setImageResource(R.mipmap.add_free);

        mPresenter.getLevelList();

        mPresenter.getKingFightList(mCurrentPage);

        mConfrontationAdapter = new HomeFreeConfrontationAdapter(getCtx(),mList);
        mConfrontationAdapter.setConfronttationClick(mConfrontationClick);

        scanFreeConfrontation.setOnRefreshDataListener(mDataListener);
        scanFreeConfrontation.initPlayListView(mConfrontationAdapter,2,0,false);
    }

    ScanContact.OnRefreshDataListener mDataListener = new ScanContact.OnRefreshDataListener() {
        @Override
        public void onRefresh() {
            mCurrentPage=1;
            mList.clear();
            mPresenter.getKingFightList(mCurrentPage);
        }
        @Override
        public void onLoadMore() {
            mCurrentPage++;
            mPresenter.getKingFightList(mCurrentPage);
        }
    };


    HomeFreeConfrontationAdapter.OnFreeConfrontationClick mConfrontationClick = new HomeFreeConfrontationAdapter.OnFreeConfrontationClick() {
        @Override
        public void onAcceptClick(int position,InviteBean bean) {
            ReceiveKingFightRequestBean requestBean = new ReceiveKingFightRequestBean();
            requestBean.receiver = bean.getReceiver();
            mPresenter.acceptKingFight(bean.getId(),requestBean);
        }

        @Override
        public void onRefuseClick(int position,InviteBean bean) {
        }

        @Override
        public void onItemClick(int position,InviteBean bean) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(CommonConfig.guessInviteKey,bean);
            skipActivity(HomeConfrontationSureActivity.class,bundle);
        }
    };

    @OnClick({R.id.iv_back, R.id.iv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.iv_right:
                skipActivity(HomeAddConfrontationActivity.class);
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetKingFightListRequestBean.class){
                List<InviteBean> inviteBeans = JSON.parseArray(responseBean.getListData(), InviteBean.class);
                if(scanFreeConfrontation.getCurrentLoadingStatus()==1){
                    mList.addAll(0,inviteBeans);
                    scanFreeConfrontation.refreshVideoList(mList);
                } else {
                    scanFreeConfrontation.addMoreData(inviteBeans);
                }
            }
            if(requestCls==ReceiveKingFightRequestBean.class){
                // TODO: 2020/3/21 接受对抗后跳转到对抗确认页面进行放分 
                skipActivity(HomeConfrontationSureActivity.class);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}
