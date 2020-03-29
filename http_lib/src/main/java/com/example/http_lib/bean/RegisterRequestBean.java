package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.register,method = RequestMethodEnum.POST)
public class RegisterRequestBean extends BaseBean implements ILocalHost {

    public String username;   //用户名
    public String phone;   //手机号
    public String password;  //密码
    public String code;//验证码
    public String qr;  //邀请码

}
