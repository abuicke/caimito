package ie.moses.caimito.draw;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.FloatRange;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkArgument;

public final class MeasurementUtils {

    private MeasurementUtils() {
    }

    public static float dpiToPixels(Context context, @FloatRange(from = 0.0F) float dpi) {
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi, displayMetrics);
    }

    public static float pixelsToDpi(Context context, @FloatRange(from = 0.0F) float pixels) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return pixels / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

}
