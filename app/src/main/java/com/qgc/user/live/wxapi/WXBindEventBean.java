package com.qgc.user.live.wxapi;

import com.example.http_lib.bean.TripartiteLoginBean;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/10/31
 */
public class WXBindEventBean {

    private boolean isBind;
    private TripartiteLoginBean tripartiteLoginBean;
    public WXBindEventBean(boolean isBind, TripartiteLoginBean tripartiteLoginBean) {
        this.isBind = isBind;
        this.tripartiteLoginBean = tripartiteLoginBean;
    }


    public TripartiteLoginBean getTripartiteLoginBean() {
        return tripartiteLoginBean;
    }

    public void setTripartiteLoginBean(TripartiteLoginBean tripartiteLoginBean) {
        this.tripartiteLoginBean = tripartiteLoginBean;
    }

    public boolean isBind() {
        return isBind;
    }

    public void setBind(boolean bind) {
        isBind = bind;
    }
}
