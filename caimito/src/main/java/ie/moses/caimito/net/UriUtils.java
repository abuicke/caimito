package ie.moses.caimito.net;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;
import android.webkit.URLUtil;

import com.google.common.base.Preconditions;

import static com.google.common.base.Preconditions.checkArgument;

public final class UriUtils {

    private static final String TAG = UriUtils.class.getSimpleName();

    private static final String FACEBOOK_URL_REGEX = "https?://www\\.facebook\\.com(/|(.+/))?";

    private UriUtils() {
    }

    public static void openUri(Context context, String uriStr) {
        Uri uri = Uri.parse(uriStr);
        openUri(context, uri);
    }

    public static void openUri(Context context, Uri uri) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(browserIntent);
    }

    public static void openPhoneDialer(Context context, String phoneNumber) {
        checkArgument(phoneNumber.matches("\\+?[\\d-]+"),
                "phone number contains non-digit characters");
        openUri(context, "tel:" + phoneNumber);
    }

    public static void openMailClient(Context context, String email) {
        checkArgument(isValidEmail(email), email + " is not a valid email");
        openUri(context, "mailto:" + email);
    }

    public static void openUrl(Context context, String url) {
        checkIsValidUrl(url);
        openUri(context, url);
    }

    public static void openFacebookUrl(Context context, String urlStr) {
        checkIsValidUrl(urlStr);
        Preconditions.checkArgument(urlStr.matches(FACEBOOK_URL_REGEX),
                "\"" + urlStr + "\" is not a valid facebook url");
        try {
            Intent facebookIntent = getFacebookIntent(context, urlStr);
            context.startActivity(facebookIntent);
        } catch (PackageManager.NameNotFoundException nnfe) {
            Log.w(TAG, "failed to open facebook url in app, opening in browser...", nnfe);
            openUrl(context, urlStr);
        }
    }

    public static boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static void checkIsValidUrl(String urlStr) {
        checkArgument(URLUtil.isValidUrl(urlStr), "\"" + urlStr + "\" is not a valid url");
    }

    /**
     * @see <a href="https://stackoverflow.com/questions/34577864"/>
     */
    private static Intent getFacebookIntent(Context context, String urlStr) throws PackageManager.NameNotFoundException {
        PackageManager packageManager = context.getPackageManager();

        ApplicationInfo applicationInfo =
                packageManager.getApplicationInfo("com.facebook.katana", 0);
        if (applicationInfo.enabled) {
            Uri url = Uri.parse("fb://facewebmodal/f?href=" + urlStr);
            return new Intent(Intent.ACTION_VIEW, url);
        } else {
            // TODO: Should this use a different exception?
            throw new PackageManager.NameNotFoundException("package manager application info is disabled");
        }
    }

}
