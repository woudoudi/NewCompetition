package com.example.administrator.competition.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.NumberAdapter;
import com.example.administrator.competition.dialog.NumberPopWindow;
import com.example.administrator.competition.mvp.presenter.CommonPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NumberBetContainer{

    @BindView(R.id.iv_close)
    ImageView ivClose;
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
    @BindView(R.id.tv_detail1)
    TextView tvDetail1;
    @BindView(R.id.tv_concrete_1)
    TextView tvConcrete1;
    @BindView(R.id.tv_concrete_2)
    TextView tvConcrete2;
    @BindView(R.id.tv_concrete_3)
    TextView tvConcrete3;
    @BindView(R.id.tv_detail2)
    TextView tvDetail2;
    @BindView(R.id.tv_tail_1)
    TextView tvTail1;
    @BindView(R.id.tv_tail_2)
    TextView tvTail2;
    @BindView(R.id.tv_tail_3)
    TextView tvTail3;
    @BindView(R.id.tv_sure)
    TextView tvSure;


    private Context mContext;
    private LinearLayout mView;
    private CommonPresenter mPresenter;

    private List<String> mList1 = new ArrayList<>();
    private List<String> mList2 = new ArrayList<>();

    public NumberBetContainer(Context context, LinearLayout view) {
        this.mContext = context;
        ButterKnife.bind(view);
        this.mView = view;

        for (int i = 0; i < 100; i++) {
            mList1.add(i + "");
        }

        for (int i = 0; i < 10; i++) {
            mList2.add(i + "");
        }
    }

    private void initView(View view){
        ivClose = view.findViewById(R.id.iv_close);
        tvSingle1 = view.findViewById(R.id.tv_single_1);
        tvSingle2 = view.findViewById(R.id.tv_single_2);
        tvSingle3 = view.findViewById(R.id.tv_single_2);
        tvDouble1 = view.findViewById(R.id.tv_double_1);
        tvDouble2 = view.findViewById(R.id.tv_double_2);
        tvDouble3 = view.findViewById(R.id.tv_double_3);
        tvDetail1 = view.findViewById(R.id.tv_detail1);
        tvDetail2 = view.findViewById(R.id.tv_detail2);
        tvConcrete1 = view.findViewById(R.id.tv_concrete_1);
        tvConcrete2 = view.findViewById(R.id.tv_concrete_2);
        tvConcrete3 = view.findViewById(R.id.tv_concrete_3);
        tvTail1 = view.findViewById(R.id.tv_tail_1);
        tvTail2 = view.findViewById(R.id.tv_tail_2);
        tvTail3 = view.findViewById(R.id.tv_tail_3);
        tvSure = view.findViewById(R.id.tv_sure);
    }

    public void setPresenter(CommonPresenter presenter){
        this.mPresenter = presenter;
    }

    @OnClick({R.id.tv_single_1, R.id.tv_single_2, R.id.tv_single_3, R.id.tv_double_1, R.id.tv_double_2, R.id.tv_double_3, R.id.tv_detail1, R.id.tv_concrete_1, R.id.tv_concrete_2, R.id.tv_concrete_3, R.id.tv_detail2, R.id.tv_tail_1, R.id.tv_tail_2, R.id.tv_tail_3, R.id.tv_sure,R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_single_1:
                break;
            case R.id.tv_single_2:
                break;
            case R.id.tv_single_3:
                break;
            case R.id.tv_double_1:
                break;
            case R.id.tv_double_2:
                break;
            case R.id.tv_double_3:
                break;
            case R.id.tv_detail1:
                final NumberPopWindow numberPopWindow1 = new NumberPopWindow();
                numberPopWindow1.showNumberPopWindow(mContext, tvDetail1, mList1, new NumberAdapter.OnItemClick() {
                    @Override
                    public void onItemClick(String item) {
                        tvDetail1.setText(item);
                        numberPopWindow1.dismiss();
                    }
                });
                break;
            case R.id.tv_concrete_1:
                break;
            case R.id.tv_concrete_2:
                break;
            case R.id.tv_concrete_3:
                break;
            case R.id.tv_detail2:
                final NumberPopWindow numberPopWindow2 = new NumberPopWindow();
                numberPopWindow2.showNumberPopWindow(mContext, tvDetail2, mList2, new NumberAdapter.OnItemClick() {
                    @Override
                    public void onItemClick(String item) {
                        tvDetail2.setText(item);
                        numberPopWindow2.dismiss();
                    }
                });
                break;
            case R.id.tv_tail_1:
                break;
            case R.id.tv_tail_2:
                break;
            case R.id.tv_tail_3:
                break;
            case R.id.tv_sure:
                break;
        }
    }
}
