package ie.moses.caimito.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.RequiresPermission;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static ie.moses.caimito.android.PermissionUtils.checkPermission;

public final class InternetUtils {

    private InternetUtils() {
    }

    @RequiresPermission(ACCESS_NETWORK_STATE)
    public static boolean isInternetAvailable(Context context) {
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
