package com.yidao.module_lib.config;

import android.os.Environment;

/**
 * Created by xiaochan on 2017/6/19.
 */

public class Config {
//    public static final String VERSION = "v1.0.1";

    public static final boolean ISDEBUG = true;

//    public static final String ENVIRONMENT = "dev"; // 项目环境：dev, test, prod

//    public static final String path = "http://ofjj1t0ai.bkt.clouddn.com/";

    public static boolean isPublish = true;

    public static boolean PublishTag = false; // 编辑页面点编辑返回后 将改值设为true, 代表在编辑页面已经save了，发布页面不需要再次save，否则添加的mv音乐都没

    public static boolean isOnclick=true;

    public static final String BASE_STORAGE_DIR = Environment.getExternalStorageDirectory() + "/competition";

    public static final String VIDEO_STORAGE_DIR = BASE_STORAGE_DIR + "/ShortVideo/";
    public static final String PHOTO_STORAGE_DIR = BASE_STORAGE_DIR + "/photo/";
    public static final String PHOTO_STORAGE_DOWMLOAD_DIR = BASE_STORAGE_DIR + "/download/";
}
