package dev.gravitycode.caimito.java.draw;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.FloatRange;

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
