package com.example.administrator.competition.activity.guess;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.my.MyBetActivity;
import com.example.administrator.competition.adapter.NumberAdapter;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.dialog.AnswerDialog;
import com.example.administrator.competition.dialog.BetDialog;
import com.example.administrator.competition.dialog.ExitRoomDialog;
import com.example.administrator.competition.dialog.MorePopWindow;
import com.example.administrator.competition.dialog.NumberPopWindow;
import com.example.administrator.competition.dialog.RuleDialog;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.view.RunTextView;
import com.example.http_lib.bean.GetNumberLevelRequestBean;
import com.example.http_lib.bean.GetRoomRoleRequestBean;
import com.example.http_lib.bean.LeaveRoomRequestBean;
import com.example.http_lib.bean.NumberBetRequestBean;
import com.example.http_lib.bean.PrepareRoomRequestBean;
import com.example.http_lib.bean.StartRoomRequestBean;
import com.example.http_lib.bean.WebsocketBean;
import com.example.http_lib.enums.RoomType;
import com.example.http_lib.response.BetNumberGradeBean;
import com.example.http_lib.response.GetRoomListBean;
import com.example.http_lib.websocket.MessageBean;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnClick;
import jiguang.chat.activity.fragment.ChatFragment;
import jiguang.chat.view.RecordVoiceButton;

public class GuessCompetitionActivity extends BaseUrlView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.tv_trumpet)
    TextView tvTrumpet;
    @BindView(R.id.tv_home_game)
    TextView tvHomeGame;
    @BindView(R.id.tv_look_on)
    TextView tvLookOn;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_question_number)
    TextView tvQuestionNumber;
    @BindView(R.id.tv_score)
    TextView tvScore;

    @BindView(R.id.tickerView1)
    TickerView tickerView1;
    @BindView(R.id.tickerView2)
    TickerView tickerView2;
    @BindView(R.id.tickerView3)
    TickerView tickerView3;
    @BindView(R.id.tickerView4)
    TickerView tickerView4;
    @BindView(R.id.tickerView5)
    TickerView tickerView5;
    @BindView(R.id.et_input)
    EditText etInput;

    @BindView(R.id.iv_bet)
    ImageView ivBet;
    @BindView(R.id.iv_speak)
    ImageView ivSpeak;
    @BindView(R.id.tv_state)
    TextView tvState;

    @BindView(R.id.rl_home_group)
    RelativeLayout rlHomeGroup;
    @BindView(R.id.rl_observe)
    NestedScrollView rlObserve;

    @BindView(R.id.tv_detail1)
    TextView tvDetail1;
    @BindView(R.id.tv_detail2)
    TextView tvDetail2;

    @BindView(R.id.btn_voice)
    RecordVoiceButton btnVoice;
    @BindView(R.id.tv_single_1)
    TextView tvSingle1;
    @BindView(R.id.tv_single_2)
    TextView tvSingle2;
    @BindView(R.id.tv_single_3)
    TextView tvSingle3;
    @BindView(R.id.tv_double_1)
    TextView tvDouble1;
    @BindView(R.id.tv_double_2)
    TextView tvDouble2;
    @BindView(R.id.tv_double_3)
    TextView tvDouble3;
    @BindView(R.id.tv_concrete_1)
    TextView tvConcrete1;
    @BindView(R.id.tv_concrete_2)
    TextView tvConcrete2;
    @BindView(R.id.tv_concrete_3)
    TextView tvConcrete3;
    @BindView(R.id.tv_tail_1)
    TextView tvTail1;
    @BindView(R.id.tv_tail_2)
    TextView tvTail2;
    @BindView(R.id.tv_tail_3)
    TextView tvTail3;

    private RunTextView mRunTextView1 = new RunTextView();
    private RunTextView mRunTextView2 = new RunTextView();
    private RunTextView mRunTextView3 = new RunTextView();

    private RunTextView mRunTextView4 = new RunTextView();
    private RunTextView mRunTextView5 = new RunTextView();


    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();

    private int mGuessGradeValue;

    /**
     * 0 OWNER 房主;
     * 1 ZHU 主观;
     * 2 PANG 旁观;
     */
    private int mRoleState;

    String level = "";

    private ChatFragment mChatFragment = new ChatFragment();
    private GetRoomListBean mRoomListBean;

    @Override
    protected int getView() {
        return R.layout.activity_guess_competition;
    }

    @Override
    public void init() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        mGuessGradeValue = getIntent().getIntExtra(CommonConfig.guessGradeKey, 0);
        if (mGuessGradeValue == CommonConfig.primaryValue) {
            tvTitle.setText("初级场");
            level = RoomType.RoomGradeType.PRI.key1;
        } else if (mGuessGradeValue == CommonConfig.middleValue) {
            tvTitle.setText("中级场");
            level = RoomType.RoomGradeType.MIDDLE.key1;
        } else if (mGuessGradeValue == CommonConfig.highValue) {
            tvTitle.setText("高级场");
            level = RoomType.RoomGradeType.SENIOR.key1;
        }

        ivRight.setImageResource(R.mipmap.detail);

        tickerView1.setCharacterLists(TickerUtils.provideNumberList());
        tickerView1.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);

        tickerView2.setCharacterLists("+-");
        tickerView2.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);

        tickerView3.setCharacterLists(TickerUtils.provideNumberList());
        tickerView3.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);

        tickerView4.setCharacterLists("+-");
        tickerView4.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);

        tickerView5.setCharacterLists(TickerUtils.provideNumberList());
        tickerView5.setPreferredScrollingDirection(TickerView.ScrollingDirection.ANY);

        tickerView2.setText("+");
        tickerView4.setText("-");

        mRoomListBean = (GetRoomListBean) getIntent().getSerializableExtra(CommonConfig.guessRoomKey);
        tvTrumpet.setText(mRoomListBean.getNoticeBoard());

        if (CommonConfig.TenAndOneTableMode == CommonConfig.TenPersonTable) {

        } else if (CommonConfig.TenAndOneTableMode == CommonConfig.OneVOneTable) {
            ivBet.setVisibility(View.GONE);
            ivSpeak.setVisibility(View.GONE);
            tvState.setVisibility(View.GONE);
        }
        updateBoardPersonNumber(mRoomListBean.getPersonZhu(),mRoomListBean.getPersonPang());

        for (int i = 0; i < 100; i++) {
            mList1.add(i + "");
        }

        for (int i = 0; i < 10; i++) {
            mList2.add(i + "");
        }

        mPresenter.getNumberLevel(level);

        mPresenter.getRoomDetail(mRoomListBean.getId());

        mPresenter.getRoomRoleDetail();  //{"OWNER":"房主","ZHU":"主观","PANG":"旁观"}

        mPresenter.getBetKindType();    //{"NUMBER":"数字","COMMON":"常识","UNKNOWN":"未知","FIGHT":"对抗","LIVE":"直播"}
        mPresenter.getBetNumberType();  //{"VAL":"具体数字","EVEN_ODD":"单双","KING":"王者","UNKNOWN":"未知","TAIL_DIGIT":"尾数","SEL":"选择题"}

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout_chat, mChatFragment, "").show(mChatFragment).commit();

        addViewToList();
    }

    @OnClick({R.id.iv_back, R.id.iv_right, R.id.iv_bet, R.id.iv_speak, R.id.iv_send, R.id.iv_switch, R.id.tv_state, R.id.tv_detail1, R.id.tv_detail2, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ExitRoomDialog exitRoomDialog = new ExitRoomDialog(getCtx());
                exitRoomDialog.setCheckListener(new ExitRoomDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        mPresenter.leaveRoom(mRoomListBean.getId());
                    }
                });
                exitRoomDialog.show();
                break;
            case R.id.iv_right:
                MorePopWindow morePopWindow = new MorePopWindow(getCtx(), ivRight);
                morePopWindow.setOnItemClick(new MorePopWindow.OnItemClick() {
                    @Override
                    public void onBetRule() {
                        RuleDialog ruleDialog = new RuleDialog(getCtx());
                        ruleDialog.show();
                    }
                    @Override
                    public void onBetRecord() {
                        Bundle bundle = new Bundle();
                        bundle.putInt(CommonConfig.betRecordKey,CommonConfig.betNotKindValue);
                        skipActivity(MyBetActivity.class,bundle);
                    }
                });
                morePopWindow.showPopWindow();
                break;
            case R.id.iv_bet:
                BetDialog betDialog = new BetDialog(getCtx(),level);
                betDialog.show();
                break;
            case R.id.iv_speak:
                btnVoice.setConversation(mChatFragment.getConversation(), mChatFragment.getChattingListAdapter(), mChatFragment.getChatView());

                if (btnVoice.getVisibility() == View.GONE) {
                    btnVoice.setVisibility(View.VISIBLE);
                } else {
                    btnVoice.setVisibility(View.GONE);
                }

                AnswerDialog answerDialog = new AnswerDialog(getCtx());
//                answerDialog.show();
                break;
            case R.id.iv_send:
                NumberBetRequestBean requestBean = new NumberBetRequestBean();
                requestBean.betInput = etInput.getText().toString();
                requestBean.betType = RoomType.BetKindType.NUMBER.key1;
                requestBean.kind = "JINGCAIZHU";
                mPresenter.numberBet("", "", requestBean);
                break;
            case R.id.iv_switch:
                startNumRoll();
                break;
            case R.id.tv_state:
                if (mRoleState == 0) {
                    mPresenter.startRoom(mRoomListBean.getId());
                } else if (mRoleState == 1) {
                    mPresenter.prepareRoom(mRoomListBean.getId());
                }
                break;
            case R.id.tv_detail1:
                final NumberPopWindow numberPopWindow1 = new NumberPopWindow();
                numberPopWindow1.showNumberPopWindow(getCtx(), tvDetail1, mList1, new NumberAdapter.OnItemClick() {
                    @Override
                    public void onItemClick(String item) {
                        tvDetail1.setText(item);
                        numberPopWindow1.dismiss();
                    }
                });
                break;
            case R.id.tv_detail2:
                final NumberPopWindow numberPopWindow2 = new NumberPopWindow();
                numberPopWindow2.showNumberPopWindow(getCtx(), tvDetail2, mList2, new NumberAdapter.OnItemClick() {
                    @Override
                    public void onItemClick(String item) {
                        tvDetail2.setText(item);
                        numberPopWindow2.dismiss();
                    }
                });
                break;
            case R.id.tv_sure:
                int single = getSelectItem(msingleList);
                int double1 = getSelectItem(mdoubleList);
                int concrete = getSelectItem(mconcreteList);
                int tail = getSelectItem(mTailList);

                if (single != -1) {
                    requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.EVEN_ODD.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if (double1 != -1) {
                    requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.EVEN_ODD.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if (concrete != -1) {
                    requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.VAL.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if (tail != -1) {
                    requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.TAIL_DIGIT.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                break;
        }
    }

    private void startNumRoll() {
        mRunTextView1.runWithAnimation(6, tickerView1);
        mRunTextView2.runWithAnimation(8, tickerView3);
        mRunTextView3.runWithAnimation(9, tickerView5);

        mRunTextView4.runTextWithAnimation(8, tickerView2);
        mRunTextView5.runTextWithAnimation(9, tickerView4);
    }

    private void setState(int state) {
        if (state == 0) {
            tvState.setText("开始");
            tvState.setEnabled(true);
        } else if (state == 1) {
            tvState.setText("准备");
            tvState.setEnabled(true);
        } else if (state == 2) {
            tvState.setText("取消准备");
            tvState.setEnabled(true);
        } else if (state == 3) {
            tvState.setText("已开始");
            tvState.setEnabled(false);
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

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageBean event) {
        WebsocketBean websocketBean = JSON.parseObject(event.message, WebsocketBean.class);

        updateBoardPersonNumber(10,1000);

    }

    private void updateBoardPersonNumber(int zhu,int pang){
        if(CommonConfig.TenAndOneTableMode==CommonConfig.TenPersonTable){
            tvHomeGame.setText(String.format("主场：10/%s", zhu));
        } else {
            tvHomeGame.setText(String.format("主场：1/%s", zhu));
        }
        tvLookOn.setText(String.format("旁观：%s",pang));
    }


    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == LeaveRoomRequestBean.class) {
                ViewManager.getInstance().finishView();
            }
            if (requestCls == StartRoomRequestBean.class) {
                setState(3);
            }
            if (requestCls == NumberBetRequestBean.class) {

            }
            if (requestCls == PrepareRoomRequestBean.class) {
                setState(2);
            }
            if (requestCls == GetRoomRoleRequestBean.class) {
                JSONObject object = JSON.parseObject(responseBean.getData());
                if (object.containsKey(RoomType.RoomRoleType.OWNER.key1)) {
                    mRoleState = 0;
                    setState(0);
                } else if (object.containsKey(RoomType.RoomRoleType.ZHU.key1)) {
                    mRoleState = 1;
                    setState(1);
                } else if (object.containsKey(RoomType.RoomRoleType.PANG.key1)) {
                    mRoleState = 2;
                    rlHomeGroup.setVisibility(View.GONE);
                    rlObserve.setVisibility(View.VISIBLE);
                }
            }
            if(requestCls==GetNumberLevelRequestBean.class){
                List<BetNumberGradeBean> gradeBeans = JSON.parseArray(responseBean.getListData(), BetNumberGradeBean.class);
                if(gradeBeans.size()>0){
                    initCentView(gradeBeans.get(0));
                }
            }
        } else {
            ToastUtil.showShortToast(responseBean.getRetMsg());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    @OnClick({R.id.tv_single_1, R.id.tv_single_2, R.id.tv_single_3, R.id.tv_double_1, R.id.tv_double_2, R.id.tv_double_3, R.id.tv_concrete_1, R.id.tv_concrete_2, R.id.tv_concrete_3, R.id.tv_tail_1, R.id.tv_tail_2, R.id.tv_tail_3})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.tv_single_1:
                clearListSelect(mdoubleList);
                betItemChoose(msingleList, 0);
                break;
            case R.id.tv_single_2:
                clearListSelect(mdoubleList);
                betItemChoose(msingleList, 1);
                break;
            case R.id.tv_single_3:
                clearListSelect(mdoubleList);
                betItemChoose(msingleList, 2);
                break;
            case R.id.tv_double_1:
                clearListSelect(msingleList);
                betItemChoose(mdoubleList, 0);
                break;
            case R.id.tv_double_2:
                clearListSelect(msingleList);
                betItemChoose(mdoubleList, 1);
                break;
            case R.id.tv_double_3:
                clearListSelect(msingleList);
                betItemChoose(mdoubleList, 2);
                break;
            case R.id.tv_concrete_1:
                betItemChoose(mconcreteList, 0);
                break;
            case R.id.tv_concrete_2:
                betItemChoose(mconcreteList, 1);
                break;
            case R.id.tv_concrete_3:
                betItemChoose(mconcreteList, 2);
                break;
            case R.id.tv_tail_1:
                betItemChoose(mTailList, 0);
                break;
            case R.id.tv_tail_2:
                betItemChoose(mTailList, 1);
                break;
            case R.id.tv_tail_3:
                betItemChoose(mTailList, 2);
                break;
        }
    }

    List<View> mTailList = new ArrayList<>();
    List<View> mconcreteList = new ArrayList<>();
    List<View> msingleList = new ArrayList<>();
    List<View> mdoubleList = new ArrayList<>();

    private void addViewToList() {
        msingleList.add(tvSingle1);
        msingleList.add(tvSingle2);
        msingleList.add(tvSingle3);

        mdoubleList.add(tvDouble1);
        mdoubleList.add(tvDouble2);
        mdoubleList.add(tvDouble3);

        mconcreteList.add(tvConcrete1);
        mconcreteList.add(tvConcrete2);
        mconcreteList.add(tvConcrete3);

        mTailList.add(tvTail1);
        mTailList.add(tvTail2);
        mTailList.add(tvTail3);
    }

    private void betItemChoose(List<View> list, int index) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                list.get(i).setSelected(false);
            } else {
                list.get(i).setSelected(i == index);
            }
        }
    }

    private void clearListSelect(List<View> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setSelected(false);
        }
    }

    private int getSelectItem(List<View> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                return i;
            }
        }
        return -1;
    }


    private void initCentView(BetNumberGradeBean bean){
        if(bean.getScores().size()>2){
            tvSingle1.setText(bean.getScores().get(0)+"\n1.24");
            tvSingle2.setText(bean.getScores().get(1)+"\n1.24");
            tvSingle3.setText(bean.getScores().get(2)+"\n1.24");

            tvDouble1.setText(bean.getScores().get(0)+"\n1.24");
            tvDouble2.setText(bean.getScores().get(1)+"\n1.24");
            tvDouble2.setText(bean.getScores().get(2)+"\n1.24");

            tvConcrete1.setText(bean.getScores().get(0)+"\n1.24");
            tvConcrete2.setText(bean.getScores().get(1)+"\n1.24");
            tvConcrete3.setText(bean.getScores().get(2)+"\n1.24");

            tvTail1.setText(bean.getScores().get(0)+"\n1.24");
            tvTail2.setText(bean.getScores().get(1)+"\n1.24");
            tvTail3.setText(bean.getScores().get(2)+"\n1.24");
        }
    }
}
