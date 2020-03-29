package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.getKindRecord)
public class GetKindRecordRequestBean extends BaseBean implements ILocalHost {

    public String kind;

    public Integer page;
    public Integer size = 10;


}
