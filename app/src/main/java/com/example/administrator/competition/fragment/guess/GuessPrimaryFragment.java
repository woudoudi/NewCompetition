package com.example.administrator.competition.fragment.guess;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.base.scanlistlibrary.base.ScanContact;
import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.guess.GuessKingComonSenceActivity;
import com.example.administrator.competition.adapter.GuessCommonAdapter;
import com.example.administrator.competition.base.BaseUrlFragment;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.mvp.presenter.JCLoginPress;
import com.example.http_lib.bean.EnterRoomRequestBean;
import com.example.http_lib.bean.GetRoomKindRequestBean;
import com.example.http_lib.bean.GetRoomListRequestBean;
import com.example.http_lib.bean.GetRoomTypeRequestBean;
import com.example.http_lib.enums.RoomType;
import com.example.http_lib.response.GetRoomListBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GuessPrimaryFragment extends BaseUrlFragment {

    @BindView(R.id.scan_guess)
    ScanVideoPlayView scanGuess;

    GuessCommonAdapter mGuessCommonAdapter;

    private int mGuessGradeValue;

    private int mCurrentPage = 1;

    private GetRoomListBean mCurrentRoomBean;

    private List<GetRoomListBean> mRoomListBeans = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.framelayout_guess_primary;
    }

    @Override
    protected void initPress() {
    }

    @Override
    protected void init() {
        mPresenter.getRoomType();
        mPresenter.getRoomKind();

        request();

        mGuessCommonAdapter = new GuessCommonAdapter(getCtx(), mRoomListBeans);
        scanGuess.setOnRefreshDataListener(mDataListener);
        scanGuess.initPlayListView(mGuessCommonAdapter, 3, 0, false);
        mGuessCommonAdapter.setOnGuessItemClick(mOnGuessItemClick);
    }

    private void request() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mGuessGradeValue = bundle.getInt(CommonConfig.guessGradeKey, 0);
            String kindType = "";
            if (CommonConfig.KindAndNumberMode == CommonConfig.NumberType) {
                kindType = RoomType.BetKindType.NUMBER.key1;
            } else {
                kindType = RoomType.BetKindType.COMMON.key1;
            }
            if (mGuessGradeValue == CommonConfig.primaryValue) {
                mPresenter.getRoomList(kindType, RoomType.RoomGradeType.PRI.key1, mCurrentPage);
            } else if (mGuessGradeValue == CommonConfig.middleValue) {
                mPresenter.getRoomList(kindType, RoomType.RoomGradeType.MIDDLE.key1, mCurrentPage);
            } else if (mGuessGradeValue == CommonConfig.highValue) {
                mPresenter.getRoomList(kindType, RoomType.RoomGradeType.SENIOR.key1, mCurrentPage);
            }
        }
    }

    public void onSync() {
        mCurrentPage = 1;
        mRoomListBeans.clear();
        request();
    }


    ScanContact.OnRefreshDataListener mDataListener = new ScanContact.OnRefreshDataListener() {
        @Override
        public void onRefresh() {
            mCurrentPage = 1;
            mRoomListBeans.clear();
            request();
        }

        @Override
        public void onLoadMore() {
            mCurrentPage++;
            request();
        }
    };

    GuessCommonAdapter.OnGuessItemClick mOnGuessItemClick = new GuessCommonAdapter.OnGuessItemClick() {
        @Override
        public void onItemClick(GetRoomListBean bean) {
            mCurrentRoomBean = bean;
            mPresenter.enterRoom(bean.getId());

//            if (CommonConfig.KindAndNumberMode == CommonConfig.NumberType) {
//                JCLoginPress.jumpGroupChatActivity(43248088, mGuessGradeValue,bean);
//            } else {
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(CommonConfig.guessRoomKey,bean);
//                bundle.putInt(CommonConfig.guessGradeKey,mGuessGradeValue);
//                skipActivity(GuessKingComonSenceActivity.class,bundle);
//            }
        }
    };

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == GetRoomTypeRequestBean.class) {

            }
            if (requestCls == GetRoomKindRequestBean.class) {

            }
            if (requestCls == GetRoomListRequestBean.class) {
                List<GetRoomListBean> getRoomListBeans = JSON.parseArray(responseBean.getListData(), GetRoomListBean.class);
                if (scanGuess.getCurrentLoadingStatus() == 1) {
                    mRoomListBeans.addAll(0, getRoomListBeans);
                    scanGuess.refreshVideoList(mRoomListBeans);
                } else {
                    scanGuess.addMoreData(getRoomListBeans);
                }
            }
            if (requestCls == EnterRoomRequestBean.class) {
                if (CommonConfig.KindAndNumberMode == CommonConfig.NumberType) {
                    JCLoginPress.jumpGroupChatActivity(43248088, mGuessGradeValue,mCurrentRoomBean);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(CommonConfig.guessRoomKey,mCurrentRoomBean);
                    bundle.putInt(CommonConfig.guessGradeKey,mGuessGradeValue);
                    skipActivity(GuessKingComonSenceActivity.class,bundle);
                }
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }
}
