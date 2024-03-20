package dev.gravitycode.caimito.java.android;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class ToastUtils {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({Toast.LENGTH_SHORT, Toast.LENGTH_LONG})
    public @interface Duration {
    }

    public static void toast(Context context, CharSequence msg) {
        toast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void toast(Context context, CharSequence msg, @Duration int duration) {
        Toast.makeText(context, msg, duration).show();
    }

}
