package com.midistudio.mods;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by kvnxp on 22/07/2016.
 */
class scrollviewlock extends ScrollView {
    private boolean move = false;

    public scrollviewlock(Context context) {
        super(context);
    }

    public scrollviewlock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public scrollviewlock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public  void setMove(Boolean move){
        this.move = move;
    }



    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public void setOnTouchListener(OnTouchListener l) {
        //super.setOnTouchListener(l);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return Boolean.parseBoolean(null);
    }
}
