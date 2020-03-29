package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IMessagePress extends IBasePress {

    void getMessageList(int pageIndex, String messageType);

    void getAttentionList(int pageIndex, Long concernTargetId);

    void getAttentionList(int pageIndex,Long concernUid, Long concernTargetId);

    void getBeSupporList(long userId,int pageIndex,int showCount);

    void getCommentMeList(long userId,int pageIndex,int showCount);
}
