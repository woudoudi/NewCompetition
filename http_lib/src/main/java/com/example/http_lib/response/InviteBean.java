package com.example.http_lib.response;

import java.io.Serializable;

public class InviteBean implements Serializable {

    /**
     * nickInviter : 一
     * receiver : 87508980111554551
     * fightStatus : RECEIVED
     * updateAt : 2020-03-26 13:49:15
     * createAt : 2020-03-26 13:49:15
     * fscoreA : 12
     * fscoreB : 128
     * phoneInviter : 18258037659
     * inviter : 95504951935256647
     * id : 51412219237387450
     * status : 0
     */

    private String nickInviter;
    private String receiver;
    private String fightStatus;
    private String updateAt;
    private String createAt;
    private int fscoreA;
    private int fscoreB;
    private String phoneInviter;
    private String inviter;
    private String id;
    private int status;

    public String getNickInviter() {
        return nickInviter;
    }

    public void setNickInviter(String nickInviter) {
        this.nickInviter = nickInviter;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getFightStatus() {
        return fightStatus;
    }

    public void setFightStatus(String fightStatus) {
        this.fightStatus = fightStatus;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getFscoreA() {
        return fscoreA;
    }

    public void setFscoreA(int fscoreA) {
        this.fscoreA = fscoreA;
    }

    public int getFscoreB() {
        return fscoreB;
    }

    public void setFscoreB(int fscoreB) {
        this.fscoreB = fscoreB;
    }

    public String getPhoneInviter() {
        return phoneInviter;
    }

    public void setPhoneInviter(String phoneInviter) {
        this.phoneInviter = phoneInviter;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
