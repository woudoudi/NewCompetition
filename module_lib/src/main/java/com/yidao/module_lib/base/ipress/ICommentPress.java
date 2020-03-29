package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ICommentPress extends IBasePress {


    void getCommentList(int pageIndex, String commentTargetId, String commentType);

    void getCommentReply(int pageIndex, String commentId,int position);

    void addComment(String commentTargetId,String commentUserId,String commentType,String commentContent,long videoId);

    void addNewSupport(String type,String supportTargetId,String supportUid,String supportType,String videoId,int position);
}
