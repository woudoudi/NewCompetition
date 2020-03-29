package com.example.administrator.competition.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.base.scanlistlibrary.scanlist.ScanVideoPlayView;
import com.example.administrator.competition.R;
import com.example.administrator.competition.adapter.HomeMessageAdapter;
import com.yidao.module_lib.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MessagePopWindow {

    public void showMessagePopWindow(Context context,View view){

        View contentView = LayoutInflater.from(context).inflate(R.layout.item_pop_message, null);
        PopupWindow popWnd = new PopupWindow(context);
        popWnd.setContentView(contentView);
        popWnd.setWidth(DensityUtil.dip2px(context,150));
        popWnd.setHeight(DensityUtil.dip2px(context,200));

        popWnd.setOutsideTouchable(true);


        List<Object> list = new ArrayList<>();
        for (int i=0;i<8;i++){
            list.add(i+"");
        }

        HomeMessageAdapter adapter = new HomeMessageAdapter(context,list);
        adapter.setOnItemClick(mOnItemClick);
        ScanVideoPlayView scanVideoPlayView = contentView.findViewById(R.id.scan_message);
        scanVideoPlayView.initPlayListView(adapter,1,0,false);
        scanVideoPlayView.setOnRefresh(false);

        popWnd.showAsDropDown(view,-DensityUtil.dip2px(context,15),-DensityUtil.dip2px(context,15),Gravity.TOP|Gravity.RIGHT);
    }


    public void showMessageLandPopWindow(Context context,View view){

        View contentView = LayoutInflater.from(context).inflate(R.layout.item_pop_message, null);
        PopupWindow popWnd = new PopupWindow(context);
        popWnd.setContentView(contentView);
        popWnd.setWidth(DensityUtil.dip2px(context,150));
        popWnd.setHeight(DensityUtil.dip2px(context,200));

        popWnd.setOutsideTouchable(true);


        List<Object> list = new ArrayList<>();
        for (int i=0;i<8;i++){
            list.add(i+"");
        }

        HomeMessageAdapter adapter = new HomeMessageAdapter(context,list);
        adapter.setOnItemClick(mOnItemClick);
        ScanVideoPlayView scanVideoPlayView = contentView.findViewById(R.id.scan_message);
        scanVideoPlayView.initPlayListView(adapter,1,0,false);
        scanVideoPlayView.setOnRefresh(false);

        popWnd.showAsDropDown(view,0,-DensityUtil.dip2px(context,15));
    }



    HomeMessageAdapter.OnItemClick mOnItemClick = new HomeMessageAdapter.OnItemClick() {
        @Override
        public void onItemClick() {

        }
    };
}
