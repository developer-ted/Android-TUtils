package com.tedkim.android.tutils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class collection of network
 * Created by Ted
 */
public class NetworkUtils {

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;

    /**
     * Get current connect network status
     *
     * @param context context
     * @return Network connect status
     */
    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return TYPE_WIFI;
            }

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * Get current connect network string
     *
     * @param context context
     * @return Network connect status string
     */
    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        String status = null;

        switch (conn) {
            case TYPE_WIFI:
                status = "Wifi network enabled";
                break;
            case TYPE_MOBILE:
                status = "3G/4G network enabled";
                break;
            case TYPE_NOT_CONNECTED:
                status = "Not connected to Internet";
                break;
        }
        return status;
    }

    /**
     * Check network status
     *
     * @param context context
     * @return Network connect status
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Check wifi status
     *
     * @param context context
     * @return wifi status
     */
    public static boolean isWifiState(final Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        }
        return false;
    }

    /**
     * Check 3G/4G status
     *
     * @param context context
     * @return 3G/4G status
     */
    public static boolean isDataNetworkState(final Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        }
        return false;
    }
}
