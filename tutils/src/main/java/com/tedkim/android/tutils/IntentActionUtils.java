package com.tedkim.android.tutils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

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
     * Default Intent Action
     *
     * @param activity    activity
     * @param clazz       move to class
     * @param requestCode request code
     */
    public static void intentDefaultNoAnimation(Activity activity, Class clazz, int requestCode) {
        log.d(TAG, "[intentDefault] class : " + clazz.getSimpleName());
        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * Default intent back pressed action
     *
     * @param activity   activity
     * @param resultCode result code
     */
    public static void intentBackPressed(Activity activity, int resultCode) {
        Intent intent = new Intent();
        activity.setResult(resultCode, intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.activity_transition_end_enter, R.anim.activity_transition_end_exit);
    }

    /**
     * No animation intent back pressed action
     *
     * @param activity   activity
     * @param resultCode result code
     */
    public static void intentBackPressedNoAnimation(Activity activity, int resultCode) {
        Intent intent = new Intent();
        activity.setResult(resultCode, intent);
        activity.finish();
    }

    /**
     * Slide Intent Action
     *
     * @param activity    activity
     * @param clazz       move to class
     * @param requestCode request code
     */
    public static void intentSlide(Activity activity, Class clazz, int requestCode) {
        log.d(TAG, "[intentSlide] class : " + clazz.getSimpleName());
        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.activity_transition_slide_up, R.anim.activity_transition_not_changed);
    }

    /**
     * Slide Intent action
     *
     * @param activity   activity
     * @param resultCode result code
     */
    public static void intentBackPressedSlide(Activity activity, int resultCode) {
        log.d(TAG, "[intentSlide] class : " + activity.getClass().getSimpleName());
        Intent intent = new Intent();
        activity.setResult(resultCode, intent);
        activity.finish();
        activity.overridePendingTransition(R.anim.activity_transition_not_changed, R.anim.activity_transition_slide_down);
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
        activity.overridePendingTransition(R.anim.activity_transition_end_enter, R.anim.activity_transition_end_exit);
    }

    /**
     * Restart application
     *
     * @param activity MainActivity
     * @param clazz    class
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void restartApp(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.setAction("android.intent.action.MAIN");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * Restart activity & no animation
     *
     * @param activity activity
     */
    public static void restartActivity(Activity activity) {
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
        activity.overridePendingTransition(0, 0);
    }

    /**
     * External URL intent action
     *
     * @param activity context
     * @param url      intent action url
     */
    public static void intentExternalURL(Activity activity, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        activity.startActivity(intent);
    }

    /**
     * When Tab to change intent action
     *
     * @param activity    activity
     * @param clazz       class
     * @param requestCode request code
     */
    public static void intentTabChange(Activity activity, Class clazz, int requestCode) {
        Intent intentReward = new Intent(activity, clazz);
        intentReward.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivityForResult(intentReward, requestCode);
        activity.overridePendingTransition(0, 0);
    }

    /**
     * Intent google play store
     *
     * @param context content
     */
    public static void openAppInGooglePlay(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) { // if there is no Google Play on device
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
