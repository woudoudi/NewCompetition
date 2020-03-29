package com.example.administrator.competition.activity.guess;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.competition.R;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.dialog.ExitRoomDialog;
import com.example.administrator.competition.dialog.KindSenceDialog;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.bean.LeaveRoomRequestBean;
import com.example.http_lib.bean.NumberBetRequestBean;
import com.example.http_lib.bean.WebsocketBean;
import com.example.http_lib.enums.RoomType;
import com.example.http_lib.response.GetRoomListBean;
import com.example.http_lib.websocket.MessageBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class GuessKingComonSenceActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_person_number)
    TextView tvPersonNumber;
    @BindView(R.id.tv_question_number)
    TextView tvQuestionNumber;
    @BindView(R.id.tv_question_no)
    TextView tvQuestionNo;
    @BindView(R.id.tv_question_no_1)
    TextView tvQuestionNo1;
    @BindView(R.id.tv_question)
    TextView tvQuestion;
    @BindView(R.id.tv_A)
    TextView tvA;
    @BindView(R.id.tv_B)
    TextView tvB;
    @BindView(R.id.tv_C)
    TextView tvC;
    @BindView(R.id.tv_D)
    TextView tvD;
    @BindView(R.id.tv_question_setter)
    TextView tvQuestionSetter;
    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.iv_send)
    ImageView ivSend;

    private List<TextView> mTextViews = new ArrayList<>();
    private GetRoomListBean mRoomListBean;

    @Override
    protected int getView() {
        return R.layout.activity_guess_common_sence;
    }

    @Override
    public void init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        tvTitle.setText("王者常识");
        ivRight.setImageResource(R.mipmap.common_sence_detail);

        mTextViews.add(tvA);
        mTextViews.add(tvB);
        mTextViews.add(tvC);
        mTextViews.add(tvD);

        mRoomListBean = (GetRoomListBean) getIntent().getSerializableExtra(CommonConfig.guessRoomKey);

        if (CommonConfig.TenAndOneTableMode == CommonConfig.TenPersonTable) {
            tvPersonNumber.setText(String.format("人数：10/%s", mRoomListBean.getPersonZhu()));
        } else if (CommonConfig.TenAndOneTableMode == CommonConfig.OneVOneTable) {
            tvPersonNumber.setText(String.format("人数：1/%s", mRoomListBean.getPersonZhu()));
        }


    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.tv_A, R.id.tv_B, R.id.tv_C, R.id.tv_D, R.id.tv_question_setter, R.id.iv_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_right:
                break;
            case R.id.tv_A:
                KindSenceDialog kindSenceDialog = new KindSenceDialog(getCtx());
                kindSenceDialog.setCheckListener(new KindSenceDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        answer(0);
                    }
                });
                kindSenceDialog.show();
                break;
            case R.id.tv_B:
                kindSenceDialog = new KindSenceDialog(getCtx());
                kindSenceDialog.setCheckListener(new KindSenceDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        answer(1);
                    }
                });
                kindSenceDialog.show();
                break;
            case R.id.tv_C:
                kindSenceDialog = new KindSenceDialog(getCtx());
                kindSenceDialog.setCheckListener(new KindSenceDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        answer(2);
                    }
                });
                kindSenceDialog.show();
                break;
            case R.id.tv_D:
                kindSenceDialog = new KindSenceDialog(getCtx());
                kindSenceDialog.setCheckListener(new KindSenceDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        answer(3);
                    }
                });
                kindSenceDialog.show();
                break;
            case R.id.tv_question_setter:
                break;
            case R.id.iv_send:

                break;
        }
    }

    private void choose(int index) {
        for (int i = 0; i < 4; i++) {
            mTextViews.get(i).setSelected(i == index);
        }
    }

    private void answer(int index) {
        String answer = index == 0 ? "A" : index == 1 ? "B" : index == 2 ? "C" : "D";
        choose(index);
        NumberBetRequestBean requestBean = new NumberBetRequestBean();
        requestBean.kind = RoomType.BetKindType.COMMON.key1;
        requestBean.betType = RoomType.BetNumberType.SEL.key1;
        requestBean.betInput = answer;
        mPresenter.numberBet("", "", requestBean);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageBean event) {
        WebsocketBean websocketBean = JSON.parseObject(event.message, WebsocketBean.class);

    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == NumberBetRequestBean.class) {

            }
            if (requestCls == LeaveRoomRequestBean.class) {
                ViewManager.getInstance().finishView();
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }

    @Override
    public void onBackPressed() {
        ExitRoomDialog exitRoomDialog = new ExitRoomDialog(getCtx());
        exitRoomDialog.setCheckListener(new ExitRoomDialog.OnFillCheckListener() {
            @Override
            public void onFillCheck() {
                mPresenter.leaveRoom(mRoomListBean.getId());
            }
        });
        exitRoomDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
