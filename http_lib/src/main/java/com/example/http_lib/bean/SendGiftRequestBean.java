package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.sendGift,method = RequestMethodEnum.POST)
public class SendGiftRequestBean extends BaseBean implements ILocalHost {

    public String id;

    private String kind;    //礼物分组的种类

    public Long groupId;   //在房间里送则传房间ID

    public Long receiverId;    //接收人的ID


}
