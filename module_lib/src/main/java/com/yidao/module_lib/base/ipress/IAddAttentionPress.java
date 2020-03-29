package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IAddAttentionPress extends IBasePress {


    void addAttention(int concernUid,int concernTargetId,int concernType,int type);

}
