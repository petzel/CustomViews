package com.ericpetzel.customviewing.events;

import android.view.View;

import com.ericpetzel.customviewing.LogUtils;
import com.ericpetzel.customviewing.MyApplication;

import java.util.HashSet;

public class LogEvent {

    private static String packageName;
    public final String message;
    private static final HashSet<String> mHelperClasses = new HashSet<String>(){{
        add(LogEvent.class.getName());
        add(LogUtils.class.getName());
    }};

    public LogEvent(String message, Object object) {
        if (packageName == null) {
            packageName = MyApplication.getInstance().getPackageName();
        }

        if (object != null) {
            String className = "";
            for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
                String elementClassName = element.getClassName();
                if (elementClassName.contains(packageName)
                        && !mHelperClasses.contains(elementClassName)) {
                    className = elementClassName;
                    break;
                }
            }
            className = className.substring(className.lastIndexOf(".") + 1); // strip namespace

            String identifier = Integer.toString(object.hashCode());
            if (object instanceof View) {
                identifier = MyApplication.getInstance().getResources().getResourceName(((View) object).getId());
                identifier = identifier.replace(packageName, ""); // remove package name
                identifier = identifier.substring(identifier.indexOf("id/")); // strip away package name
            }
            className += "(" + identifier + ")"; // append hashCode to differentiate instances
            message = className + "\n" + message;
        }

        this.message = message;
    }
}
