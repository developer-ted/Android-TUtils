package com.tedkim.android.tutils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;


/**
 * Class collection of back pressed app close
 * Created by Ted
 */
public class BackPressedHandler {

    private static long backKeyPressedTime = 0;
    private static Toast toast;

    public static void onBackPressed(Context context) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(context, context.getString(R.string.toast_back_press_app), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            SystemUtils.onDestroyApp();
        }
    }

    public static void onBackPressedKillProcess(Activity activity) {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            toast = Toast.makeText(activity, activity.getString(R.string.toast_back_press_app), Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            toast.cancel();
            SystemUtils.killProcess(activity);
        }
    }
}
