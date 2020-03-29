package com.yidao.module_lib.base.ipress;

import com.yidao.module_lib.base.ibase.IBasePress;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/7
 */
public interface ISettingPress extends IBasePress {

    void signUp();

    void fillSignUp(String dailySignId,String day);

    void checkSignUp();

    void getInvitationCode();

    void getInvitationList(long userId,int pageIndex);

    void getSetting(long userId);

    void updateSetting(long settingId,int pushStatus,int likeStatus,int commentStatus,int attentionStatus,int atStatus,int privateLetterStatus,int focusWorkStatus);

    void getRuleUrl(int ruleConfigId);

    void getPoster();
}
