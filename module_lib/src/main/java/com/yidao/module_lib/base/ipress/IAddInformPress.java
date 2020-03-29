package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IAddInformPress extends IBasePress {


    void addInform(Long informUid,Long informAccusedId, int informType, String informContent,String informDes,  Long informVideoId,Long informCommentId,String informImgUrl);

}
