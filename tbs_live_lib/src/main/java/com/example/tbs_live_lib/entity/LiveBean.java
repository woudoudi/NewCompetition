package com.example.tbs_live_lib.entity;

import java.io.Serializable;

public class LiveBean implements Serializable {


    /**
     * messageBoard : 今晚打老虎
     * updateAt : 2020-03-25 14:20:41
     * title : 王者荣耀
     * userId : 42019508774718967
     * goodHero : 妖姬
     * createAt : 2020-03-25 14:20:41
     * pullURL : rtmp://videoplay.jchm99.com/live/16347329907899101
     * cover : https://cdn.pixabay.com/photo/2020/03/21/19/27/sea-4955005_960_720.jpg
     * pushURL : rtmp://87962.livepush.myqcloud.com/live/16347329907899101?txSecret=1515660df645d12ab7cae0afa2832f30&txTime=5E7C1179
     * lastWin : true
     * lastHeadRatio : 10:3
     * id : 16347329907899101
     * winPercent : 60
     * status : 0
     */

    private String messageBoard;
    private String updateAt;
    private String title;
    private String userId;
    private String goodHero;
    private String createAt;
    private String pullURL;
    private String cover;
    private String pushURL;
    private boolean lastWin;
    private String lastHeadRatio;
    private String id;
    private int winPercent;
    private int status;

    public String getMessageBoard() {
        return messageBoard;
    }

    public void setMessageBoard(String messageBoard) {
        this.messageBoard = messageBoard;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGoodHero() {
        return goodHero;
    }

    public void setGoodHero(String goodHero) {
        this.goodHero = goodHero;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getPullURL() {
        return pullURL;
    }

    public void setPullURL(String pullURL) {
        this.pullURL = pullURL;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getPushURL() {
        return pushURL;
    }

    public void setPushURL(String pushURL) {
        this.pushURL = pushURL;
    }

    public boolean isLastWin() {
        return lastWin;
    }

    public void setLastWin(boolean lastWin) {
        this.lastWin = lastWin;
    }

    public String getLastHeadRatio() {
        return lastHeadRatio;
    }

    public void setLastHeadRatio(String lastHeadRatio) {
        this.lastHeadRatio = lastHeadRatio;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWinPercent() {
        return winPercent;
    }

    public void setWinPercent(int winPercent) {
        this.winPercent = winPercent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
