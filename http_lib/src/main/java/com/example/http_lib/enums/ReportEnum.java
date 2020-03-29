package com.example.http_lib.enums;

/**
 * Created by chenhe_han on 2017/10/14.
 * 公司：杭州宸禾网络科技有限公司
 * 描述：
 */

public enum ReportEnum {

    REPORT_VIDEO(1, "举报视频"), REPORT_USER(4, "举报用户"),
    REPORT_LIVE_ROOM(3, "举报直播间"), REPORT_COMMENT(2, "举报评论"),
    REPORT_DYNAMIC(5, "举报动态"), REPORT_TOPIC(6, "举报话题");

    ReportEnum(int type, String name) {
        this.type = type;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    private int type;
    private String name;

    public static ReportEnum parseEnum(int type) {
        for (ReportEnum anEnum : ReportEnum.values()) {
            if (anEnum.getType() == type) {
                return anEnum;
            }
        }
        throw new IllegalArgumentException("使用举报枚举的姿势不正确, 错误的type =\t" + type);
    }
}
