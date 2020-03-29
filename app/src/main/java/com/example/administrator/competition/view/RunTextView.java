package com.example.administrator.competition.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.robinhood.ticker.TickerView;

public class RunTextView   {


    private int duration = 10000;
    private int number;

    public float getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    /**
     * 显示
     * @param number
     */
    public void runWithAnimation(int number, final TickerView textView){
        int[] values2 = new int[]{0,90+number};
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "number", values2);

        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new LinearInterpolator());

        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                textView.setText(value%10 + "");
            }
        });
    }

    /**
     * 显示
     * @param number
     */
    public void runTextWithAnimation(int number, final TickerView textView){
        int[] values = new int[]{0,30+number};
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "number", values);

        objectAnimator.setDuration(duration);
        objectAnimator.setInterpolator(new LinearInterpolator());

        objectAnimator.start();
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                String text = value%2 == 0 ?"+":"-" ;
                textView.setText(text);
            }
        });
    }
}
