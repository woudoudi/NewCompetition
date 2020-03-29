package jiguang.chat.activity.fragment;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Toast;

import com.sj.emoji.EmojiBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.content.EventNotificationContent;
import cn.jpush.im.android.api.content.FileContent;
import cn.jpush.im.android.api.content.LocationContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.enums.MessageDirect;
import cn.jpush.im.android.api.event.ChatRoomMessageEvent;
import cn.jpush.im.android.api.event.ChatRoomNotificationEvent;
import cn.jpush.im.android.api.event.CommandNotificationEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.MessageReceiptStatusChangeEvent;
import cn.jpush.im.android.api.event.MessageRetractEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import jiguang.chat.R;
import jiguang.chat.activity.ChatActivity;
import jiguang.chat.activity.ForwardMsgActivity;
import jiguang.chat.adapter.ChattingListAdapter;
import jiguang.chat.application.JGApplication;
import jiguang.chat.model.Constants;
import jiguang.chat.pickerimage.utils.RequestCode;
import jiguang.chat.utils.CommonUtils;
import jiguang.chat.utils.IdHelper;
import jiguang.chat.utils.SharePreferenceManager;
import jiguang.chat.utils.SimpleCommonUtils;
import jiguang.chat.utils.ToastUtil;
import jiguang.chat.utils.imagepicker.bean.ImageItem;
import jiguang.chat.utils.keyboard.XhsEmoticonsKeyBoard;
import jiguang.chat.utils.keyboard.data.EmoticonEntity;
import jiguang.chat.utils.keyboard.interfaces.EmoticonClickListener;
import jiguang.chat.utils.keyboard.widget.FuncLayout;
import jiguang.chat.utils.photovideo.takevideo.utils.LogUtils;
import jiguang.chat.view.ChatView;
import jiguang.chat.view.SendDocumentView;
import jiguang.chat.view.SimpleAppsGridView;
import jiguang.chat.view.TipItem;
import jiguang.chat.view.TipView;
import jiguang.chat.view.listview.DropDownListView;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends BaseFragment implements FuncLayout.OnFuncKeyBoardListener{

    DropDownListView lvChat;

    XhsEmoticonsKeyBoard ekBar;

    private String mTitle;
    private boolean mLongClick = false;

    private static final String MEMBERS_COUNT = "membersCount";
    private static final String GROUP_NAME = "groupName";

    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";
    private static final String DRAFT = "draft";
    private ChatView mChatView;
    private boolean mIsSingle = true;
    private Conversation mConv;
    private String mTargetId;
    private String mTargetAppKey;
    private Activity mContext;
    private ChattingListAdapter mChatAdapter;
    private List<UserInfo> mAtList;
    private long mGroupId;
    private static final int REFRESH_LAST_PAGE = 0x1023;
    private static final int REFRESH_CHAT_TITLE = 0x1024;
    private static final int REFRESH_GROUP_NAME = 0x1025;
    private static final int REFRESH_GROUP_NUM = 0x1026;

    private GroupInfo mGroupInfo;
    private UserInfo mMyInfo;
    private static final String GROUP_ID = "groupId";
    private int mAtMsgId;
    private int mAtAllMsgId;
    private int mUnreadMsgCnt;
    private List<UserInfo> forDel = new ArrayList<>();

    Window mWindow;
    InputMethodManager mImm;
    private final UIHandler mUIHandler = new UIHandler(this);
    private boolean isChatRoom = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View inflate = inflater.inflate(R.layout.activity_chat, container, false);
        return inflate;
    }

    public Conversation getConversation(){
        return mConv;
    }

    public ChatView getChatView(){
        return  mChatView;
    }

    public ChattingListAdapter getChattingListAdapter(){
        return mChatAdapter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        mContext = getActivity();

        mChatView = (ChatView) mContext.findViewById(R.id.chat_view);
        lvChat = (DropDownListView) getActivity().findViewById(R.id.lv_chat);
        ekBar = (XhsEmoticonsKeyBoard) mContext.findViewById(R.id.ek_bar);

        mContext.findViewById(R.id.jmui_menu_title_bar).setVisibility(View.GONE);

        mChatView.initModule(mDensity, mDensityDpi);

        this.mWindow = mContext.getWindow();
        this.mImm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

        initView();
        //来自聊天室

        initData();


        ekBar.setInputContainerVisiblity(false);
    }

    private void initView() {
        lvChat = (DropDownListView) mContext.findViewById(R.id.lv_chat);
        ekBar = (XhsEmoticonsKeyBoard) mContext.findViewById(R.id.ek_bar);
        initListView();

        ekBar.getEtChat().addTextChangedListener(new TextWatcher() {
            private CharSequence temp = "";

            @Override
            public void afterTextChanged(Editable arg0) {
                if (temp.length() > 0) {
                    mLongClick = false;
                }

                if (mAtList != null && mAtList.size() > 0) {
                    for (UserInfo info : mAtList) {
                        String name = info.getDisplayName();

                        if (!arg0.toString().contains("@" + name + " ")) {
                            forDel.add(info);
                        }
                    }
                    mAtList.removeAll(forDel);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        ekBar.getEtChat().setOnFocusChangeListener((v, hasFocus) -> {
            String content;
            if (hasFocus) {
                content = "{\"type\": \"input\",\"content\": {\"message\": \"对方正在输入\"}}";
            } else {
                content = "{\"type\": \"input\",\"content\": {\"message\": \"\"}}";
            }
            if (mIsSingle) {
                JMessageClient.sendSingleTransCommand(mTargetId, null, content, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {

                    }
                });
            }
        });

        mChatView.getChatListView().setOnTouchListener((v, event) -> {
            mChatView.getChatListView().setFocusable(true);
            mChatView.getChatListView().setFocusableInTouchMode(true);
            mChatView.getChatListView().requestFocus();
            CommonUtils.hideKeyboard(mContext);
            return false;
        });
    }


    private void initData() {
        SimpleCommonUtils.initEmoticonsEditText(ekBar.getEtChat());
        Intent intent = getActivity().getIntent();
        mTargetId = intent.getStringExtra(TARGET_ID);
        mTargetAppKey = intent.getStringExtra(TARGET_APP_KEY);
        mTitle = intent.getStringExtra(JGApplication.CONV_TITLE);
        mMyInfo = JMessageClient.getMyInfo();
        initEmoticonsKeyBoardBar();

        if (!TextUtils.isEmpty(mTargetId)) {
            //单聊
            mIsSingle = true;
            mChatView.setChatTitle(mTitle);
            mConv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
            if (mConv == null) {
                mConv = Conversation.createSingleConversation(mTargetId, mTargetAppKey);
            }
            if (mConv != null) {
                mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);
            } else {
                JMessageClient.login(SharePreferenceManager.getCachedUsername(), SharePreferenceManager.getCachedPsw(), new BasicCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage) {
                        if (responseCode == 0) {
                            mConv = JMessageClient.getSingleConversation(mTargetId, mTargetAppKey);
                            if (mConv == null) {
                                mConv = Conversation.createSingleConversation(mTargetId, mTargetAppKey);
                            }
                            if (mConv == null) {
                                ToastUtil.shortToast(mContext, "创建会话失败，请重新进入");
                                return;
                            }
                            mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);
                        } else {
                            ToastUtil.shortToast(mContext, "创建会话失败，请重新进入");
                        }
                    }
                });
            }
        } else {
            //群聊
            mIsSingle = false;
            mGroupId = intent.getLongExtra(GROUP_ID, 0);
            mTargetId = String.valueOf(mGroupId);
            final boolean fromGroup = intent.getBooleanExtra("fromGroup", false);
            if (fromGroup) {
                mChatView.setChatTitle(mTitle, intent.getIntExtra(MEMBERS_COUNT, 0));
                mConv = JMessageClient.getGroupConversation(mGroupId);
                mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);//长按聊天内容监听
            } else {
                mAtMsgId = intent.getIntExtra("atMsgId", -1);
                mAtAllMsgId = intent.getIntExtra("atAllMsgId", -1);
                mConv = JMessageClient.getGroupConversation(mGroupId);
                if (mConv != null) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(mMyInfo.getUserName(), mMyInfo.getAppKey());
                    //如果自己在群聊中，聊天标题显示群人数
                    if (userInfo != null) {
                        if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
                            mChatView.setChatTitle(mTitle, groupInfo.getGroupMembers().size());
                        } else {
                            mChatView.setChatTitle(mTitle, groupInfo.getGroupMembers().size());
                        }
                        mChatView.showRightBtn();
                    } else {
                        if (!TextUtils.isEmpty(mTitle)) {
                            mChatView.setChatTitle(mTitle);
                        } else {
                            mChatView.setChatTitle("群聊");
                        }
                        mChatView.dismissRightBtn();
                    }
                } else {
                    mConv = Conversation.createGroupConversation(mGroupId);
                }
                //更新群名
                JMessageClient.getGroupInfo(mGroupId, new GetGroupInfoCallback(false) {
                    @Override
                    public void gotResult(int status, String desc, GroupInfo groupInfo) {
                        if (status == 0) {
                            mGroupInfo = groupInfo;
                            mUIHandler.sendEmptyMessage(REFRESH_CHAT_TITLE);
                        }
                    }
                });
                if (mAtMsgId != -1) {
                    mUnreadMsgCnt = mConv.getUnReadMsgCnt();
                    // 如果 @我 的消息位于屏幕显示的消息之上，显示 有人@我 的按钮
                    if (mAtMsgId + 8 <= mConv.getLatestMessage().getId()) {
                        mChatView.showAtMeButton();
                    }
                    mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener, mAtMsgId);
                } else {
                    mChatAdapter = new ChattingListAdapter(mContext, mConv, longClickListener);
                }

            }
            //聊天信息标志改变
            mChatView.setGroupIcon();
        }

        String draft = intent.getStringExtra(DRAFT);
        if (draft != null && !TextUtils.isEmpty(draft)) {
            ekBar.getEtChat().setText(draft);
        }

        mChatView.setChatListAdapter(mChatAdapter);
        mChatView.getListView().setOnDropDownListener(new DropDownListView.OnDropDownListener() {
            @Override
            public void onDropDown() {
                mUIHandler.sendEmptyMessageDelayed(REFRESH_LAST_PAGE, 1000);
            }
        });
        mChatView.setToBottom();
        mChatView.setConversation(mConv);
    }


    private void initEmoticonsKeyBoardBar() {
        //切换语音输入
        ekBar.getVoiceOrText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.btn_voice_or_text) {
                    ekBar.setVideoText();
                    ekBar.getBtnVoice().initConv(mConv, mChatAdapter, mChatView);
                }
            }
        });
    }

    private void initListView() {
        lvChat.setAdapter(mChatAdapter);
        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        ekBar.reset();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }


    private void scrollToBottom() {
        lvChat.requestLayout();
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom());
            }
        });
    }

    @Override
    public void OnFuncPop(int height) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {
    }

    @Override
    public void onPause() {
        super.onPause();
        JMessageClient.exitConversation();
        ekBar.reset();
    }

    @Override
    public void onResume() {
        String targetId = mContext.getIntent().getStringExtra(TARGET_ID);
        if (mIsSingle) {
            if (null != targetId) {
                String appKey = mContext.getIntent().getStringExtra(TARGET_APP_KEY);
                JMessageClient.enterSingleConversation(targetId, appKey);
            }
        } else if (!isChatRoom) {
            long groupId = mContext.getIntent().getLongExtra(GROUP_ID, 0);
            if (groupId != 0) {
                JGApplication.isAtMe.put(groupId, false);
                JGApplication.isAtall.put(groupId, false);
                JMessageClient.enterGroupConversation(groupId);
            }
        }

        //历史消息中删除后返回到聊天界面刷新界面
        if (JGApplication.ids != null && JGApplication.ids.size() > 0) {
            for (Message msg : JGApplication.ids) {
                mChatAdapter.removeMessage(msg);
            }
        }
        if (mChatAdapter != null)
            mChatAdapter.notifyDataSetChanged();
        //发送名片返回聊天界面刷新信息
        super.onResume();

    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(CommandNotificationEvent event) {
        if (event.getType().equals(CommandNotificationEvent.Type.single)) {
            String msg = event.getMsg();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject object = new JSONObject(msg);
                        JSONObject jsonContent = object.getJSONObject("content");
                        String messageString = jsonContent.getString("message");
                        if (TextUtils.isEmpty(messageString)) {
                            mChatView.setTitle(mConv.getTitle());
                        } else {
                            mChatView.setTitle(messageString);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventMainThread(ChatRoomMessageEvent event) {
        List<Message> messages = event.getMessages();
        mChatAdapter.addMsgListToList(messages);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventMainThread(ChatRoomNotificationEvent event) {
        try {
            Constructor constructor = EventNotificationContent.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            List<Message> messages = new ArrayList<>();
            switch (event.getType()) {
                case add_chatroom_admin:
                case del_chatroom_admin:
                    event.getTargetUserInfoList(new GetUserInfoListCallback() {
                        @Override
                        public void gotResult(int i, String s, List<UserInfo> list) {
                            if (i == 0) {
                                for (UserInfo userInfo : list) {
                                    try {
                                        EventNotificationContent content = (EventNotificationContent) constructor.newInstance();
                                        Field field = content.getClass().getSuperclass().getDeclaredField("contentType");
                                        field.setAccessible(true);
                                        field.set(content, ContentType.eventNotification);
                                        String user = userInfo.getUserID() == JMessageClient.getMyInfo().getUserID()
                                                ? "你" : TextUtils.isEmpty(userInfo.getNickname()) ? userInfo.getUserName() : userInfo.getNickname();
                                        String result = event.getType() == ChatRoomNotificationEvent.Type.add_chatroom_admin ? "被设置成管理员" : "被取消管理员";
                                        content.setStringExtra("msg", user + result);
                                        if (mConv != null) {
                                            messages.add(mConv.createSendMessage(content));
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (messages.size() > 0) {
                                    mChatAdapter.addMsgListToList(messages);
                                }
                            }
                        }
                    });
                    break;
                default:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(MessageEvent event) {
        final Message message = event.getMessage();

        //若为群聊相关事件，如添加、删除群成员
        if (message.getContentType() == ContentType.eventNotification) {
            GroupInfo groupInfo = (GroupInfo) message.getTargetInfo();
            long groupId = groupInfo.getGroupID();
            EventNotificationContent.EventNotificationType type = ((EventNotificationContent) message
                    .getContent()).getEventNotificationType();
            if (groupId == mGroupId) {
                switch (type) {
                    case group_member_added:
                        //添加群成员事件
                        List<String> userNames = ((EventNotificationContent) message.getContent()).getUserNames();
                        //群主把当前用户添加到群聊，则显示聊天详情按钮
                        refreshGroupNum();
                        if (userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) {
                            getActivity().runOnUiThread(() -> mChatView.showRightBtn());
                        }

                        break;
                    case group_member_removed:
                        //删除群成员事件
                        userNames = ((EventNotificationContent) message.getContent()).getUserNames();
                        UserInfo operator = ((EventNotificationContent) message.getContent()).getOperatorUserInfo();
                        //群主删除了当前用户，则隐藏聊天详情按钮
                        if ((userNames.contains(mMyInfo.getNickname()) || userNames.contains(mMyInfo.getUserName())) && operator.getUserID() != mMyInfo.getUserID()) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mChatView.dismissRightBtn();
                                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                                    if (TextUtils.isEmpty(groupInfo.getGroupName())) {
                                        mChatView.setChatTitle(R.string.group);
                                    } else {
                                        mChatView.setChatTitle(groupInfo.getGroupName());
                                    }
                                    mChatView.dismissGroupNum();
                                }
                            });
                        } else {
                            refreshGroupNum();
                        }

                        break;
                    case group_member_exit:
                        EventNotificationContent content = (EventNotificationContent) message.getContent();
                        if (content.getUserNames().contains(JMessageClient.getMyInfo().getUserName())) {
                            mChatAdapter.notifyDataSetChanged();
                        } else {
                            refreshGroupNum();
                        }
                        break;
                }
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (message.getTargetType() == ConversationType.single) {
                    UserInfo userInfo = (UserInfo) message.getTargetInfo();
                    String targetId = userInfo.getUserName();
                    String appKey = userInfo.getAppKey();
                    if (mIsSingle && targetId.equals(mTargetId) && appKey.equals(mTargetAppKey)) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        if (lastMsg == null || message.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(message);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    long groupId = ((GroupInfo) message.getTargetInfo()).getGroupID();
                    if (groupId == mGroupId) {
                        Message lastMsg = mChatAdapter.getLastMsg();
                        if (lastMsg == null || message.getId() != lastMsg.getId()) {
                            mChatAdapter.addMsgToList(message);
                        } else {
                            mChatAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventMainThread(MessageRetractEvent event) {
        Message retractedMessage = event.getRetractedMessage();
        mChatAdapter.delMsgRetract(retractedMessage);
    }

    /**
     * 当在聊天界面断网再次连接时收离线事件刷新
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        if (conv.getType().equals(ConversationType.single)) {
            UserInfo userInfo = (UserInfo) conv.getTargetInfo();
            String targetId = userInfo.getUserName();
            String appKey = userInfo.getAppKey();
            if (mIsSingle && targetId.equals(mTargetId) && appKey.equals(mTargetAppKey)) {
                List<Message> singleOfflineMsgList = event.getOfflineMessageList();
                if (singleOfflineMsgList != null && singleOfflineMsgList.size() > 0) {
                    mChatView.setToBottom();
                    mChatAdapter.addMsgListToList(singleOfflineMsgList);
                }
            }
        } else {
            long groupId = ((GroupInfo) conv.getTargetInfo()).getGroupID();
            if (groupId == mGroupId) {
                List<Message> offlineMessageList = event.getOfflineMessageList();
                if (offlineMessageList != null && offlineMessageList.size() > 0) {
                    mChatView.setToBottom();
                    mChatAdapter.addMsgListToList(offlineMessageList);
                }
            }
        }
    }

    private void refreshGroupNum() {
        Conversation conv = JMessageClient.getGroupConversation(mGroupId);
        GroupInfo groupInfo = (GroupInfo) conv.getTargetInfo();
        if (!TextUtils.isEmpty(groupInfo.getGroupName())) {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NAME;
            Bundle bundle = new Bundle();
            bundle.putString(GROUP_NAME, groupInfo.getGroupName());
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        } else {
            android.os.Message handleMessage = mUIHandler.obtainMessage();
            handleMessage.what = REFRESH_GROUP_NUM;
            Bundle bundle = new Bundle();
            bundle.putInt(MEMBERS_COUNT, groupInfo.getGroupMembers().size());
            handleMessage.setData(bundle);
            handleMessage.sendToTarget();
        }
    }

    private ChattingListAdapter.ContentLongClickListener longClickListener = new ChattingListAdapter.ContentLongClickListener() {

        @Override
        public void onContentLongClick(final int position, View view) {
            if (isChatRoom) {
                return;
            }
            final Message msg = mChatAdapter.getMessage(position);

            if (msg == null) {
                return;
            }
            //如果是文本消息
            if ((msg.getContentType() == ContentType.text) && ((TextContent) msg.getContent()).getStringExtra("businessCard") == null) {
                //接收方
                if (msg.getDirect() == MessageDirect.receive) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            if (Build.VERSION.SDK_INT > 11) {
                                                ClipboardManager clipboard = (ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clip = ClipData.newPlainText("Simple text", content);
                                                clipboard.setPrimaryClip(clip);
                                            } else {
                                                android.text.ClipboardManager clip = (android.text.ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                if (clip.hasText()) {
                                                    clip.getText();
                                                }
                                            }
                                            Toast.makeText(getContext(), "已复制", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "只支持复制文字", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 1) {
                                        Intent intent = new Intent(getContext(), ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                    //发送方
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("复制"))
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("撤回"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 0) {
                                        if (msg.getContentType() == ContentType.text) {
                                            final String content = ((TextContent) msg.getContent()).getText();
                                            if (Build.VERSION.SDK_INT > 11) {
                                                ClipboardManager clipboard = (ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                ClipData clip = ClipData.newPlainText("Simple text", content);
                                                clipboard.setPrimaryClip(clip);
                                            } else {
                                                android.text.ClipboardManager clip = (android.text.ClipboardManager) mContext
                                                        .getSystemService(Context.CLIPBOARD_SERVICE);
                                                if (clip.hasText()) {
                                                    clip.getText();
                                                }
                                            }
                                            Toast.makeText(getContext(), "已复制", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "只支持复制文字", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 1) {
                                        //转发
                                        if (msg.getContentType() == ContentType.text || msg.getContentType() == ContentType.image ||
                                                (msg.getContentType() == ContentType.file && (msg.getContent()).getStringExtra("video") != null)) {
                                            Intent intent = new Intent(getContext(), ForwardMsgActivity.class);
                                            JGApplication.forwardMsg.clear();
                                            JGApplication.forwardMsg.add(msg);
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(getContext(), "只支持转发文本,图片,小视频", Toast.LENGTH_SHORT).show();
                                        }
                                    } else if (position == 2) {
                                        //撤回
                                        mConv.retractMessage(msg, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 855001) {
                                                    Toast.makeText(getContext(), "发送时间过长，不能撤回", Toast.LENGTH_SHORT).show();
                                                } else if (i == 0) {
                                                    mChatAdapter.delMsgRetract(msg);
                                                }
                                            }
                                        });
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
                //除了文本消息类型之外的消息类型
            } else {
                //接收方
                if (msg.getDirect() == MessageDirect.receive) {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 1) {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    } else {
                                        Intent intent = new Intent(getContext(), ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                    //发送方
                } else {
                    int[] location = new int[2];
                    view.getLocationOnScreen(location);
                    float OldListY = (float) location[1];
                    float OldListX = (float) location[0];
                    new TipView.Builder(getContext(), mChatView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                            .addItem(new TipItem("转发"))
                            .addItem(new TipItem("撤回"))
                            .addItem(new TipItem("删除"))
                            .setOnItemClickListener(new TipView.OnItemClickListener() {
                                @Override
                                public void onItemClick(String str, final int position) {
                                    if (position == 1) {
                                        //撤回
                                        mConv.retractMessage(msg, new BasicCallback() {
                                            @Override
                                            public void gotResult(int i, String s) {
                                                if (i == 855001) {
                                                    Toast.makeText(getContext(), "发送时间过长，不能撤回", Toast.LENGTH_SHORT).show();
                                                } else if (i == 0) {
                                                    mChatAdapter.delMsgRetract(msg);
                                                }
                                            }
                                        });
                                    } else if (position == 0) {
                                        Intent intent = new Intent(getContext(), ForwardMsgActivity.class);
                                        JGApplication.forwardMsg.clear();
                                        JGApplication.forwardMsg.add(msg);
                                        startActivity(intent);
                                    } else {
                                        //删除
                                        mConv.deleteMessage(msg.getId());
                                        mChatAdapter.removeMessage(msg);
                                    }
                                }

                                @Override
                                public void dismiss() {

                                }
                            })
                            .create();
                }
            }
        }
    };

    /**
     * 消息已读事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventMainThread(MessageReceiptStatusChangeEvent event) {
        List<MessageReceiptStatusChangeEvent.MessageReceiptMeta> messageReceiptMetas = event.getMessageReceiptMetas();
        for (MessageReceiptStatusChangeEvent.MessageReceiptMeta meta : messageReceiptMetas) {
            long serverMsgId = meta.getServerMsgId();
            int unReceiptCnt = meta.getUnReceiptCnt();
            mChatAdapter.setUpdateReceiptCount(serverMsgId, unReceiptCnt);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case JGApplication.RESULT_CODE_AT_MEMBER:
                if (!mIsSingle) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    String username = data.getStringExtra(JGApplication.TARGET_ID);
                    String appKey = data.getStringExtra(JGApplication.TARGET_APP_KEY);
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(username, appKey);
                    if (null == mAtList) {
                        mAtList = new ArrayList<UserInfo>();
                    }
                    mAtList.add(userInfo);
                    mLongClick = true;
                    ekBar.getEtChat().appendMention(data.getStringExtra(JGApplication.NAME));
                    ekBar.getEtChat().setSelection(ekBar.getEtChat().getText().length());
                }
                break;
            case JGApplication.RESULT_CODE_SEND_FILE:
                String msgListJson = data.getStringExtra(JGApplication.MSG_LIST_JSON);
                if (msgListJson != null) {
                    for (Message msg : Message.fromJsonToCollection(msgListJson)) {
                        handleSendMsg(msg);
                    }
                }
                break;
            case JGApplication.RESULT_CODE_CHAT_DETAIL:
                String title = data.getStringExtra(JGApplication.CONV_TITLE);
                if (!mIsSingle) {
                    GroupInfo groupInfo = (GroupInfo) mConv.getTargetInfo();
                    UserInfo userInfo = groupInfo.getGroupMemberInfo(mMyInfo.getUserName(), mMyInfo.getAppKey());
                    //如果自己在群聊中，同时显示群人数
                    if (userInfo != null) {
                        if (TextUtils.isEmpty(title)) {
                            mChatView.setChatTitle(IdHelper.getString(mContext, "group"),
                                    data.getIntExtra(MEMBERS_COUNT, 0));
                        } else {
                            mChatView.setChatTitle(title, data.getIntExtra(MEMBERS_COUNT, 0));
                        }
                    } else {
                        if (TextUtils.isEmpty(title)) {
                            mChatView.setChatTitle(IdHelper.getString(mContext, "group"));
                        } else {
                            mChatView.setChatTitle(title);
                        }
                        mChatView.dismissGroupNum();
                    }

                } else mChatView.setChatTitle(title);
                if (data.getBooleanExtra("deleteMsg", false)) {
                    mChatAdapter.clearMsgList();
                }
                break;
        }

    }




    /**
     * 处理发送图片，刷新界面
     *
     * @param msg
     */
    private void handleSendMsg(Message msg) {
        mChatAdapter.setSendMsgs(msg);
        mChatView.setToBottom();
    }

    private static class UIHandler extends Handler {
        private final WeakReference<ChatFragment> mActivity;

        public UIHandler(ChatFragment activity) {
            mActivity = new WeakReference<ChatFragment>(activity);
        }

        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            ChatFragment activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case REFRESH_LAST_PAGE:
                        activity.mChatAdapter.dropDownToRefresh();
                        activity.mChatView.getListView().onDropDownComplete();
                        if (activity.mChatAdapter.isHasLastPage()) {
                            if (Build.VERSION.SDK_INT >= 21) {
                                activity.mChatView.getListView()
                                        .setSelectionFromTop(activity.mChatAdapter.getOffset(),
                                                activity.mChatView.getListView().getHeaderHeight());
                            } else {
                                activity.mChatView.getListView().setSelection(activity.mChatAdapter
                                        .getOffset());
                            }
                            activity.mChatAdapter.refreshStartPosition();
                        } else {
                            activity.mChatView.getListView().setSelection(0);
                        }
                        //显示上一页的消息数18条
                        activity.mChatView.getListView()
                                .setOffset(activity.mChatAdapter.getOffset());
                        break;
                    case REFRESH_GROUP_NAME:
                        if (activity.mConv != null) {
                            int num = msg.getData().getInt(MEMBERS_COUNT);
                            String groupName = msg.getData().getString(GROUP_NAME);
                            activity.mChatView.setChatTitle(groupName, num);
                        }
                        break;
                    case REFRESH_GROUP_NUM:
                        int num = msg.getData().getInt(MEMBERS_COUNT);
                        activity.mChatView.setChatTitle(R.string.group, num);
                        break;
                    case REFRESH_CHAT_TITLE:
                        if (activity.mGroupInfo != null) {
                            //检查自己是否在群组中
                            UserInfo info = activity.mGroupInfo.getGroupMemberInfo(activity.mMyInfo.getUserName(),
                                    activity.mMyInfo.getAppKey());
                            if (!TextUtils.isEmpty(activity.mGroupInfo.getGroupName())) {
                                if (info != null) {
                                    activity.mChatView.setChatTitle(activity.mTitle,
                                            activity.mGroupInfo.getGroupMembers().size());
                                    activity.mChatView.showRightBtn();
                                } else {
                                    activity.mChatView.setChatTitle(activity.mTitle);
                                    activity.mChatView.dismissRightBtn();
                                }
                            }
                        }
                        break;
                }
            }
        }
    }
}
