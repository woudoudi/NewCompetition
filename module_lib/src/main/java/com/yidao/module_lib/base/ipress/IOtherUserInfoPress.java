package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IOtherUserInfoPress extends IBasePress {

    void addAttention(long concernUid,long concernTargetId,int concernType,int type,long position);

    void getUserInfo(int concernUid,int concernUserid);

    void getVideoListByUser(int currentPage,int showCount,long participatingId);

    void starRoad(long starUserId);

    void getSupport(long userId,int supportType,int currentPage,int showCount);

    void getRecommendUser(long userId,int pageIndex,int showCount);

}
