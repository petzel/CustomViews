package com.ericpetzel.customviewing.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.ericpetzel.customviewing.LogUtils;
import com.ericpetzel.customviewing.TheBus;
import com.ericpetzel.customviewing.events.LogEvent;

public class VerboseFrameLayout extends FrameLayout {

    public VerboseFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        log("3 arg constructor");
    }

    public VerboseFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        log("2 arg constructor");
    }

    public VerboseFrameLayout(Context context) {
        super(context);
        log("1 arg constructor");
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        LogUtils.onMeasure(this, widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void log(String event) {
        TheBus.getInstance().post(new LogEvent(event, this));
    }
}
