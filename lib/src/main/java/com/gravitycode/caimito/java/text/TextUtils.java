package ie.moses.caimito.text;

import android.graphics.Paint;
import android.widget.TextView;

public final class TextUtils {

    private TextUtils() {
    }

    public static float getLineHeight(TextView textView) {
        Paint textPaint = textView.getPaint();
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        return fontMetrics.bottom - fontMetrics.top;
    }

}
