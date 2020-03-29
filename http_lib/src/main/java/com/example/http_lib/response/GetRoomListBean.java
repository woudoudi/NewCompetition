package com.example.http_lib.response;

import java.io.Serializable;
import java.util.List;

public class GetRoomListBean implements Serializable {


    /**
     * id : 83942944947062478
     * status : 0
     * createAt : 2020-03-27 09:45:35
     * updateAt : 2020-03-27 09:45:35
     * kind : NUMBER
     * level : PRI
     * roomStatus : CREATED
     * noticeBoard : 数字竞猜公告
     * personZhu : 2
     * personKe : 0
     * boardList : []
     * personPang : 0
     * beginTime : null
     * endTime : null
     */

    private String id;
    private int status;
    private String createAt;
    private String updateAt;
    private String kind;
    private String level;
    private String roomStatus;
    private String noticeBoard;
    private int personZhu;
    private int personKe;
    private int personPang;
    private Object beginTime;
    private Object endTime;
    private List<?> boardList;

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

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getNoticeBoard() {
        return noticeBoard;
    }

    public void setNoticeBoard(String noticeBoard) {
        this.noticeBoard = noticeBoard;
    }

    public int getPersonZhu() {
        return personZhu;
    }

    public void setPersonZhu(int personZhu) {
        this.personZhu = personZhu;
    }

    public int getPersonKe() {
        return personKe;
    }

    public void setPersonKe(int personKe) {
        this.personKe = personKe;
    }

    public int getPersonPang() {
        return personPang;
    }

    public void setPersonPang(int personPang) {
        this.personPang = personPang;
    }

    public Object getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Object beginTime) {
        this.beginTime = beginTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public List<?> getBoardList() {
        return boardList;
    }

    public void setBoardList(List<?> boardList) {
        this.boardList = boardList;
    }
}
