package com.example.administrator.competition.mvp.model;

import com.example.administrator.competition.mvp.presenter.JCLoginPress;
import com.example.http_lib.bean.LoginRequestBean;
import com.example.http_lib.model.BaseModel;
import com.example.http_lib.utils.UserCacheHelper;
import com.yidao.module_lib.base.BasePress;
import com.yidao.module_lib.base.http.ResponseBean;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public class CommomModel extends BaseModel<BasePress> {

    public CommomModel(BasePress press) {
        super(press);
    }

    @Override
    public void failed(ResponseBean responseBean) {
        super.failed(responseBean);
    }

    @Override
    public void success(ResponseBean responseBean) {
        super.success(responseBean);
        if(responseBean.getRequestClass()==LoginRequestBean.class){
            UserCacheHelper.setToken(responseBean.getData());


            JCLoginPress.JMessageLogin();
        }
    }
}
