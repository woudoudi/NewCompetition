package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.io.Serializable;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/30
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.wechatLogin,method = RequestMethodEnum.POST)
public class TripartiteLoginBean extends BaseBean implements Serializable {

    public String openId;
    public int type;
    public String userDeviceId;
    public String userNickName;
    public String userHeadPortrait;
    public String userSex;
    public String userBirthday;
    public String userCity;
    public String code;


}
