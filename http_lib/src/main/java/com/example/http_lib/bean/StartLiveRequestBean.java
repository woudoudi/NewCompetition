package com.example.http_lib.bean;

import com.example.http_lib.annotation.RequestrAnnotation;
import com.example.http_lib.enums.RequestMethodEnum;
import com.yidao.module_lib.base.BaseBean;
import com.yidao.module_lib.base.http.ILocalHost;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2019/8/15
 */
@RequestrAnnotation(baseUrl = ILocalHost.ycUrl,path = ILocalHost.startLive,method = RequestMethodEnum.POST)
public class StartLiveRequestBean extends BaseBean implements ILocalHost {


    public String title;   //名称

    public String goodHero;    //英雄

    public Integer winPercent; //胜率

    public String cover;   //封面

    public String messageBoard;    //专属公告栏

    public Boolean lastWin;    //上场是否得胜

    public String lastHeadRatio;   //人头对比如 200:2

    //直播相关
    public String endAt;  //时间戳(结束时间)

    public String pushURL; //推流地址

    public String pullURL; //拉流地址


}
