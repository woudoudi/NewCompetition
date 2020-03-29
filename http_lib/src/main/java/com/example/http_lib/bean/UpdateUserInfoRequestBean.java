package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.getUserInfo,method = RequestMethodEnum.PUT)
public class UpdateUserInfoRequestBean extends BaseBean implements ILocalHost {

    public String nickname ;   //昵称
    public Integer sex ;//1,0
    public String avatar;  //头像


}
