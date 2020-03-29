package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.createChatGroup,method = RequestMethodEnum.POST)
public class CreateGroupRequestBean extends BaseBean implements ILocalHost {


    public String name;//名称

    public String descript;//描述

    public String noticeBoard; //公告板

}
