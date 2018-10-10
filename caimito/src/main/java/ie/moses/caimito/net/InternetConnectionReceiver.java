package ie.moses.caimito.net;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

import ie.moses.caimito.Callback;
import ie.moses.caimito.android.SpecializedBroadcastReceiver;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static ie.moses.caimito.android.IntentUtils.intentFilter;
import static ie.moses.caimito.android.PermissionUtils.checkPermission;

public class InternetConnectionReceiver extends SpecializedBroadcastReceiver {

    private final Callback<Boolean> _internetAvailableCallback;

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public InternetConnectionReceiver(Context context, Callback<Boolean> internetAvailableCallback) {
        checkPermission(context, ACCESS_NETWORK_STATE);
        _internetAvailableCallback = internetAvailableCallback;
    }

    @Override
    public IntentFilter getIntentFilter() {
        return intentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    }

    @Override
    @RequiresPermission(ACCESS_NETWORK_STATE)
    public final void onReceive(Context context, Intent intent) {
        checkPermission(context, ACCESS_NETWORK_STATE);
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            _internetAvailableCallback.call(wifi.isAvailable() || mobile.isAvailable());
        }
    }

}
