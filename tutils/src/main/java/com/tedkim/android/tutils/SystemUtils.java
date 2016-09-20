package com.tedkim.android.tutils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.UUID;

/**
 * Class collection of android system
 * Created by Ted
 */
public class SystemUtils {

    private static final String TAG = SystemUtils.class.getSimpleName();

    /**
     * Get the current orientation of the screen
     * landscape : odd number(홀수), portrait : even number(짝수)
     *
     * @param context context
     * @return orientation
     */
    public static int getCurrentOrientation(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        return display.getRotation();
    }

    /**
     * Hide status bar
     *
     * @param activity activity
     */
    public static void hideStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);

        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Show status bar
     *
     * @param activity activity
     */
    public static void showStatusBar(Activity activity) {
        WindowManager.LayoutParams attrs = activity.getWindow().getAttributes();
        attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
        activity.getWindow().setAttributes(attrs);

        View decorView = activity.getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_VISIBLE;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    /**
     * Get status bar height
     *
     * @param context context
     * @return status bar height pixel
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * Get width pixel of the screen
     *
     * @param context context
     * @return width pixel
     */
    private static int getScreenWidthPx(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * Get height pixel of the screen
     *
     * @param context context
     * @return height pixel
     */
    private static int getScreenHeightPx(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * Get pixel short side of the screen
     *
     * @param context context
     * @return screen short side pixel
     */
    public static int getScreenWidth(Context context) {
        int width = getScreenWidthPx(context);
        int height = getScreenHeightPx(context);
        if (width > height) {
            return height;
        } else {
            return width;
        }
    }

    /**
     * Get pixel long side of the screen
     *
     * @param context context
     * @return screen long side pixel
     */
    public static int getScreenHeight(Context context) {
        int width = getScreenWidthPx(context);
        int height = getScreenHeightPx(context);
        if (width > height) {
            return width;
        } else {
            return height;
        }
    }

    /**
     * Get ratio of the screen
     *
     * @param context context
     * @return screen ratio
     */
    public static float getScreenRatio(Context context) {
        return (float) getScreenHeight(context) / (float) getScreenWidth(context);
    }

    /**
     * Get dp width of the screen
     *
     * @param context context
     * @return width dp
     */
    public static int getScreenWidthDp(Context context) {
        return ConvertUtils.convertPxToDp(context, context.getResources().getDisplayMetrics().widthPixels);
    }

    /**
     * Get dp height of the screen
     *
     * @param context context
     * @return height dp
     */
    public static int getScreenHeightDp(Context context) {
        return ConvertUtils.convertPxToDp(context, context.getResources().getDisplayMetrics().heightPixels);
    }

    /**
     * Get device country code
     *
     * @param context context
     * @return country code
     */
    public static String getDeviceCountryCode(Context context) {
        return String.valueOf(context.getResources().getConfiguration().locale.getCountry());
    }

    /**
     * Get device language
     *
     * @param context context
     * @return language code
     */
    public static String getDeviceLanguage(Context context) {
        return String.valueOf(context.getResources().getConfiguration().locale.toString());
    }

    /**
     * Show keyboard
     *
     * @param activity activity
     */
    public static void showKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
    }

    /**
     * Hide keyboard
     *
     * @param activity activity
     */
    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
        }
    }

    /**
     * Get mobile software version
     *
     * @return mobile software version
     */
    public static int getSoftWareVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Get auto rotate status
     *
     * @param context context
     * @return auto rotate/not auto rotate
     */
    public static boolean getAutoScreenRotateState(Context context) {
        boolean isAutoRotate;
        int rotateState = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION, 0);
        isAutoRotate = rotateState != 0;
        log.d(TAG, "getAutoScreenRotateState : isAutoRotate = " + isAutoRotate);
        return isAutoRotate;
    }

    /**
     * Get application icon
     *
     * @param context     context
     * @param packageName package name
     * @return application icon drawable
     */
    public static Drawable getApplicationIcon(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return context.getPackageManager().getApplicationIcon(applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get app version name
     *
     * @param context context
     * @return app version
     */
    public static String getAppVersionName(Context context) {
        String version = null;

        try {
            version = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return version;
    }

    /**
     * Get app version code
     *
     * @param context context
     * @return app version
     */
    public static int getVersionCode(Context context) {
        int v = 0;
        try {
            v = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * Check tablet
     *
     * @param context context
     * @return true : tablet, false : mobile
     */
    public static boolean isTablet(Context context) {
        return (isTabletPhoneType(context) || isTabletDevice(context));
    }

    /**
     * 테블릿인지 phone Type 확인하는 메소드
     *
     * @param context context
     * @return true : 테블릿, false : 모바일
     */
    private static boolean isTabletPhoneType(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        log.d(TAG, "isTablet : " + (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE));
        return (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE);
    }

    /**
     * 테블릿 디바이스인지 확인하는 메소드
     *
     * @param context context
     * @return true : 테블릿, false : 모바일
     */
    private static boolean isTabletDevice(Context context) {
        // Verifies if the Generalized Size of the device is XLARGE to be considered a Tablet
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);

        // If XLarge, checks if the Generalized Density is at least MDPI (160dpi)
        if (xlarge) {
            DisplayMetrics metrics = new DisplayMetrics();
            Activity activity = (Activity) context;
            activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

            // MDPI=160, DEFAULT=160, DENSITY_HIGH=240, DENSITY_MEDIUM=160,
            // DENSITY_TV=213, DENSITY_XHIGH=320
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT || metrics.densityDpi == DisplayMetrics.DENSITY_HIGH
                    || metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                log.d(TAG, "isTabletDevice : true");
                return true;
            }
        }
        log.d(TAG, "isTabletDevice : false");
        return false;
    }

    /**
     * Change fullscreen of the screen
     *
     * @param activity Activity
     */
    public static void setFullScreen(Activity activity) {
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    /**
     * Destroy app
     */
    public static void onDestroyApp() {
        System.exit(0);
    }

    /**
     * Process to shut down apps
     */
    public static void killProcess(Activity activity) {
        activity.moveTaskToBack(true);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * Request SMS
     *
     * @param activity    activity
     * @param phoneNumber send phone number
     * @param message     sms message
     */
    public static void requestSMS(Activity activity, String phoneNumber, String message) {
        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        sendIntent.putExtra("sms_body", message); // 보낼 문자
        sendIntent.putExtra("address", phoneNumber); // 받는사람 번호
        sendIntent.setType("vnd.android-dir/mms-sms");
        activity.startActivity(sendIntent);
    }

    /**
     * Request call
     *
     * @param activity    activity
     * @param phoneNumber phone number
     */
    public static void requestCall(Activity activity, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        activity.startActivity(intent);
    }

    /**
     * Get resource color
     *
     * @param context context
     * @param id      resource color id
     * @return color int
     */
    public static int getColor(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    /**
     * change application language
     *
     * @param context context
     * @param locale  locale
     */
    public static void changeLanguage(Context context, Locale locale) {
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    /**
     * Get device unique id
     *
     * @param context context
     * @return device unique id
     */
    public static String getDeviceUniqueID(Context context) {
        String DEVICE_UNIQUE_ID = "DEVICE_UNIQUE_ID";
        String id = Preferences.loadString(context, DEVICE_UNIQUE_ID, "");
        if (id.equals("")) {
            UUID uuid;
            final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            try {
                if (!"9774d56d682e549c".equals(androidId)) {
                    uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                } else {
                    final String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                    uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")) : UUID.randomUUID();
                }
                id = uuid.toString();
            } catch (UnsupportedEncodingException e) {
//            throw new RuntimeException(e);
                id = "";
            }
            Preferences.saveString(context, DEVICE_UNIQUE_ID, id);
        }

        log.d(TAG, "Device Unique ID : " + id);
        return id;
    }
}
