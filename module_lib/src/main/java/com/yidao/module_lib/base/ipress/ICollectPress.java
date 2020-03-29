package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ICollectPress extends IBasePress {

    void collectVideo(long userId, long videoId);

    void collectOperaAndRole(Long roleId, Long operaId,int type);

    void collectGoods(long userId, int goodsId);

    void getCollectVideo(int currentPage, long userId, int showCount);

    void getCollectOpera(int currentPage, long userId);

    void getCollectGoods(int currentPage, long userId, int showCount);

    void getCollectRole(long userId, int pageIndex,int showCount);

    void getBannerList(int scriptId);

}
