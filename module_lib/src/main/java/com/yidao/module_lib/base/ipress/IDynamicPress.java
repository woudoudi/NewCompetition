package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IDynamicPress extends IBasePress {

    void getDynamicList(int pageIndex, int tag);

    void addNewSupport(String type,String supportTargetId,String supportUid,int position);

    void addComment(String commentTargetId,String commentUserId,String commentContent);

}
