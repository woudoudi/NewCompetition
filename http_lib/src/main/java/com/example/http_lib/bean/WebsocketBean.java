package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

import java.util.Map;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
public class WebsocketBean extends BaseBean implements ILocalHost {


    public ChatMessageKindEnum kind;  //消息种类CHAT,GROUP,EVENT

    public ChatMessageTypeEnum type;  //消息类型 TEXT

    public ChatMessageStatusEnum messageStatus;   //消息状态CREATED,READED

    public String content;    //内容

    public Long fromUserId;

    public Long toUserId;

    public Long toGroupId;

    public Map<String, Object> attachment;//附加信息{JSON对象}


    public  enum  ChatMessageKindEnum{
        CHAT,
        GROUP,
        EVENT
    }

    public  enum  ChatMessageTypeEnum{
        TEXT
    }

    public  enum  ChatMessageStatusEnum{
        CREATED,
        READED
    }
}
