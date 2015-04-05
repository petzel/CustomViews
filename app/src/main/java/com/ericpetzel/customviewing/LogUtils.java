package com.ericpetzel.customviewing;

import android.view.View;
import android.view.ViewGroup;

import com.ericpetzel.customviewing.events.LogEvent;

public class LogUtils {
    public static void onMeasure(ViewGroup viewGroup, int widthMeasureSpec, int heightMeasureSpec) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("onMeasure")
                .append("\nwidthSize: ").append(View.MeasureSpec.getSize(widthMeasureSpec))
                .append("\nwidthMode: ").append(getMode(widthMeasureSpec))
                .append("\nheightSize: ").append(View.MeasureSpec.getSize(heightMeasureSpec))
                .append("\nheightMode: ").append(getMode(heightMeasureSpec));

        TheBus.getInstance().post(new LogEvent(stringBuilder.toString(), viewGroup));
    }

    private static String getMode(int measureSpec) {
        switch (View.MeasureSpec.getMode(measureSpec)) {
            case View.MeasureSpec.AT_MOST:
                return "AT_MOST";
            case View.MeasureSpec.EXACTLY:
                return "EXACTLY";
            case View.MeasureSpec.UNSPECIFIED:
                return "UNSPECIFIED";
        }
        throw new IllegalStateException("unknown mode given measure spec " + measureSpec);
    }
}
