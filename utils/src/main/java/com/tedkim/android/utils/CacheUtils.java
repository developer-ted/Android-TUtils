package com.tedkim.android.utils;

import android.content.Context;
import android.text.format.Formatter;

import java.io.File;

/**
 * Class collection of app cache
 * Created by Ted
 */
public class CacheUtils {

    /**
     * Get cache size
     * return Human Readable (B, KB, MB ,...)
     *
     * @param context context
     * @return cache size (human readable)
     */
    public static String getCacheSize(Context context) {
        return Formatter.formatShortFileSize(context, getDirSize(context.getCacheDir()));
    }

    /**
     * Get cache size
     * return byte
     *
     * @param dir file
     * @return cache size (byte)
     */
    public static long getDirSize(File dir) {
        long size = 0;
        for (File file : dir.listFiles()) {
            if (file != null && file.isDirectory()) {
                size += getDirSize(file);
            } else if (file != null && file.isFile()) {
                size += file.length();
            }
        }
        return size;
    }

    /**
     * clear cache
     *
     * @param context context
     */
    public static void clearCache(Context context) {
        deleteDir(context.getCacheDir());
    }

    /**
     * delete cache
     * @param dir file
     * @return delete success or failure
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir != null && dir.delete();
    }

}
