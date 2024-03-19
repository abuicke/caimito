package com.gravitycode.caimito.java.net;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static com.gravitycode.caimito.java.android.PermissionUtils.checkPermission;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.RequiresPermission;

public final class InternetUtils {

    private InternetUtils() {
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isInternetConnected(Context context) {
        checkPermission(context, ACCESS_NETWORK_STATE);
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } else {
            return false;
        }
    }

}
