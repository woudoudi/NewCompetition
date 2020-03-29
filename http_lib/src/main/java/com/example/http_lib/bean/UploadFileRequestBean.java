package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.example.http_lib.enums.RequestTypeEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.io.File;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.uploadFile,method = RequestMethodEnum.POST,type = RequestTypeEnum.FORM)
public class UploadFileRequestBean extends BaseBean implements ILocalHost {

    public File file;//名称
}
