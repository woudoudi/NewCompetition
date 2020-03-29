package com.yidao.module_lib.entity;

public class EventBusBean {


    public static int JumpToBetFragment = 0x11;

    public int type;
    public int tableType;
    public String content;

    public EventBusBean(int type) {
        this.type = type;
    }

    public EventBusBean setTableType(int tableType) {
        this.tableType = tableType;
        return this;
    }

    public EventBusBean setContent(String content) {
        this.content = content;
        return this;
    }
}
