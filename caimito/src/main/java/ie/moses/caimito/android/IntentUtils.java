package ie.moses.caimito.android;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import static com.google.common.base.Preconditions.checkArgument;

public final class IntentUtils {

    private IntentUtils() {
    }

    public static Intent intent(Context packageContext, Class<?> cls, String... extras) {
        checkArgument((extras.length % 2) == 0, "odd " +
                "number of arguments, the last key has no value");
        Intent intent = new Intent(packageContext, cls);
        for (int i = 0; i < extras.length; i += 2) {
            String key = extras[i];
            String value = extras[i + 1];
            intent.putExtra(key, value);
        }

        return intent;
    }

    public static IntentFilter intentFilter(String... actions) {
        IntentFilter intentFilter = new IntentFilter();
        for (int i = 0; i < actions.length; i++) {
            intentFilter.addAction(actions[i]);
        }

        return intentFilter;
    }

    public static void startActivity(Context context, Class<?> cls, String... extras) {
        Intent intent = intent(context, cls, extras);
        context.startActivity(intent);
    }

}
