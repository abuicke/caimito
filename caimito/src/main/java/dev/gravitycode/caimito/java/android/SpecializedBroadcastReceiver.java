package dev.gravitycode.caimito.java.android;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;

import androidx.annotation.Nullable;

import dev.gravitycode.caimito.kotlin.core.AppConfiguration;

public abstract class SpecializedBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = SpecializedBroadcastReceiver.class.getSimpleName();

    protected SpecializedBroadcastReceiver() {

    }

    public abstract IntentFilter getIntentFilter();

    public static void registerSpecializedReceiver(Context context, SpecializedBroadcastReceiver receiver) {
        checkNotNull(context);
        checkNotNull(receiver);
        IntentFilter intentFiler = receiver.getIntentFilter();
        context.registerReceiver(receiver, intentFiler);
    }

    public static void unregisterSpecializedReceiver(Context context, @Nullable SpecializedBroadcastReceiver receiver) {
        checkNotNull(context);
        if(receiver != null) {
            context.unregisterReceiver(receiver);
        }else {
            if(AppConfiguration.INSTANCE.isDebug()) {
                throw new IllegalArgumentException("attempting to " +
                        "unregister null specialized broadcast receiver");
            }else {
                Log.w(TAG, "attempting to unregister null specialized broadcast receiver");
            }
        }
    }

}
