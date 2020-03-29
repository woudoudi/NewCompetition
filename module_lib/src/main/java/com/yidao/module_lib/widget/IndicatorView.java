package com.yidao.module_lib.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yidao.module_lib.R;

/**
 * Created with XIAOYUDEXIEE.
 * Date: 2020/1/2
 */
public class IndicatorView extends LinearLayout {

    private final View view;

    public IndicatorView(Context context) {
        super(context);
        View inflate = inflate(context, R.layout.indicator_view, this);
        view = inflate.findViewById(R.id.view);

    }


    public void selected(boolean select){
        view.setSelected(select);
    }

}

