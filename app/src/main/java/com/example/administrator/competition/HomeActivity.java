package com.example.administrator.competition;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

import com.example.administrator.competition.entity.CommonConfig;
import com.example.administrator.competition.fragment.GuessFragment;
import com.example.administrator.competition.fragment.HomeFragment;
import com.example.administrator.competition.fragment.MyFragment;
import com.example.administrator.competition.mvp.presenter.JCLoginPress;
import com.example.administrator.competition.service.WebsocketService;
import com.example.http_lib.utils.UserCacheHelper;
import com.example.http_lib.websocket.MessageBean;
import com.example.http_lib.websocket.WebSocketManager;
import com.yidao.module_lib.anotation.FragmentId;
import com.yidao.module_lib.base.BaseFragment;
import com.yidao.module_lib.base.BaseView;
import com.yidao.module_lib.entity.EventBusBean;
import com.yidao.module_lib.manager.ViewManager;
import com.yidao.module_lib.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class HomeActivity extends BaseView {

    HomeFragment mHomeFragment = new HomeFragment();
    GuessFragment mGuessFragment = new GuessFragment();
    MyFragment mMyFragment = new MyFragment();

    BaseFragment[] mBaseFragments = new BaseFragment[]{mHomeFragment, mGuessFragment, mMyFragment};

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @Override
    protected int getView() {
        return R.layout.activity_home;
    }

    @Override
    public void init() {
        showFragment(FragmentId.VideoPlay_Fragment);

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        bindService();
    }

    /**
     * 绑定服务
     */
    private void bindService() {
        Intent bindIntent = new Intent(getApplicationContext(), WebsocketService.class);
        bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //服务与活动成功绑定
            Log.e("HomeActivity", "服务与活动成功绑定");
            WebsocketService.JWebSocketClientBinder binder = (WebsocketService.JWebSocketClientBinder) iBinder;
            WebSocketManager.getSocketManager().setJWebSocketClient(binder.getService());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            //服务与活动断开
            Log.e("HomeActivity", "服务与活动成功断开");
            WebSocketManager.getSocketManager().closeConnect();
        }
    };


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEvent(MessageBean event) {

    }


    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onEventEvent(EventBusBean event) {
        if (event.type == EventBusBean.JumpToBetFragment) {
            if (mHomeFragment != null) {
                radioGroup.clearCheck();
                mHomeFragment.showFragment(1);
                mHomeFragment.setTitleType(event.tableType);
            }
        }
    }

    @OnCheckedChanged({R.id.radio_home, R.id.radio_guess, R.id.radio_my})
    public void onCheckedChanged(CompoundButton view, boolean isCheck) {
        setCompentButton(view);
        switch (view.getId()) {
            case R.id.radio_home:
                if (isCheck) {
                    showFragment(FragmentId.VideoPlay_Fragment);
                    if (mHomeFragment != null) {
                        mHomeFragment.showFragment(0);
                    }
                }
                break;
            case R.id.radio_guess:
                if (isCheck) {
                    showFragment(FragmentId.GrassStar_Fragment);
                }
                break;
            case R.id.radio_custom:
                if (isCheck) {
                }
                break;
            case R.id.radio_my:
                if (isCheck) {
                    showFragment(FragmentId.Message_Fragment);
                }
                break;
        }
    }

    @OnClick(R.id.radio_custom)
    public void onViewClicked() {
        if(isFastClick()){
            return;
        }
        JCLoginPress.jumpChatActivity(CommonConfig.Service,"客服");
    }

    private void setCompentButton(CompoundButton view) {
        view.setTextColor(getResources().getColor(view.isChecked() ? R.color.color_BCA081 : R.color.color_999999));
    }


    public void showFragment(@FragmentId int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (!mBaseFragments[position].isAdded()) {
            transaction.add(R.id.frameLayout_home, mBaseFragments[position], position + "");
        }
        for (int i = 0; i < mBaseFragments.length; i++) {
            if (position == i) {
                transaction.show(mBaseFragments[i]);
            } else {
                transaction.hide(mBaseFragments[i]);
            }
        }
        transaction.commitAllowingStateLoss();
    }

    long firstTime;

    @Override
    public void onBackPressed() {
        long secondTime = System.currentTimeMillis();
        if (secondTime - firstTime > 2000) {
            ToastUtil.showShortToast("再按一次退出程序");
            firstTime = secondTime;
        } else {
            ViewManager.getInstance().AppExit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        WebSocketManager.getSocketManager().closeConnect();
    }
}
