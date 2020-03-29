package com.example.http_lib.enums;

import android.text.TextUtils;

public enum ParamEnum {

    /**
     * 剧目题材
     */
    QUANBU(0, "全部"),
    XIJU(1,"喜剧"),
    BEIJU(2, "悲剧"),
    AIQING(3, "爱情"),
    DONGZUO(4, "动作"),
    QIANGZHAN(5, "枪战"),
    FANZUI(6, "犯罪"),
    JINGSONG(7, "惊悚"),
    KONGBU(8, "恐怖"),
    XUANYI(9, "悬疑"),
    DONGHUA(10, "动画"),
    JIATING(11, "家庭"),
    QIHUAN(12, "奇幻"),
    MOHUAN(13, "魔幻"),
    KEHUAN(14, "科幻"),
    ZHANZHENG(15, "战争"),
    QINGCHUN(16, "青春"),
    WENYI(17, "文艺"),
    DIANZIJINGJI(18, "电子竞技");

    private Integer code;
    private String name;

     ParamEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据code找到指定的枚举值
     * @param code
     * @return
     */
    public static ParamEnum parseEnum(Integer code) {
        for (ParamEnum paramEnum : ParamEnum.values()) {
            if (paramEnum.getCode().equals(code)) {
                return paramEnum;
            }
        }
        return QUANBU;
    }

    /**
     * 根据name找到指定的枚举值
     * @param name
     * @return
     */
    public static ParamEnum parseEnum(String name){

        if (TextUtils.isEmpty(name)){
            throw new NullPointerException("传递的题材名字为空");
        }
        for (ParamEnum paramEnum : ParamEnum.values()) {
            if (paramEnum.getName().equals(name)){
                return paramEnum;
            }
        }
        return QUANBU;
    }

}
