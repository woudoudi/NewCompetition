package com.yidao.module_lib.base.ipress;

import android.content.Intent;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/16
 */
public interface IOtherPlatformPress {


    void qqLogin(int type);

    void weiboLoin(int type);

    void weiboCallBack(int requestCode, int resultCode, Intent data);
}
