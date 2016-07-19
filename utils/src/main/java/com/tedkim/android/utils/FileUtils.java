package com.tedkim.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Class collection of file
 * Created by Ted
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * URI 로 PATH 를 반환하는 메소드
     */
    public static String getPathFromURI(Context context, Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            //HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            //THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            log.d(TAG, "- PATH = " + filePath);

            return filePath;
        } else {
            log.d(TAG, "- PATH = " + uri.getPath());
            return uri.getPath();               // FOR OI/ASTRO/Dropbox etc
        }
    }


    /**
     * 비트맵을 파일형태로 변환하는 메소드
     */
    public static File getFileFromBitmap(Bitmap bitmap, String path) {
        log.d(TAG, "getFileFromBitmap : path = " + path);
        File fileCacheItem = new File(path);
        OutputStream out = null;

        try {
            fileCacheItem.createNewFile();
            out = new FileOutputStream(fileCacheItem);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            return fileCacheItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
