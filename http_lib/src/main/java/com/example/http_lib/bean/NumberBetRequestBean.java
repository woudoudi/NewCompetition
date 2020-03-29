package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/12
 */

@RequestrAnnotation(baseUrl = ILocalHost.ycUrl, path = ILocalHost.numberBet,method = RequestMethodEnum.POST)
public class NumberBetRequestBean extends BaseBean implements ILocalHost {

    public String kind ="JINGCAIZHU";

    public String betType;

    public String betInput;

    public Double betScore ;


}
