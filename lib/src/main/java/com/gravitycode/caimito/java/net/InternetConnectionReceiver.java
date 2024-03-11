package com.gravitycode.caimito.java.net;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static com.gravitycode.caimito.java.android.PermissionUtils.checkPermission;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import androidx.annotation.RequiresPermission;

import com.gravitycode.caimito.java.Callback;
import com.gravitycode.caimito.java.android.IntentUtils;
import com.gravitycode.caimito.java.android.SpecializedBroadcastReceiver;

public class InternetConnectionReceiver extends SpecializedBroadcastReceiver {

    private final Callback<Boolean> _internetAvailableCallback;

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public InternetConnectionReceiver(Context context, Callback<Boolean> internetAvailableCallback) {
        checkPermission(context, ACCESS_NETWORK_STATE);
        _internetAvailableCallback = internetAvailableCallback;
    }

    @Override
    public IntentFilter getIntentFilter() {
        return IntentUtils.intentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    }

    @Override
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public final void onReceive(Context context, Intent intent) {
        checkPermission(context, ACCESS_NETWORK_STATE);
        boolean isInternetConnected = InternetUtils.isInternetConnected(context);
        _internetAvailableCallback.call(isInternetConnected);
    }

}
