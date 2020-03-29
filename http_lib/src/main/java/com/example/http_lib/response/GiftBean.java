package com.example.http_lib.response;

public class GiftBean {

    /**
     * cover : http://yc.leafabs.faith:8888/preview/2020/03/image/19053832960099252.png
     * scorePrice : 100
     * name : 火车
     * updateAt : 2020-03-27 15:07:17
     * createAt : 2020-03-27 15:07:17
     * status : 0
     */
    private String id;
    private boolean isSecelet;
    private String cover;
    private int scorePrice;
    private String name;
    private String updateAt;
    private String createAt;
    private int status;

    public boolean isSecelet() {
        return isSecelet;
    }

    public void setSecelet(boolean secelet) {
        isSecelet = secelet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getScorePrice() {
        return scorePrice;
    }

    public void setScorePrice(int scorePrice) {
        this.scorePrice = scorePrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
