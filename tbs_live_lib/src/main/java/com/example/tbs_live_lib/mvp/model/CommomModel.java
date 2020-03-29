package com.example.tbs_live_lib.mvp.model;

import com.example.http_lib.model.BaseModel;
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
        if (responseBean.getRetCode() == 999){
         /*   Toast.makeText(getPress().getView().getCtx(),"用户在别处已登录",Toast.LENGTH_LONG).show();
            WXEntryActivity.setIsLogin(true);
            UserCacheHelper.logOut();
            QQInfo.getInstance().getmTencent().logout(getPress().getView().getCtx());
            SharePreferenceManager.setCachedUsername("");
            TagAliasOperatorHelper.getInstance().removeJPushAlias();
            JMessageClient.logout();
            ViewManager.getInstance().finishAllView();
            getPress().getView().getCtx().startActivity(new Intent(getPress().getView().getCtx(),LoginView.class));*/

        }

        super.failed(responseBean);

    }
}
