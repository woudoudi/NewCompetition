package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.sendKingFight,method = RequestMethodEnum.POST)
public class SendKingFightRequestBean extends BaseBean implements ILocalHost {

    public String inviter;  //发起人

    public String nickInviter;

    public String phoneInviter;

    public String receiver;  //接收人

    public String nickReceiver;

    public String phoneReceiver;

    public Double fscoreA; //发起人分数

    public Double fscoreB; //接收人分数

}
