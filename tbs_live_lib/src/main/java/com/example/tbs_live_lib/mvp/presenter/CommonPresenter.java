package com.example.tbs_live_lib.mvp.presenter;

import com.example.http_lib.bean.GetTokenRequestBean;
import com.example.http_lib.bean.StartLiveRequestBean;
import com.example.http_lib.bean.UpdateLiveInfoRequestBean;
import com.example.tbs_live_lib.mvp.model.CommomModel;
import com.yidao.module_lib.base.BasePressPlus;
import com.yidao.module_lib.base.ibase.IBaseView;

public class CommonPresenter extends BasePressPlus<IBaseView>{

    private CommomModel mCommomModel;

    public CommonPresenter(IBaseView view) {
        super(view);
        mCommomModel = new CommomModel(this);
    }

    public void getToken(){
        GetTokenRequestBean getTokenRequestBean = new GetTokenRequestBean();
        setRequst(getTokenRequestBean,mCommomModel);
    }

    public void startLive(StartLiveRequestBean startLiveRequestBean){
        setRequst(startLiveRequestBean,mCommomModel);
    }

    public void updateLiveInfo(UpdateLiveInfoRequestBean infoRequestBean){
        setRequst(infoRequestBean,mCommomModel);
    }
}
