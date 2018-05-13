package com.example.karan.myapplication2;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v4.provider.FontRequest;
import android.support.v4.provider.FontsContractCompat;

/**
 * Created by karan on 5/10/2018.
 */

public class FontDownloader {
    private final DownloadTarget mTarget;
    private final Context mContext;
    private Handler mHandler;

    public FontDownloader(Context context, DownloadTarget target) {
        mTarget = target;
        mContext = context;
        initializeFonts();
    }

    private void initializeFonts() {
        // https://developers.google.com/fonts/docs/android
        String query = "name=Open Sans&weight=800&italic=0";
        FontRequest request =
                new FontRequest(
                        "com.google.android.gms.fonts",
                        "com.google.android.gms",
                        query,
                        R.array.com_google_android_gms_fonts_certs);

        FontsContractCompat.FontRequestCallback callback =
                new FontsContractCompat.FontRequestCallback() {
                    @Override
                    public void onTypefaceRetrieved(Typeface typeface) {
                        mTarget.applyFont(typeface);
                    }
                };
        FontsContractCompat.requestFont(mContext, request, callback, getHandlerThreadHandler());
    }


    private Handler getHandlerThreadHandler() {
        if (mHandler == null) {
            HandlerThread handlerThread = new HandlerThread("fonts");
            handlerThread.start();
            mHandler = new Handler(handlerThread.getLooper());
        }
        return mHandler;
    }

    public interface DownloadTarget {
        void applyFont(Typeface typeface);
    }
}
