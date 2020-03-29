package com.example.administrator.competition.mvp.presenter;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.administrator.competition.activity.guess.GuessCompetitionActivity;
import com.example.administrator.competition.activity.login.LoginUserFirstActivity;
import com.example.administrator.competition.application.ComApplication;
import com.example.administrator.competition.entity.CommonConfig;
import com.example.http_lib.response.GetRoomListBean;
import com.example.http_lib.response.UserInfoBean;
import com.example.http_lib.utils.UserCacheHelper;
import com.qgc.user.live.wxapi.WXEntryActivity;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.LogUtils;

import java.io.File;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.application.JGApplication;
import jiguang.chat.database.UserEntry;
import jiguang.chat.utils.SharePreferenceManager;


/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/28
 */
public class JCLoginPress {

    public static void JMessageLogin() {
        UserInfoBean userInfoBean = UserCacheHelper.getUserInfo();
        if (userInfoBean == null) {
            return;
        }
        final String phone = userInfoBean.getUserName();
        final String password = "qm_" + phone;

        SharePreferenceManager.setCachedUsername(phone);
        SharePreferenceManager.setCachedPsw(password);

        JMessageClient.login(phone, password, new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {

                    SharePreferenceManager.setCachedUsername(phone);
                    SharePreferenceManager.setCachedPsw(password);

                    UserInfo myInfo = JMessageClient.getMyInfo();
                    File avatarFile = myInfo.getAvatarFile();
                    //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                    if (avatarFile != null) {
                        SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                    } else {
                        SharePreferenceManager.setCachedAvatarPath(null);
                    }
                    String username = myInfo.getUserName();
                    String appKey = myInfo.getAppKey();
                    UserEntry user = UserEntry.getUser(username, appKey);
                    if (null == user) {
                        user = new UserEntry(username, appKey);
                        user.save();
                    }
                    LogUtils.d("JM-登陆成功");
                } else if (responseCode == 801003) {
                    JMessageClient.register(phone, password, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                JMessageClient.login(phone, password, new BasicCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage) {
                                        if (responseCode == 0) {
                                            LogUtils.d("JM-登陆成功");
                                        } else {
                                            LogUtils.d("JM-登陆失败" + responseMessage);
                                        }
                                    }
                                });
                            }
                        }
                    });
                    LogUtils.d("JM-登陆失败" + responseMessage);
                }
            }
        });

        registerCustomService();

        registerDefaultUser();
    }

    public static void jumpChatActivity(String targetId, String title) {
        Intent intent = new Intent(ComApplication.getApplication(), ChatActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(JGApplication.TARGET_ID, targetId);
        intent.putExtra(JGApplication.CONV_TITLE, title);
        intent.putExtra(JGApplication.TARGET_APP_KEY, CommonConfig.Jpush_IM_Key);
        intent.putExtra(JGApplication.DRAFT, "");
        ComApplication.getApplication().startActivity(intent);
    }

    public static void jumpGroupChatActivity(long groupId, int guessGradeValue,GetRoomListBean roomListBean) {
        Intent intent = new Intent(ComApplication.getApplication(), GuessCompetitionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(JGApplication.GROUP_ID, groupId);

        intent.putExtra(JGApplication.TARGET_APP_KEY, CommonConfig.Jpush_IM_Key);
        intent.putExtra(JGApplication.DRAFT, "");
        intent.putExtra(CommonConfig.guessGradeKey, guessGradeValue);
        intent.putExtra(CommonConfig.guessRoomKey,roomListBean);
        ComApplication.getApplication().startActivity(intent);
    }

    public static void logout(Context context) {
        WXEntryActivity.setIsLogin(true);
        UserCacheHelper.logOut();
        SharePreferenceManager.setCachedUsername("");
        JMessageClient.logout();
        ViewManager.getInstance().finishAllView();
        context.startActivity(new Intent(context, LoginUserFirstActivity.class));
    }


    /**
     * 创建客服账号
     */
    private static void registerCustomService(){
        JMessageClient.getUserInfo(CommonConfig.Service, CommonConfig.Jpush_IM_Key, new GetUserInfoCallback() {
            @Override
            public void gotResult(int code, String s, UserInfo userInfo) {
                LogUtils.d("JM客服 code:"+code+" , " + s);
                if(code == 898002){
                    JMessageClient.register(CommonConfig.DefaultUser, "123456", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                LogUtils.d("JM客服-注册成功");
                            } else {
                                LogUtils.d("JM客服 code:"+responseCode+" , " + responseMessage);
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 创建默认账户，用于竞猜创建群组
     */
    private static void registerDefaultUser(){
        JMessageClient.getUserInfo(CommonConfig.DefaultUser, CommonConfig.Jpush_IM_Key, new GetUserInfoCallback() {
            @Override
            public void gotResult(int code, String s, UserInfo userInfo) {
                LogUtils.d("JM默认账户 code:"+code+" , " + s);
                if(code == 898002){
                    JMessageClient.register(CommonConfig.DefaultUser, "123456", new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0) {
                                LogUtils.d("JM默认账户-注册成功");
                            } else {
                                LogUtils.d("JM默认账户 code:"+responseCode+" , " + responseMessage);
                            }
                        }
                    });
                }
            }
        });
    }

}
