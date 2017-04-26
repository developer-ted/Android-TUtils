package com.tedkim.android.tutils;


import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;

import static android.content.Context.CLIPBOARD_SERVICE;

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
        clipBoard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
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

    public static void setClipboardText(Context context, String text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
    }

    /**
     * Get clipboard text
     *
     * @param context context
     * @return clipboard text
     */
    public static String getClipboardText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
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
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
        if (clipboardManager.hasPrimaryClip()) {
            ClipData clipData = ClipData.newPlainText(null, "");
            clipboardManager.setPrimaryClip(clipData);
        }
    }
}
