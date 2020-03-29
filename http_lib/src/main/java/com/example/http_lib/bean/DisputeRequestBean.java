package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.dispute,method = RequestMethodEnum.POST)
public class DisputeRequestBean extends BaseBean implements ILocalHost {


}
