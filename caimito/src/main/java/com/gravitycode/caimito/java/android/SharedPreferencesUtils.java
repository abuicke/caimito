package com.gravitycode.caimito.java.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.common.base.Function;

import java.io.IOException;

public final class SharedPreferencesUtils {

    private SharedPreferencesUtils() {
    }

    /**
     * Checks to see if the specified {@code key} exists.
     *
     * @param context The context
     * @param key     The key to check
     * @return {@code true} if the key exists, {@code false} otherwise.
     */
    public static boolean hasKey(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.contains(key);
    }

    /**
     * Sets a value or values in the default shared preferences.
     *
     * @param context          The context
     * @param setValueFunction A function which is supplied with a {@link SharedPreferences.Editor}
     *                         to set the new values on, the function should call {@link SharedPreferences.Editor#commit()}
     *                         or {@link SharedPreferences.Editor#apply()} and return the result.
     * @throws IOException if {@code setValueFunction} returns {@code false}.
     */
    @SuppressWarnings("Guava")
    public static void set(Context context, Function<SharedPreferences.Editor, Boolean> setValueFunction) throws IOException {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean wasSuccessful = setValueFunction.apply(editor);
        if (!wasSuccessful) {
            throw new IOException("failed to write default shared preferences");
        }
    }

}
