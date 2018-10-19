package ie.moses.caimito.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;

import ie.moses.caimito.R;

/**
 * There is currently a bug where setting <code>android:fontFamily</code>
 * in XML on a normal {@link android.widget.TextView} is not reliable.
 */
public class FontCompatTextView extends android.support.v7.widget.AppCompatTextView {

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
            Typeface typeface = ResourcesCompat.getFont(context, fontRes);
            setTypeface(typeface);
        }
    }

}
