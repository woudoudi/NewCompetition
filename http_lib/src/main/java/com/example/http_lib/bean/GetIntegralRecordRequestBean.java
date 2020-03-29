package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.getIntegralRecord)
public class GetIntegralRecordRequestBean extends BaseBean implements ILocalHost {

    public Integer page;
    public Integer size = 10;

}
