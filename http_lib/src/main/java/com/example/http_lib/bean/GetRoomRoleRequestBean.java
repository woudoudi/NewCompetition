package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.getPersonRoleDetail)
public class GetRoomRoleRequestBean extends BaseBean implements ILocalHost {



}
