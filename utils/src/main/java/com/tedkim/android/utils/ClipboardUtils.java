package com.tedkim.android.utils;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

/**
 * Class collection of Clipboard
 * Created by Ted
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ClipboardUtils {

    private static final String TAG = ClipboardUtils.class.getSimpleName();

    private ClipboardManager clipBoard;

    /**
     * Constructor
     */
    public ClipboardUtils(Context context) {
        clipBoard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        registerClipboardListener();
    }

    /**
     * Register Clipboard
     */
    public void registerClipboardListener() {
        if (clipBoard != null)
            clipBoard.addPrimaryClipChangedListener(mPrimaryClipChangedListener);
    }

    /**
     * Unregister Clipboard
     */
    private void unregisterClipboardListener() {
        if (clipBoard != null) {
            clipBoard.removePrimaryClipChangedListener(mPrimaryClipChangedListener);
            clipBoard = null;
        }
    }

    /**
     * Clipboard Manager Listener
     */
    ClipboardManager.OnPrimaryClipChangedListener mPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
        public void onPrimaryClipChanged() {
            ClipData clipData = clipBoard.getPrimaryClip();
        }
    };

    /**
     * Get clipboard text
     *
     * @param context context
     * @return clipboard text
     */
    public static String getClipboardText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = clipboardManager.getPrimaryClip();

            if (clipData.getDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
                // textDataOnly
                return clipData.getItemAt(0).getText().toString();
            }
        }
        return null;
    }

    /**
     * Clear clipboard text
     *
     * @param context context
     */
    public static void clearClipboardText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = ClipData.newPlainText(null, "");
            clipboardManager.setPrimaryClip(clipData);
        }
    }
}
