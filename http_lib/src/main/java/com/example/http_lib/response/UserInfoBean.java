package com.example.http_lib.response;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */
public class UserInfoBean {

    private String userName;
    private String password;
    private boolean isLogin;
    private String wechatOpenId;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getWechatOpenId() {
        return wechatOpenId;
    }

    public void setWechatOpenId(String wechatOpenId) {
        this.wechatOpenId = wechatOpenId;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isLogin=" + isLogin +
                '}';
    }
}
