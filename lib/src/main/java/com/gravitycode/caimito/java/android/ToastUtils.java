package ie.moses.caimito.android;

import android.content.Context;
import android.support.annotation.IntDef;
import android.widget.Toast;

public final class ToastUtils {

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
