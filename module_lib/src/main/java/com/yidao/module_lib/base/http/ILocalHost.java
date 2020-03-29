package com.yidao.module_lib.base.http;

/**
 * Created by dream on 2017/7/20.
 * 请求地址的配置
 */

public interface ILocalHost {

    String getToken ="open/login/token/debug"; //获取token

    String login ="open/login/login/normal"; //登录

    String register = "open/login/register/normal"; //注册

    String getSendCode = "open/login/sms_code"; //获取验证码

    String bindPhone = "api/user/bindPhone"; //绑定手机号

    String updatePwd = "api/user/modifyPass"; //修改密码

    String getUserInfo = "api/user/info"; //获取用户信息

    String wechatLogin = "open/login/login/wechat"; //微信登录

    String getIntegralInfo = "api/user/account"; //积分信息

    String getInviteList = "api/user/invite/children"; //邀请列表

    String getIntegralRecord = "api/scoreLog/page"; //积分记录

    String uploadFile = "common/file/upload"; //文件上传



    String createChatGroup = "api/chatGroup"; //创建群

    String getGroupInfo = "api/chatGroup/{id}"; //读取群信息

    String joinGroupById = "api/chatGroup/{id}/join"; //加入群

    String dissolveGroupById = "api/chatGroup/{id}/dissolve"; //解散群

    String dropGroupById = "api/chatGroup/{id}/drop"; //退出群

    String kickUserGroupById = "api/chatGroup/{groupId}/user/{userId}/kick"; //把人踢出群

    String signMessageReaded = "/api/chatMessage/{id}/read"; //标记已读



    String websocketRoute = "/ws/Message?token="; //建立连接 路径



    String getKingFightList = "api/kingFight/page"; //获取对抗列表

    String sendKingFight = "api/kingFight"; //发出对抗邀请

    String receiveKingFight = "api/kingFight/{id}/join"; //接收对抗邀请

    String getKingLevel = "api/kingLevel/all"; //获取等级列表

    String dispute = "api/kingFight/{id}/dispute"; //仲裁

    String confirmResult = "api/kingFight/{id}/confirmResult"; //确认放分




    String startLive = "api/live"; //开始直播

    String updateLiveInfo = "api/live/{id}"; //更新直播信息



    String getRoomType = "api/numberRoom/field/level/enum/map"; //获取房间type

    String getRoomKind = "api/numberRoom/field/kind/enum/map"; //获取房间kind

    String getNumberLevel = "api/numberLevel/all"; //获取数字等级列表

    String getRoomList = "api/numberRoom/page"; //获取房间列表

    String enterRoom = "api/numberRoom/enterRoom"; //进入房间

    String startRoom = "api/numberRoom/start"; //开始房间

    String prepareRoom = "api/numberRoom/prepare"; //准备房间

    String leaveRoom = "api/numberRoom/leaveRoom"; //离开房间

    String getPersonRoleDetail = "api/numberRoomEnter/field/role/enum/map"; //进入房间的身份

    String getRoomDetail = "api/numberRoom/{roomId}"; //获取房间详情

    String getBoardDetail = "api/roomBoard/{boardId}"; //获取局详情

    String numberBet = "api/betHistory/board/{boardId}/sense/{senseId}"; //数字竞猜回答

    String getBetHistory = "api/betHistory/sense/{senseId}/history"; //每一题的历史

    String getBoardTopList = "api/betHistory/board/{boardId}/topList"; //局的排行榜

    String betNumberType = "api/betHistory/field/betType/enum/map"; //获取数字竞猜押注类型

    String betKindType = "api/betHistory/field/kind/enum/map"; //获取王者竞猜押注类型



    String getGiftList = "api/gift/page"; //礼物列表

    String getGiftTypeList = "api/giftHistory/kind/enum/map"; //送礼物种类的列表

    String sendGift = "api/giftHistory"; //送礼物接口

    String receiveGift = "api/giftHistory/page"; //送礼物接口




    String qrCodeTransition = "open/tool/qr";   //二维码转换

    String getKindRecord = "api/betHistory/page";   //王者直播押注记录 和 非王者直播押注记录









    /**
     * 线上地址
     */

    String ycUrl = "http://yc.leafabs.faith:8888/";
}
