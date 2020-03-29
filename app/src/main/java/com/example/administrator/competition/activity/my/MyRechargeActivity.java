package com.example.administrator.competition.activity.my;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.competition.R;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.BitmapUtils;
import com.yidao.module_lib.utils.DensityUtil;
import com.yidao.module_lib.utils.QRCodeUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MyRechargeActivity extends BaseView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qrCode)
    ImageView ivQrCode;

    @Override
    protected int getView() {
        return R.layout.activity_my_recharge;
    }

    @Override
    public void init() {
        tvTitle.setText("充值");

        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap("5645", DensityUtil.dip2px(getCtx(),190), DensityUtil.dip2px(getCtx(),190));
        ivQrCode.setImageBitmap(bitmap);
    }


    @OnClick({R.id.iv_back,R.id.iv_save})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.iv_save:
                BitmapUtils.createBitmap(getCtx(),ivQrCode,System.currentTimeMillis()+".jpg");
                break;
        }
    }
}
