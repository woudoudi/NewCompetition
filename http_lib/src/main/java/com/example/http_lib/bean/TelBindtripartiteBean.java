package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/30
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.bindPhone)
public class TelBindtripartiteBean extends BaseBean {
    public String openId;
    public int type;
    public String userDeviceId;
    public String userNickName;
    public String userHeadPortrait;
    public String userSex;
    public String userBirthday;
    public String userCity;

}
