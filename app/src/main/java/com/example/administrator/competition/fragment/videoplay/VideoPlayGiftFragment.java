package com.example.administrator.competition.fragment.videoplay;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.home.HomeVideoPlayActivity;
import com.example.administrator.competition.adapter.HomeGiftAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.http_lib.bean.GetGiftListRequestBean;
import com.example.http_lib.response.GiftBean;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class VideoPlayGiftFragment extends BaseUrlFragment {

    @BindView(R.id.scan_gift)
    ScanVideoPlayView scanGift;

    private HomeGiftAdapter mGiftAdapter;

    private List<GiftBean> mGiftBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_video_play_gift;
    }

    @Override
    protected void initPress() {

    }

    @Override
    protected void init() {

        mPresenter.getGiftListType();

        mPresenter.getGiftList();

        mGiftAdapter = new HomeGiftAdapter(getCtx(),mGiftBeans);
        mGiftAdapter.setOnGuessItemClick(mOnItemClick);
        scanGift.initPlayListView(mGiftAdapter,4,0,false);
        scanGift.setOnRefresh(false);
    }

    HomeGiftAdapter.OnItemClick mOnItemClick = new HomeGiftAdapter.OnItemClick() {
        @Override
        public void onItemClick(int position,GiftBean bean) {
            ((HomeVideoPlayActivity)getAty()).isChooseGift(bean);
        }
    };

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(mGiftBeans.size()==0){
            mPresenter.getGiftList();
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==GetGiftListRequestBean.class){
                List<GiftBean> giftBeans = JSON.parseArray(responseBean.getListData(), GiftBean.class);
                scanGift.refreshVideoList(giftBeans);
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}
