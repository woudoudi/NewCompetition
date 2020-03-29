package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IHotSearchPress extends IBasePress {


    void getHotAndScript();

    void getKeyWordList(int pageIndex,int showCount);

    void getRankingRuleList(int type);

}
