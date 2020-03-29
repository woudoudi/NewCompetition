package com.example.administrator.competition.activity.my;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.JsonReader;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.administrator.competition.R;
import com.example.administrator.competition.activity.login.LoginUserFirstActivity;
import com.example.administrator.competition.base.BaseUrlView;
import com.example.administrator.competition.dialog.ExitDialog;
import com.example.administrator.competition.mvp.presenter.CommonPresenter;
import com.example.administrator.competition.mvp.presenter.JCLoginPress;
import com.example.http_lib.bean.UpdateUserInfoRequestBean;
import com.example.http_lib.bean.UploadFileRequestBean;
import com.example.http_lib.bean.UserInfoRequestBean;
import com.example.http_lib.response.OverUserInfoBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.base.http.ResponseBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.BitmapUtils;
import com.yidao.module_lib.utils.CommonGlideUtils;
import com.yidao.module_lib.utils.GlideCatchUtil;
import com.yidao.module_lib.utils.ToastUtil;
import com.yidao.module_lib.widget.ChoosePhotoDialog;

import java.io.File;
import java.io.IOException;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MySettingActivity extends BaseUrlView {

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.tv_right)
    TextView tvRight;

    @BindView(R.id.iv_head)
    ImageView ivHead;

    @BindView(R.id.tv_name)
    TextView tvName;

    @BindView(R.id.tv_cache)
    TextView tvCache;

    public static String NickName = "nickName";

    private ChoosePhotoDialog mChoosePhotoDialog;

    @Override
    protected int getView() {
        return R.layout.activity_my_setting;
    }

    @Override
    public void init() {
        tvTitle.setText("设置");
        tvRight.setText("确定");

        mPresenter.getUserInfo();

        tvCache.setText(GlideCatchUtil.getInstance().getCacheSize(getCtx()));
    }

    @OnClick({R.id.iv_back, R.id.tv_right, R.id.rl_clear, R.id.rl_about_us, R.id.tv_logout, R.id.iv_head,R.id.rl_nick})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                ViewManager.getInstance().finishView();
                break;
            case R.id.tv_right:
                UpdateUserInfoRequestBean requestBean = new UpdateUserInfoRequestBean();
                requestBean.nickname = tvName.getText().toString();
                requestBean.avatar = "https://cdn.pixabay.com/photo/2020/03/22/16/18/bread-4957679_960_720.jpg";
                mPresenter.updateUserInfol(requestBean);
                break;
            case R.id.rl_clear:
                GlideCatchUtil.getInstance().clearCacheDiskSelf(getCtx());
                tvCache.setText("0.0MB");
                ToastUtil.showShortToast("已清理完毕");
                break;
            case R.id.rl_about_us:
                skipActivity(MyAboutUsActivity.class);
                break;
            case R.id.tv_logout:
                ExitDialog exitDialog = new ExitDialog(getCtx());
                exitDialog.setCheckListener(new ExitDialog.OnFillCheckListener() {
                    @Override
                    public void onFillCheck() {
                        JCLoginPress.logout(getCtx());
                    }
                });
                exitDialog.show();
                break;
            case R.id.iv_head:
                mChoosePhotoDialog = new ChoosePhotoDialog(getCtx());
                mChoosePhotoDialog.show();
                break;
            case R.id.rl_nick:
                Intent intent = new Intent(this,EditPersonalNickActivity.class);
                intent.putExtra(NickName,tvName.getText().toString());
                startActivityForResult(intent,99);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ChoosePhotoDialog.Album_Code) {
                mChoosePhotoDialog.photoClip(data.getData(), false);
            }
            if (requestCode == ChoosePhotoDialog.Take_Code) {
                mChoosePhotoDialog.photoClip(mChoosePhotoDialog.getUri(), true);
            }
            if (requestCode == ChoosePhotoDialog.Crop_Code) {
                try {
                    Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), mChoosePhotoDialog.getCropUri());
                    String path = BitmapUtils.saveImage(image);
                    CommonGlideUtils.showCirclePhoto(this, path, ivHead);
                    CommonGlideUtils.lubanCompress(getCtx(), path, new CommonGlideUtils.ICompressListener() {
                        @Override
                        public void compressSuccess(File file) {
                            mPresenter.uploadFile(file);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(requestCode == 99){
                if(data!=null){
                    tvName.setText(data.getStringExtra(NickName));
                }
            }
        }
    }

    @Override
    public void onResponse(boolean success, Class requestCls, ResponseBean responseBean) {
        super.onResponse(success, requestCls, responseBean);
        if(success){
            if(requestCls==UploadFileRequestBean.class){


            }
            if(requestCls==UserInfoRequestBean.class){
                OverUserInfoBean userInfoBean = JSON.parseObject(responseBean.getData(),OverUserInfoBean.class);

                CommonGlideUtils.showCirclePhoto(getCtx(),userInfoBean.getAvatar(),ivHead);
                tvName.setText(userInfoBean.getNickname());
            }
        }
    }
}
