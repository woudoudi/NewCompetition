package com.yidao.module_lib.base.imodel;

import com.yidao.module_lib.base.ibase.IBaseModel;
import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface IUserModel extends IBaseModel<IBasePress> {

    void getPhoneCode();

    void login();

    void register();
}
