package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IVideoPlayPress extends IBasePress {

    void getCommentList(int pageIndex, String commentTargetId, String commentType,int position);

    void getVideoList(int pageIndex,int type);

    void praise(String supportTargetId,String type,int position);

    void addAttention(long concernUid,long concernTargetId,int concernType,int type,int position);

    void getUserInfo(int concernUid,int concernUserid,int position);

    void getVideoDetail(long videoId,long userId,int position);

    void searchVideo(int pageIndex, String keyword);

    void videoFullPlay(long userId, long videoId);

    void getRecommendVideoList(long userId, int pageIndex,int showCount);
}
