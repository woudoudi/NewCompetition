package com.example.administrator.competition.mvp.presenter;

import com.example.administrator.competition.mvp.model.CommomModel;
import com.example.http_lib.bean.BetKindTypeRequestBean;
import com.example.http_lib.bean.BetNumberTypeRequestBean;
import com.example.http_lib.bean.BindPhoneRequestBean;
import com.example.http_lib.bean.ConfirmResultRequestBean;
import com.example.http_lib.bean.DisputeRequestBean;
import com.example.http_lib.bean.EnterRoomRequestBean;
import com.example.http_lib.bean.GetBetHistoryListRequestBean;
import com.example.http_lib.bean.GetBoardDetailRequestBean;
import com.example.http_lib.bean.GetBordTopListRequestBean;
import com.example.http_lib.bean.GetGiftListRequestBean;
import com.example.http_lib.bean.GetGiftTypeListRequestBean;
import com.example.http_lib.bean.GetIntegralInfoRequestBean;
import com.example.http_lib.bean.GetIntegralRecordRequestBean;
import com.example.http_lib.bean.GetInviteListRequestBean;
import com.example.http_lib.bean.GetKindRecordRequestBean;
import com.example.http_lib.bean.GetKingFightListRequestBean;
import com.example.http_lib.bean.GetKingLevelListRequestBean;
import com.example.http_lib.bean.GetNumberLevelRequestBean;
import com.example.http_lib.bean.GetRoomDetailRequestBean;
import com.example.http_lib.bean.GetRoomKindRequestBean;
import com.example.http_lib.bean.GetRoomListRequestBean;
import com.example.http_lib.bean.GetRoomRoleRequestBean;
import com.example.http_lib.bean.GetRoomTypeRequestBean;
import com.example.http_lib.bean.GetTokenRequestBean;
import com.example.http_lib.bean.LeaveRoomRequestBean;
import com.example.http_lib.bean.LoginRequestBean;
import com.example.http_lib.bean.NumberBetRequestBean;
import com.example.http_lib.bean.PrepareRoomRequestBean;
import com.example.http_lib.bean.ReceiveKingFightRequestBean;
import com.example.http_lib.bean.RegisterRequestBean;
import com.example.http_lib.bean.SendCodeRequestBean;
import com.example.http_lib.bean.SendGiftRequestBean;
import com.example.http_lib.bean.SendKingFightRequestBean;
import com.example.http_lib.bean.StartRoomRequestBean;
import com.example.http_lib.bean.TripartiteLoginBean;
import com.example.http_lib.bean.UpdatePwdRequestBean;
import com.example.http_lib.bean.UpdateUserInfoRequestBean;
import com.example.http_lib.bean.UploadFileRequestBean;
import com.example.http_lib.bean.UserInfoRequestBean;
import com.yidao.module_lib.base.BasePressPlus;
import com.yidao.module_lib.base.ibase.IBaseView;

import java.io.File;

public class CommonPresenter extends BasePressPlus<IBaseView>{

    private CommomModel mCommomModel;

    public CommonPresenter(IBaseView view) {
        super(view);
        mCommomModel = new CommomModel(this);
    }

    /**
     * 获取token
     */
    public void getToken(){
        GetTokenRequestBean getTokenRequestBean = new GetTokenRequestBean();
        setRequst(getTokenRequestBean,mCommomModel);
    }

    /**
     * 发送验证码
     * @param phone
     */
    public void sendVCode(String phone){
        SendCodeRequestBean sendCodeRequestBean = new SendCodeRequestBean();
        sendCodeRequestBean.phone = phone;
        setRequst(sendCodeRequestBean,mCommomModel);
    }

    /**
     * 登录
     * @param userName
     * @param password
     */
    public void login(String userName,String password){
        LoginRequestBean loginRequestBean = new LoginRequestBean();
        loginRequestBean.username = userName;
        loginRequestBean.password = password;
        setRequst(loginRequestBean,mCommomModel);
    }


    /**
     * 注册
     * @param userName
     * @param password
     * @param code
     * @param qr
     */
    public void register(String userName,String password,String code,String qr){
        RegisterRequestBean registerRequestBean = new RegisterRequestBean();
        registerRequestBean.username = userName;
        registerRequestBean.phone = userName;
        registerRequestBean.password = password;
        registerRequestBean.code = code;
        registerRequestBean.qr = qr;
        setRequst(registerRequestBean,mCommomModel);
    }

    /**
     * 修改密码
     * @param userName
     * @param password
     * @param code
     */
    public void updatePwd(String userName,String password,String code){
        UpdatePwdRequestBean updatePwdRequestBean = new UpdatePwdRequestBean();
        updatePwdRequestBean.phone = userName;
        updatePwdRequestBean.pass = password;
        updatePwdRequestBean.code = code;
        setRequst(updatePwdRequestBean,mCommomModel);
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo(){
        UserInfoRequestBean userInfoRequestBean = new UserInfoRequestBean();
        setRequst(userInfoRequestBean,mCommomModel);
    }

    /**
     * 微信登录
     * @param
     * @param code
     */
    public void wechatLogin(String code){
        TripartiteLoginBean bindPhoneRequestBean = new TripartiteLoginBean();
        bindPhoneRequestBean.code = code;
        setRequst(bindPhoneRequestBean,mCommomModel);
    }

    /**
     * 第三方登录绑定手机号操作
     * @param userName
     * @param code
     */
    public void bindPhone(String userName,String code){
        BindPhoneRequestBean bindPhoneRequestBean = new BindPhoneRequestBean();
        bindPhoneRequestBean.phone = userName;
        bindPhoneRequestBean.code = code;
        setRequst(bindPhoneRequestBean,mCommomModel);
    }

    /**
     * 上传文件
     */
    public void uploadFile(File file){
        UploadFileRequestBean requestBean = new UploadFileRequestBean();
        requestBean.file=file;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 更新用户信息
     */
    public void updateUserInfol(UpdateUserInfoRequestBean requestBean){
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取积分详情
     */
    public void getIntegralInfo(){
        GetIntegralInfoRequestBean integralInfoRequestBean = new GetIntegralInfoRequestBean();
        setRequst(integralInfoRequestBean,mCommomModel);
    }

    /**
     * 获取邀请列表
     */
    public void getInviteList(){
        GetInviteListRequestBean integralInfoRequestBean = new GetInviteListRequestBean();
        setRequst(integralInfoRequestBean,mCommomModel);
    }


    /**
     * 获取积分记录
     */
    public void getIntegralRecord(int page){
        GetIntegralRecordRequestBean integralInfoRequestBean = new GetIntegralRecordRequestBean();
        integralInfoRequestBean.page = page;
        setRequst(integralInfoRequestBean,mCommomModel);
    }


    /**
     * 获取对抗列表
     */
    public void getKingFightList(int page){
        GetKingFightListRequestBean integralInfoRequestBean = new GetKingFightListRequestBean();
        integralInfoRequestBean.page = page;
        setRequst(integralInfoRequestBean,mCommomModel);
    }

    /**
     * 发起对抗
     * @param requestBean
     */
    public void startKingFight(SendKingFightRequestBean requestBean){
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 接受对抗
     * @param id
     */
    public void acceptKingFight(String id,ReceiveKingFightRequestBean requestBean){
        requestBean.strBefore = new String[]{"{id}"};
        requestBean.strAfter = new String[]{id};
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取等级列表
     *
     */
    public void getLevelList(){
        GetKingLevelListRequestBean requestBean = new GetKingLevelListRequestBean();
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取数字等级列表
     *
     */
    public void getNumberLevel(String level){
        GetNumberLevelRequestBean requestBean = new GetNumberLevelRequestBean();
        requestBean.level = level;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 进行仲裁
     *
     */
    public void dispute(String id){
        DisputeRequestBean requestBean = new DisputeRequestBean();
        requestBean.strBefore= new String[]{"{id}"};
        requestBean.strAfter= new String[]{id};
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 确认放分
     *
     */
    public void confirmResult(String id,boolean win){
        ConfirmResultRequestBean requestBean = new ConfirmResultRequestBean();
        requestBean.strBefore= new String[]{"{id}"};
        requestBean.strAfter= new String[]{id};
        requestBean.win = win;
        setRequst(requestBean,mCommomModel);
    }



    /**
     * 获取房间type
     *
     */
    public void getRoomType(){
        GetRoomTypeRequestBean requestBean = new GetRoomTypeRequestBean();
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取房间kind
     *
     */
    public void getRoomKind(){
        GetRoomKindRequestBean requestBean = new GetRoomKindRequestBean();
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取房间列表
     *
     */
    public void getRoomList(String kind,String level,int page){
        GetRoomListRequestBean requestBean = new GetRoomListRequestBean();
        requestBean.kind = kind;
        requestBean.level = level;
        requestBean.page = page;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 进入房间
     *
     */
    public void enterRoom(String id){
        EnterRoomRequestBean requestBean = new EnterRoomRequestBean();
        requestBean.id = id;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取自己的房间角色
     *
     */
    public void getRoomRoleDetail(){
        GetRoomRoleRequestBean requestBean = new GetRoomRoleRequestBean();
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 开始房间
     *
     */
    public void startRoom(String id){
        StartRoomRequestBean requestBean = new StartRoomRequestBean();
        requestBean.id = id;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 准备房间
     *
     */
    public void prepareRoom(String id){
        PrepareRoomRequestBean requestBean = new PrepareRoomRequestBean();
        requestBean.id = id;
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 离开房间
     *
     */
    public void leaveRoom(String id){
        LeaveRoomRequestBean requestBean = new LeaveRoomRequestBean();
        requestBean.id = id;
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取数字竞猜押注类型
     *
     */
    public void getBetNumberType(){
        BetNumberTypeRequestBean requestBean= new BetNumberTypeRequestBean();
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取王者竞猜押注类型
     *
     */
    public void getBetKindType(){
        BetKindTypeRequestBean requestBean= new BetKindTypeRequestBean();
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 数字竞猜回答
     *
     */
    public void numberBet(String boardId,String senseId,NumberBetRequestBean requestBean){
        requestBean.strBefore = new String[]{"{boardId}","{senseId}"};
        requestBean.strBefore = new String[]{boardId,senseId};
        setRequst(requestBean,mCommomModel);
    }

    /**
     * 获取礼物列表
     *
     */
    public void getGiftList(){
        GetGiftListRequestBean requestBean = new GetGiftListRequestBean();
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取送礼物种类的列表
     *
     */
    public void getGiftListType(){
        GetGiftTypeListRequestBean requestBean = new GetGiftTypeListRequestBean();
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 送礼物
     *
     */
    public void sendGift(String id){
        SendGiftRequestBean requestBean = new SendGiftRequestBean();
        requestBean.id = id;
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取房间详情
     *
     */
    public void getRoomDetail(String roomId){
        GetRoomDetailRequestBean requestBean = new GetRoomDetailRequestBean();
        requestBean.strBefore = new String[]{"{roomId}"};
        requestBean.strAfter = new String[]{roomId};
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取局详情
     *
     */
    public void getBoardDetail(String boardId){
        GetBoardDetailRequestBean requestBean = new GetBoardDetailRequestBean();
        requestBean.strBefore = new String[]{"{boardId}"};
        requestBean.strAfter = new String[]{boardId};
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取每一题的历史排名
     *
     */
    public void getQuestionTopList(String senseId){
        GetBetHistoryListRequestBean requestBean = new GetBetHistoryListRequestBean();
        requestBean.strBefore = new String[]{"{senseId}"};
        requestBean.strAfter = new String[]{senseId};
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取局排名列表
     *
     */
    public void getBordTopList(){
        GetBordTopListRequestBean requestBean = new GetBordTopListRequestBean();
        setRequst(requestBean,mCommomModel);
    }


    /**
     * 获取王者直播押注记录 和 非王者直播押注记录
     *
     */
    public void getKindRecord(String kind,int page){
        GetKindRecordRequestBean requestBean = new GetKindRecordRequestBean();
        requestBean.kind = kind;
        requestBean.page = page;
        setRequst(requestBean,mCommomModel);
    }

}
