package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IOperaPress extends IBasePress {

    void getOperaList(int pageIndex, int scriptActivityType,int userType);

    void getOperaInfo(long userId,int scriptId);

    void getOperaRoleInfo(long userId,int scriptId);

    void getOperaRank(Long roleId, long scriptId);

    void getOperaRankByRole(int sortType, Long scriptId,Long roleId, int scriptType);

    void collectRole(long recordRoleId, long recordScriptId, int type,int position);

    void getVideoList(int pageIndex, int type);

    void searchOpera(int pageIndex, String keyword);

    void searchVideo(int pageIndex, String keyword);

    void searchUser(int pageIndex, String keyword);

    void addAttention(int concernUid,int concernTargetId,int concernType,int type,int position);

    void getTopVideos(int pageIndex,int showCount);

    void getOperaAdverting(long advertisingScriptId);

    void getRecommonMxm();

    void queryFreeVote(String scriptId);

    void sendFreeUserGift(long recUid,long mPlayerId,int position);

}
