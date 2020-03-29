package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IFriendPress extends IBasePress {

    void getFriendList(int pageIndex, long userId);

    void getAttentionVideoList(int pageIndex,int showCount,int type);


}
