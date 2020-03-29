package com.example.http_lib.utils;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.example.http_lib.response.OverUserInfoBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.response.VersionBean;
import com.yidao.module_lib.utils.SharedPreferencesUtils;

public class UserCacheHelper {

    public static String user_Info = "user_info";
    public static String version_info = "versionInfo";

    public static String first_open = "first_open";
    public static String tokenKey = "token";

    public static String over_user_Info = "over_user_info";

    public static UserInfoBean getUserInfo() {
        String userInfo = SharedPreferencesUtils.getString(user_Info, null);
        if (TextUtils.isEmpty(userInfo)) {
            return new UserInfoBean();
        }
        return JSON.parseObject(userInfo, UserInfoBean.class);
    }

    public static void saveUserInfo(String userInfo) {
        SharedPreferencesUtils.putString(user_Info, userInfo);
    }


    public static OverUserInfoBean getOverseaUserInfo() {
        String userInfo = SharedPreferencesUtils.getString(over_user_Info, null);
        if (TextUtils.isEmpty(userInfo)) {
            return new OverUserInfoBean();
        }
        return JSON.parseObject(userInfo, OverUserInfoBean.class);
    }

    public static void saveOverseaUserInfo(String userInfo) {
        SharedPreferencesUtils.putString(over_user_Info, userInfo);
    }

    public static boolean isLogin() {
        UserInfoBean userBean = getUserInfo();
        return userBean.isLogin();
    }

    public static void logOut() {
        SharedPreferencesUtils.remove(user_Info);
    }


    public static void setVersionInfo(String info){
        SharedPreferencesUtils.putString(version_info, info);
    }

    public static VersionBean getVersionInfo(){
        String versionInfo = SharedPreferencesUtils.getString(version_info, null);
        if (TextUtils.isEmpty(versionInfo)) {
            return new VersionBean();
        }
        return JSON.parseObject(versionInfo, VersionBean.class);
    }

    public static void setFirstOpen(boolean open){
        SharedPreferencesUtils.putBoolean(first_open, open);
    }

    public static boolean getFirstOpen(){
        return SharedPreferencesUtils.getBoolean(first_open, true);
    }

    public static void setToken(String token){
        SharedPreferencesUtils.putString(tokenKey, token);
    }

    public static String getToken(){
        return SharedPreferencesUtils.getString(tokenKey, "");
    }
}
