package com.gravitycode.caimito.java.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;

import com.gravitycode.caimito.R;

/**
 * There is currently a bug where setting <code>android:fontFamily</code>
 * in XML on a normal {@link android.widget.TextView} is not reliable.
 *
 * @deprecated Just download the TFF files directly into your project.
 */
@Deprecated
public class FontCompatTextView extends AppCompatTextView {

    private final static String TAG = FontCompatTextView.class.getSimpleName();

    public FontCompatTextView(Context context) {
        this(context, null);
    }

    public FontCompatTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        Resources.Theme theme = context.getTheme();
        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.FontCompatTextView, 0, 0);
        int fontRes = a.getResourceId(R.styleable.FontCompatTextView_font, -1);
        if (fontRes != -1) {
            try {
                Typeface typeface = ResourcesCompat.getFont(context, fontRes);
                setTypeface(typeface);
            } catch (Resources.NotFoundException e) {
                Log.e(TAG, "call to ResourcesCompat.getFont(" + context + ", " + fontRes + ") failed");
            }
        }
    }

}
