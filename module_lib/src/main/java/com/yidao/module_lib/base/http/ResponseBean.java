package com.yidao.module_lib.base.http;


/**
 * Created by xiaochan on 2017/6/26.
 */

public class ResponseBean {

    private Integer retCode;

    private String retMsg;

    private boolean succeed;

    private String data;

    private String listData;

    private String rows;

    private Class mClass;

    private Object carry;

    private String enc;

    private boolean hasMore;

    public Integer getRetCode() {
        return retCode;
    }

    public void setRetCode(Integer retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isSucceed() {
        return succeed || retCode == 0;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getData() {
        return data;
    }

    public Class getRequestClass() {
        return mClass;
    }

    public void setRequestClass(Class aClass) {
        mClass = aClass;
    }

    public Object getCarry() {
        return carry;
    }

    public void setCarry(Object carry) {
        this.carry = carry;
    }

    public String getEnc() {
        return enc;
    }

    public void setEnc(String enc) {
        this.enc = enc;
    }

    public String getListData() {
        return listData;
    }

    public void setListData(String listData) {
        this.listData = listData;
    }

    public String getRows() {
        return rows;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + retCode +
                ", msg='" + retMsg + '\'' +
                ", succeed='" + succeed + '\'' +
                ", mClass='" + mClass + '\'' +
                ", data='" + data + '\'' +
                ", listData='" + listData + '\'' +
                ", rows='" + rows + '\'' +
                ", hasMore='" + hasMore + '\'' +
                ", enc='" + enc + '\'' +
                ", carry='" + carry + '\'' +
                '}';
    }
}
