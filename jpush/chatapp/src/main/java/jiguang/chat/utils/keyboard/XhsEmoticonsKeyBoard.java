package jiguang.chat.utils.keyboard;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import jiguang.chat.R;
import jiguang.chat.application.JGApplication;
import jiguang.chat.utils.event.ImageEvent;
import jiguang.chat.utils.keyboard.adpater.PageSetAdapter;
import jiguang.chat.utils.keyboard.data.PageSetEntity;
import jiguang.chat.utils.keyboard.utils.EmoticonsKeyboardUtils;
import jiguang.chat.utils.keyboard.widget.AutoHeightLayout;
import jiguang.chat.utils.keyboard.widget.EmoticonsEditText;
import jiguang.chat.utils.keyboard.widget.EmoticonsFuncView;
import jiguang.chat.utils.keyboard.widget.EmoticonsIndicatorView;
import jiguang.chat.utils.keyboard.widget.EmoticonsToolBarView;
import jiguang.chat.utils.keyboard.widget.FuncLayout;
import jiguang.chat.view.RecordVoiceButton;

public class XhsEmoticonsKeyBoard extends AutoHeightLayout implements View.OnClickListener, EmoticonsFuncView.OnEmoticonsPageViewListener,
        EmoticonsToolBarView.OnToolBarItemClickListener, EmoticonsEditText.OnBackKeyClickListener, FuncLayout.OnFuncChangeListener {

    public static final int FUNC_TYPE_EMOTION = -1;
    public static final int FUNC_TYPE_APPPS = -2;

    protected LayoutInflater mInflater;

    protected ImageView mBtnVoiceOrText;
    protected RecordVoiceButton mBtnVoice;
    protected EmoticonsEditText mEtChat;
    protected ImageView mBtnFace;
    protected RelativeLayout mRlInput;
    protected ImageView mBtnMultimedia;
    protected ImageView mBtnSend;
    protected FuncLayout mLyKvml;

    protected EmoticonsFuncView mEmoticonsFuncView;
    protected EmoticonsIndicatorView mEmoticonsIndicatorView;
    protected EmoticonsToolBarView mEmoticonsToolBarView;

    protected boolean mDispatchKeyEventPreImeLock = false;

    protected LinearLayout mLlMore;
    private RelativeLayout mInputContainer;

    public XhsEmoticonsKeyBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflateKeyboardBar();
        initView();
        initFuncView();
    }

    protected void inflateKeyboardBar() {
        mInflater.inflate(R.layout.view_keyboard_xhs, this);
    }

    protected View inflateFunc() {
        return mInflater.inflate(R.layout.view_func_emoticon, null);
    }

    protected void initView() {
        mBtnVoiceOrText = ((ImageView) findViewById(R.id.btn_voice_or_text));
        mBtnVoice = ((RecordVoiceButton) findViewById(R.id.btn_voice));
        mEtChat = ((EmoticonsEditText) findViewById(R.id.et_chat));
        mBtnFace = ((ImageView) findViewById(R.id.btn_face));
        mRlInput = ((RelativeLayout) findViewById(R.id.rl_input));
        mBtnMultimedia = ((ImageView) findViewById(R.id.btn_multimedia));
        mBtnSend = ((ImageView) findViewById(R.id.btn_send));
        mLyKvml = ((FuncLayout) findViewById(R.id.ly_kvml));
        mLlMore = findViewById(R.id.ll_more);

        mInputContainer = findViewById(R.id.rl_input_container);

        TextView tvAlbum = findViewById(R.id.tv_album);
        TextView tvTaking = findViewById(R.id.tv_taking);

//        mBtnVoiceOrText.setOnClickListener(this);
        mBtnFace.setOnClickListener(this);
        mBtnMultimedia.setOnClickListener(this);
        mEtChat.setOnBackKeyClickListener(this);

        tvAlbum.setOnClickListener(this);
        tvTaking.setOnClickListener(this);
    }

    protected void initFuncView() {
        initEmoticonFuncView();
        initEditView();
    }

    protected void initEmoticonFuncView() {
        View keyboardView = inflateFunc();
        mLyKvml.addFuncView(FUNC_TYPE_EMOTION, keyboardView);
        mEmoticonsFuncView = ((EmoticonsFuncView) findViewById(R.id.view_epv));
        mEmoticonsIndicatorView = ((EmoticonsIndicatorView) findViewById(R.id.view_eiv));
        mEmoticonsToolBarView = ((EmoticonsToolBarView) findViewById(R.id.view_etv));
        mEmoticonsFuncView.setOnIndicatorListener(this);
        mEmoticonsToolBarView.setOnToolBarItemClickListener(this);
        mLyKvml.setOnFuncChangeListener(this);
    }

    protected void initEditView() {
        mEtChat.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!mEtChat.isFocused()) {
                    mEtChat.setFocusable(true);
                    mEtChat.setFocusableInTouchMode(true);
                }
                return false;
            }
        });

        mEtChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                mBtnSend.setVisibility(s.length() > 0 ? VISIBLE : GONE);

//                if (!TextUtils.isEmpty(s)) {
//                    mBtnSend.setImageResource(R.mipmap.send_check);
//                    mBtnSend.setEnabled(true);
////                    mBtnMultimedia.setVisibility(GONE);
//                } else {
////                    mBtnMultimedia.setVisibility(VISIBLE);
//                    mBtnSend.setImageResource(R.mipmap.send_white);
//                    mBtnSend.setEnabled(false);
//                }
            }
        });
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        if (pageSetAdapter != null) {
            ArrayList<PageSetEntity> pageSetEntities = pageSetAdapter.getPageSetEntityList();
            if (pageSetEntities != null) {
                for (PageSetEntity pageSetEntity : pageSetEntities) {
                    mEmoticonsToolBarView.addToolItemView(pageSetEntity);
                }
            }
        }
        mEmoticonsFuncView.setAdapter(pageSetAdapter);
    }

    public void addFuncView(View view) {
        mLyKvml.addFuncView(FUNC_TYPE_APPPS, view);
    }

    public void reset() {
        EmoticonsKeyboardUtils.closeSoftKeyboard(this);
        mLyKvml.hideAllFuncView();
        mBtnFace.setImageResource(R.drawable.icon_face_nomal);
    }

    protected void showVoice() {
        mRlInput.setVisibility(GONE);
        mBtnVoice.setVisibility(VISIBLE);
        reset();
    }

    protected void checkVoice() {
        if (mBtnVoice.isShown()) {
            mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
        } else {
            mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
        }
    }

    protected void showText() {
        mRlInput.setVisibility(VISIBLE);
        mBtnVoice.setVisibility(GONE);
    }

    protected void toggleFuncView(int key) {
        showText();
        mLyKvml.toggleFuncView(key, isSoftKeyboardPop(), mEtChat);
    }

    @Override
    public void onFuncChange(int key) {
        if (FUNC_TYPE_EMOTION == key) {
            mBtnFace.setImageResource(R.drawable.icon_face_pop);
        } else {
            mBtnFace.setImageResource(R.drawable.icon_face_nomal);
        }
        checkVoice();
    }

    protected void setFuncViewHeight(int height) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLyKvml.getLayoutParams();
        params.height = height;
        mLyKvml.setLayoutParams(params);
    }

    @Override
    public void onSoftKeyboardHeightChanged(int height) {
        mLyKvml.updateHeight(height);
    }

    @Override
    public void OnSoftPop(int height) {
        super.OnSoftPop(height);
        mLyKvml.setVisibility(true);
        onFuncChange(mLyKvml.DEF_KEY);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (mLyKvml.isOnlyShowSoftKeyboard()) {
            reset();
        } else {
            onFuncChange(mLyKvml.getCurrentFuncKey());
        }
    }

    public void addOnFuncKeyBoardListener(FuncLayout.OnFuncKeyBoardListener l) {
        mLyKvml.addOnKeyBoardListener(l);
    }

    @Override
    public void emoticonSetChanged(PageSetEntity pageSetEntity) {
        mEmoticonsToolBarView.setToolBtnSelect(pageSetEntity.getUuid());
    }

    @Override
    public void playTo(int position, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playTo(position, pageSetEntity);
    }

    @Override
    public void playBy(int oldPosition, int newPosition, PageSetEntity pageSetEntity) {
        mEmoticonsIndicatorView.playBy(oldPosition, newPosition, pageSetEntity);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_voice_or_text) {
            if (mRlInput.isShown()) {
                mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
                showVoice();
            } else {
                showText();
                mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
                EmoticonsKeyboardUtils.openSoftKeyboard(mEtChat);
            }
        } else if (i == R.id.btn_face) {
            toggleFuncView(FUNC_TYPE_EMOTION);
        } else if (i == R.id.btn_multimedia) {
            if (mLlMore.getVisibility() == VISIBLE) {
                mLlMore.setVisibility(GONE);
            } else {
                mLlMore.setVisibility(VISIBLE);
            }

//            toggleFuncView(FUNC_TYPE_APPPS);
        } else if (i == R.id.tv_album) {
            EventBus.getDefault().post(new ImageEvent(JGApplication.IMAGE_MESSAGE));
        } else if (i == R.id.tv_taking) {
            EventBus.getDefault().post(new ImageEvent(JGApplication.TAKE_PHOTO_MESSAGE));
        }

//        if (appBean.getFuncName().equals("图片")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.IMAGE_MESSAGE));
//        } else if (appBean.getFuncName().equals("拍摄")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.TAKE_PHOTO_MESSAGE));
//        }else if (appBean.getFuncName().equals("位置")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.TAKE_LOCATION));
//        }else if (appBean.getFuncName().equals("文件")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.FILE_MESSAGE));
//        }else if (appBean.getFuncName().equals("视频")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.TACK_VIDEO));
//        }else if (appBean.getFuncName().equals("语音")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.TACK_VOICE));
//        }else if (appBean.getFuncName().equals("名片")) {
//            EventBus.getDefault().post(new ImageEvent(JGApplication.BUSINESS_CARD));
//        }
    }

    public void setVideoText() {
        if (mRlInput.isShown()) {
            mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text_keyboard);
            showVoice();
        } else {
            showText();
            mBtnVoiceOrText.setImageResource(R.drawable.btn_voice_or_text);
            EmoticonsKeyboardUtils.openSoftKeyboard(mEtChat);
        }
    }

    public ImageView getVoiceOrText() {
        return mBtnVoiceOrText;
    }

    @Override
    public void onToolBarItemClick(PageSetEntity pageSetEntity) {
        mEmoticonsFuncView.setCurrentPageSet(pageSetEntity);
    }

    @Override
    public void onBackKeyClick() {
        if (mLyKvml.isShown()) {
            mDispatchKeyEventPreImeLock = true;
            reset();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (mDispatchKeyEventPreImeLock) {
                    mDispatchKeyEventPreImeLock = false;
                    return true;
                }
                if (mLyKvml.isShown()) {
                    reset();
                    return true;
                } else {
                    return super.dispatchKeyEvent(event);
                }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean requestFocus(int direction, Rect previouslyFocusedRect) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return false;
        }
        return super.requestFocus(direction, previouslyFocusedRect);
    }

    @Override
    public void requestChildFocus(View child, View focused) {
        if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            return;
        }
        super.requestChildFocus(child, focused);
    }

    public boolean dispatchKeyEventInFullScreen(KeyEvent event) {
        if (event == null) {
            return false;
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_BACK:
                if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext()) && mLyKvml.isShown()) {
                    reset();
                    return true;
                }
            default:
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    boolean isFocused;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                        isFocused = mEtChat.getShowSoftInputOnFocus();
                    } else {
                        isFocused = mEtChat.isFocused();
                    }
                    if (isFocused) {
                        mEtChat.onKeyDown(event.getKeyCode(), event);
                    }
                }
                return false;
        }
    }

    public EmoticonsEditText getEtChat() {
        return mEtChat;
    }

    public RecordVoiceButton getBtnVoice() {
        return mBtnVoice;
    }

    public ImageView getBtnSend() {
        return mBtnSend;
    }

    public EmoticonsFuncView getEmoticonsFuncView() {
        return mEmoticonsFuncView;
    }

    public EmoticonsIndicatorView getEmoticonsIndicatorView() {
        return mEmoticonsIndicatorView;
    }

    public EmoticonsToolBarView getEmoticonsToolBarView() {
        return mEmoticonsToolBarView;
    }

    public void setInputContainerVisiblity(boolean visiblity) {
        if (mInputContainer != null) {
            mInputContainer.setVisibility(visiblity ? VISIBLE : GONE);
        }
    }
}
