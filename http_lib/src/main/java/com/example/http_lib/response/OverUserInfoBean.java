package com.example.http_lib.response;

import java.util.List;

public class OverUserInfoBean {


    /**
     * memberTitle : 普通用户
     * updateAt : 2020-03-24 19:54:30
     * avatar : https://cdn.pixabay.com/photo/2020/03/22/16/18/bread-4957679_960_720.jpg
     * parentId : 14746239807998103
     * createAt : 2020-03-08 15:12:48
     * children : []
     * member : 8
     * id : 54079843798662403
     * username : 15757829477
     * status : 0
     */

    private String memberTitle;
    private String updateAt;
    private String avatar;
    private String parentId;
    private String createAt;
    private int member;
    private String id;
    private String username;
    private int status;
    private List<?> children;

    private String nickname;

    public String getMemberTitle() {
        return memberTitle;
    }

    public void setMemberTitle(String memberTitle) {
        this.memberTitle = memberTitle;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<?> getChildren() {
        return children;
    }

    public void setChildren(List<?> children) {
        this.children = children;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
