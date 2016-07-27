package com.tedkim.android.tutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Class collection of intent action
 * Created by Ted
 */
public class IntentActionUtils {

    private static final String TAG = IntentActionUtils.class.getName();

    /**
     * Default Intent Action
     *
     * @param activity    activity
     * @param clazz       move to class
     * @param requestCode request code
     */
    public static void intentDefault(Activity activity, Class clazz, int requestCode) {
        log.d(TAG, "[intentDefault] class : " + clazz.getSimpleName());
        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.activity_transition_start_enter, R.anim.activity_transition_start_exit);
    }

    /**
     * Main screen intent action
     *
     * @param activity    activity
     * @param requestCode result code
     */
    public static void intentMain(Activity activity, Class clazz, int requestCode) {
        log.d(TAG, "[intentMain]");
        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.activity_transition_start_enter, R.anim.activity_transition_start_exit);
    }

    /**
     * External URL intent action
     *
     * @param context context
     * @param url     intent action url
     */
    public static void intentExternalURL(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(intent);
    }
}
