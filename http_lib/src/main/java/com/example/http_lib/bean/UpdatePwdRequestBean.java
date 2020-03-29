package com.example.http_lib.bean;


import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created by ch_wangjun on 2017/7/24.
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.updatePwd,method = RequestMethodEnum.POST)
public class UpdatePwdRequestBean extends BaseBean implements ILocalHost {

    public String phone;
    public String code;
    public String pass;

}
