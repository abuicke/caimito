package ie.moses.caimito.android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

public final class PermissionUtils {

    private PermissionUtils() {
    }

    public static void checkPermission(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
            throw new SecurityException("missing permission " + permission);
        }
    }

}
