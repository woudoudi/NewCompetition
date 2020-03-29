package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.getSendCode,method = RequestMethodEnum.POST)
public class SendCodeRequestBean extends BaseBean implements ILocalHost {
    public String phone;

}
