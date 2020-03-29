package com.example.administrator.competition.activity.home;


import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.dialog.GiftLandDialog;
import com.example.administrator.competition.dialog.MessagePopWindow;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.fragment.guess.BetRecordFragment;
import com.example.administrator.competition.fragment.videoplay.VideoPlayBetFragment;
import com.example.administrator.competition.fragment.videoplay.VideoPlayGiftFragment;
import com.example.administrator.competition.fragment.videoplay.VideoPlayLandBetFragment;
import com.example.administrator.competition.fragment.videoplay.VideoPlayLandBetRecordFragment;
import com.example.http_lib.bean.GetIntegralInfoRequestBean;
import com.example.http_lib.bean.SendGiftRequestBean;
import com.example.http_lib.response.GiftBean;
import com.example.http_lib.response.LiveAndRecordBean;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.txcvodplayer.TXCVodVideoView;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.DensityUtil;
import com.yidao.module_lib.utils.ToastUtil;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;

public class HomeVideoPlayActivity extends BaseUrlView {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.rl_play_layout)
    RelativeLayout rlPlayLayout;
    @BindView(R.id.tv_bet)
    TextView tvBet;
    @BindView(R.id.tv_bet_record)
    TextView tvBetRecord;
    @BindView(R.id.frameLayout_bet)
    FrameLayout frameLayoutBet;
    @BindView(R.id.iv_bet)
    ImageView ivBet;
    @BindView(R.id.iv_gift)
    ImageView ivGift;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.rl_send_bottom)
    RelativeLayout rlSendBottom;
    @BindView(R.id.ll_port_layout)
    LinearLayout llPortLayout;

    @BindView(R.id.frameLayout_play)
    RelativeLayout frameLayoutPlay;
    @BindView(R.id.iv_land_bet)
    ImageView ivLandBet;
    @BindView(R.id.iv_land_gift)
    ImageView ivLandGift;
    @BindView(R.id.iv_land_message)
    ImageView ivLandMessage;

    @BindView(R.id.playerView)
    TXCloudVideoView mPlayerView;

    @BindView(R.id.rl_land_bet_layout)
    RelativeLayout rlLandBetLayout;
    @BindView(R.id.rl_control_top)
    RelativeLayout rlControlTop;
    @BindView(R.id.rl_control_bottom)
    RelativeLayout rlControlBottom;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.ll_integral_container)
    LinearLayout llIntegralContainer;
    @BindView(R.id.tv_send)
    TextView tvSend;

    private MessagePopWindow mMessagePopWindow;

    private TXLivePlayer mMLivePlayer;

    private GiftBean mCurrentGiftBean;

    private int mRecordAndLiveValue;


    private VideoPlayBetFragment mPlayBetFragment = new VideoPlayBetFragment();
    private BetRecordFragment mBetRecordFragment = new BetRecordFragment(BetRecordFragment.Kind_Mode);
    private VideoPlayGiftFragment mGiftFragment = new VideoPlayGiftFragment();
    BaseFragment[] mBaseFragments = new BaseFragment[]{mPlayBetFragment, mBetRecordFragment, mGiftFragment};


    private VideoPlayLandBetFragment mLandBetFragment = new VideoPlayLandBetFragment();
    private VideoPlayLandBetRecordFragment mLandBetRecordFragment = new VideoPlayLandBetRecordFragment();
    BaseFragment[] mBaseLandFragments = new BaseFragment[]{mLandBetFragment, mLandBetRecordFragment};


    @Override
    protected int getView() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_home_video_play;
    }

    @Override
    public void init() {
        showFragment(0);

        mRecordAndLiveValue = getIntent().getIntExtra(CommonConfig.recordAndLiveKey, 0);
        LiveAndRecordBean bean = (LiveAndRecordBean) getIntent().getSerializableExtra(CommonConfig.recordAndLiveBean);

        //创建 player 对象
        mMLivePlayer = new TXLivePlayer(this);
        TXLivePlayConfig playConfig = new TXLivePlayConfig();
        playConfig.setEnableMessage(true);
        mMLivePlayer.setConfig(playConfig);

        //关键 player 对象与界面 view
        mMLivePlayer.setPlayerView(mPlayerView);
        String flvUrl = "rtmp://videoplay.jchm99.com/live/60521253710901565";
        int playMode = mRecordAndLiveValue == CommonConfig.LiveValue ? TXLivePlayer.PLAY_TYPE_LIVE_FLV : TXLivePlayer.PLAY_TYPE_VOD_FLV;
        mMLivePlayer.startPlay(flvUrl, playMode); //推荐 FLV
        // 设置填充模式
        mMLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);

        hideTopControl();
        rlControlBottom.setVisibility(View.GONE);
    }


    @OnClick({R.id.iv_back, R.id.playerView, R.id.tv_bet, R.id.tv_bet_record, R.id.iv_bet, R.id.iv_gift, R.id.iv_message, R.id.iv_land_bet, R.id.iv_land_gift, R.id.iv_land_message, R.id.iv_share, R.id.tv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    ViewManager.getInstance().finishView();
                } else {
                    //横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.playerView:
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    rlControlBottom.setVisibility(View.GONE);
                    if (rlControlTop.getVisibility() == View.VISIBLE) {
                        rlControlTop.setVisibility(View.GONE);
                    } else {
                        hideTopControl();
                    }
                } else {
                    //横屏
                    if (rlLandBetLayout.getVisibility() == View.VISIBLE) {
                        return;
                    }
                    if (rlControlBottom.getVisibility() == View.VISIBLE) {
                        rlControlBottom.setVisibility(View.GONE);
                        rlControlTop.setVisibility(View.GONE);
                    } else {
                        hideTopControl();
                        hideBottomControl();
                    }
                }
                break;
            case R.id.tv_bet:
                showFragment(0);
                if (rlBottom.getVisibility() == View.GONE) {
                    rlBottom.setVisibility(View.VISIBLE);
                }
                rlSendBottom.setVisibility(View.GONE);
                break;
            case R.id.tv_bet_record:
                showFragment(1);
                if (rlBottom.getVisibility() == View.GONE) {
                    rlBottom.setVisibility(View.VISIBLE);
                }
                rlSendBottom.setVisibility(View.GONE);
                break;
            case R.id.iv_bet:
                showFragment(0);
                if (rlBottom.getVisibility() == View.GONE) {
                    rlBottom.setVisibility(View.VISIBLE);
                }
                rlSendBottom.setVisibility(View.GONE);
                break;
            case R.id.iv_gift:
                showFragment(2);
                if (rlBottom.getVisibility() == View.VISIBLE) {
                    rlBottom.setVisibility(View.GONE);
                }
                rlSendBottom.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_message:
                if (mMessagePopWindow == null) {
                    mMessagePopWindow = new MessagePopWindow();
                }
                mMessagePopWindow.showMessagePopWindow(getCtx(), rlBottom);
                break;
            case R.id.iv_share:
                if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    //竖屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    //横屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
                break;
            case R.id.iv_land_bet:
                if (rlLandBetLayout.getVisibility() == View.VISIBLE) {
                    rlLandBetLayout.setVisibility(View.GONE);
                } else {
                    rlLandBetLayout.setVisibility(View.VISIBLE);
                    showLandFragment(0);
                }
                break;
            case R.id.iv_land_gift:
                if (rlLandBetLayout.getVisibility() == View.VISIBLE) {
                    return;
                }
                GiftLandDialog giftLandDialog = new GiftLandDialog(getCtx());
                giftLandDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                            rlControlBottom.setVisibility(View.VISIBLE);
                        }
                    }
                });
                giftLandDialog.show();
                rlControlBottom.setVisibility(View.GONE);
                break;
            case R.id.iv_land_message:
                if (rlLandBetLayout.getVisibility() == View.VISIBLE) {
                    return;
                }
                if (mMessagePopWindow == null) {
                    mMessagePopWindow = new MessagePopWindow();
                }
                mMessagePopWindow.showMessageLandPopWindow(getCtx(), ivLandGift);
                break;
            case R.id.tv_send:
                if (tvSend.isSelected()) {
                    mPresenter.sendGift(mCurrentGiftBean.getId());
                } else {
                    ToastUtil.showShortToast("请先选择礼物");
                }
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == SendGiftRequestBean.class) {
                mPresenter.getIntegralInfo();
            }
            if (requestCls == GetIntegralInfoRequestBean.class) {
                JSONObject object = JSON.parseObject(responseBean.getData());
                Double score = object.getDouble("score");
                tvIntegral.setText(score + "");
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }

    public void isChooseGift(GiftBean giftBean) {
        mCurrentGiftBean = giftBean;
        tvSend.setSelected(giftBean.isSecelet());
    }

    private Handler mHandler = new Handler();

    private Runnable mHideTopRunnable = new Runnable() {
        @Override
        public void run() {
            rlControlTop.setVisibility(View.GONE);
        }
    };

    private Runnable mHideBottomRunnable = new Runnable() {
        @Override
        public void run() {
            rlControlBottom.setVisibility(View.GONE);
        }
    };

    private void hideTopControl() {
        rlControlTop.setVisibility(View.VISIBLE);
        mHandler.removeCallbacks(mHideTopRunnable);
        mHandler.postDelayed(mHideTopRunnable, 3000);
    }

    private void hideBottomControl() {
        rlControlBottom.setVisibility(View.VISIBLE);
        mHandler.removeCallbacks(mHideBottomRunnable);
        mHandler.postDelayed(mHideBottomRunnable, 3000);
    }

    /**
     * 控制竖屏时押注、押注记录和礼物的添加
     *
     * @param index
     */
    private void showFragment(int index) {
        if (index != 2) {
            tvBet.setSelected(index == 0);
            tvBetRecord.setSelected(index == 1);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mBaseFragments[index].isAdded()) {
            transaction.add(R.id.frameLayout_bet, mBaseFragments[index], index + "");
        }
        for (int i = 0; i < mBaseFragments.length; i++) {
            if (index == i) {
                transaction.show(mBaseFragments[i]);
            } else {
                transaction.hide(mBaseFragments[i]);
            }
        }
        transaction.commit();
    }


    /**
     * 控制横屏时押注和押注记录的添加
     *
     * @param index
     */
    public void showLandFragment(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mBaseLandFragments[index].isAdded()) {
            transaction.add(R.id.frameLayout_land_bet, mBaseLandFragments[index], index + "");
        }
        for (int i = 0; i < mBaseLandFragments.length; i++) {
            if (index == i) {
                transaction.show(mBaseLandFragments[i]);
            } else {
                transaction.hide(mBaseLandFragments[i]);
            }
        }
        transaction.commit();
    }

    public void closeLandLayout() {
        rlLandBetLayout.setVisibility(View.GONE);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // 加入横屏要处理的代码

            ViewGroup.LayoutParams params = frameLayoutPlay.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;

            llPortLayout.setVisibility(View.GONE);
            rlControlTop.setVisibility(View.GONE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // 加入竖屏要处理的代码

            ViewGroup.LayoutParams params = frameLayoutPlay.getLayoutParams();
            params.height = DensityUtil.dip2px(getCtx(), 210);
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;

            llPortLayout.setVisibility(View.VISIBLE);

            rlControlBottom.setVisibility(View.GONE);
            rlLandBetLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMLivePlayer != null) {
            mMLivePlayer.resume();
        }

        if (mPlayerView != null) {
            mPlayerView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mMLivePlayer != null) {
            mMLivePlayer.pause();
        }

        if (mPlayerView != null) {
            mPlayerView.onPause();
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //竖屏
            super.onBackPressed();
        } else {
            //横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMLivePlayer != null) {
            mMLivePlayer.stopPlay(true);
        }
        mPlayerView.onDestroy();
    }


}
