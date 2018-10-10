package ie.moses.caimito.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.Log;

import ie.moses.caimito.BuildConfig;

import static com.google.common.base.Preconditions.checkNotNull;

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
            if(BuildConfig.DEBUG) {
                throw new IllegalArgumentException("attempting to " +
                        "unregister null specialized broadcast receiver");
            }else {
                Log.w(TAG, "attempting to unregister null specialized broadcast receiver");
            }
        }
    }

}
