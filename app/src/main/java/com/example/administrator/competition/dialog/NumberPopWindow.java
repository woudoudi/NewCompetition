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
import com.example.administrator.competition.adapter.NumberAdapter;
import com.yidao.module_lib.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class NumberPopWindow {

    private PopupWindow mPopWnd;

    public void showNumberPopWindow(Context context, View view,List<String> list, NumberAdapter.OnItemClick onItemClick){

        View contentView = LayoutInflater.from(context).inflate(R.layout.item_pop_message, null);
        mPopWnd = new PopupWindow(context);
        mPopWnd.setContentView(contentView);
        mPopWnd.setWidth(DensityUtil.dip2px(context,60));
        mPopWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopWnd.setOutsideTouchable(true);

        NumberAdapter adapter = new NumberAdapter(context,list);
        adapter.setOnItemClick(onItemClick);
        ScanVideoPlayView scanVideoPlayView = contentView.findViewById(R.id.scan_message);
        scanVideoPlayView.initPlayListView(adapter,1,0,false);
        scanVideoPlayView.setOnRefresh(false);

        mPopWnd.showAsDropDown(view);
    }

    public void dismiss(){
        if(mPopWnd!=null && mPopWnd.isShowing()){
            mPopWnd.dismiss();
        }
    }
}
