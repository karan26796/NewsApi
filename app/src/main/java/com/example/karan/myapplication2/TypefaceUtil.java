package com.example.karan.myapplication2;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by karan on 5/12/2018.
 */

public class TypefaceUtil {
    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {

        try {

            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);

            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);

            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {
            //Log.e("Can not set custom font " + customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }
}
