package ie.moses.caimito;

import android.support.annotation.IntDef;
import android.util.Log;

@IntDef({Log.VERBOSE, Log.DEBUG, Log.INFO, Log.WARN, Log.ERROR})
public @interface LogLevel {
}
