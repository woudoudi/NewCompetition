package com.example.administrator.competition.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.NumberAdapter;
import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.example.administrator.competition.view.NumberBetContainer;
import com.example.http_lib.bean.GetNumberLevelRequestBean;
import com.example.http_lib.bean.NumberBetRequestBean;
import com.example.http_lib.enums.RoomType;
import com.example.http_lib.response.BetNumberGradeBean;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class BetDialog extends BaseDialog {

    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_detail1)
    TextView tvDetail1;
    @BindView(R.id.tv_detail2)
    TextView tvDetail2;
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
    @BindView(R.id.tv_sure)
    TextView tvSure;


    private List<String> mList1;
    private List<String> mList2;

    private CommonPresenter mPresenter = new CommonPresenter(this);


    List<View> mTailList = new ArrayList<>();
    List<View> mconcreteList = new ArrayList<>();
    List<View> msingleList = new ArrayList<>();
    List<View> mdoubleList = new ArrayList<>();

    private String level;

    public BetDialog(Context context,String level) {
        super(context);
        this.level = level;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_quizess_bet;
    }

    @Override
    protected void init() {
        super.init();
        ivClose.setVisibility(View.VISIBLE);

        mList1 = new ArrayList<>();
        mList2 = new ArrayList<>();

        mTailList = new ArrayList<>();
        mconcreteList = new ArrayList<>();
        msingleList = new ArrayList<>();
        mdoubleList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            mList1.add(i + "");
        }

        for (int i = 0; i < 10; i++) {
            mList2.add(i + "");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addViewToList();

        mPresenter.getNumberLevel(level);
    }

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

    private int getSelectItem(List<View> list){
        for (int i=0;i<list.size();i++){
            if(list.get(i).isSelected()){
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

    @Override
    protected void initPress() {

    }

    @OnClick({R.id.tv_detail1, R.id.tv_detail2, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
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
        }
    }

    @OnClick({R.id.tv_single_1, R.id.tv_single_2, R.id.tv_single_3, R.id.tv_double_1, R.id.tv_double_2, R.id.tv_double_3, R.id.tv_concrete_1, R.id.tv_concrete_2, R.id.tv_concrete_3, R.id.tv_tail_1, R.id.tv_tail_2, R.id.tv_tail_3, R.id.tv_sure})
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
            case R.id.tv_sure:
                dismiss();
                int single = getSelectItem(msingleList);
                int double1 = getSelectItem(mdoubleList);
                int concrete = getSelectItem(mconcreteList);
                int tail = getSelectItem(mTailList);

                if(single!=-1){
                    NumberBetRequestBean requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.EVEN_ODD.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if(double1!=-1){
                    NumberBetRequestBean requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.EVEN_ODD.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if(concrete!=-1){
                    NumberBetRequestBean requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.VAL.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                if(tail!=-1){
                    NumberBetRequestBean requestBean = new NumberBetRequestBean();
                    requestBean.kind = RoomType.BetKindType.NUMBER.key1;
                    requestBean.betType = RoomType.BetNumberType.TAIL_DIGIT.key1;
                    mPresenter.numberBet("", "", requestBean);
                }
                break;
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if (success) {
            if (requestCls == NumberBetRequestBean.class) {

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
}
