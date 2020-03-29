package com.yidao.module_lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/27
 */
public class CommonUtils {

    public static boolean isPhone(String phone) {
        if(TextUtils.isEmpty(phone)){
            return false;
        }
        String regex = "^(1[1-9])\\d{9}$";
        if (phone.length() != 11) {
            System.out.println("手机号应为11位数");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            if (!isMatch) {
                System.out.println("请填入正确的手机号");
            }
            return isMatch;
        }
    }

    // 取得版本号
    public static String getVersionName(Context context) {
        try {
            PackageInfo manager = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return manager.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "0";
        }
    }

    private static long mTime;

    public static boolean fastClick(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - mTime < 1000){
            return true;
        }
        mTime = currentTime;
        return false;
    }

    public static String NumPointTwo(double num){
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return decimalFormat.format(num);
    }

}
