package com.tedkim.android.tutils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class collection of Encoding & Decoding
 * Created by Ted
 */
public class EncodeDecodeUtils {

    private static final String UTF_8 = "utf-8";

    /**
     * Base64 encoding
     *
     * @param content encoding string
     * @return Base64-encoded string
     */
    public static String getBase64encode(String content) {
        return Base64.encodeToString(content.getBytes(), 0);
    }

    /**
     * Base64 decoding
     *
     * @param content decoding string
     * @return Base64-decoded string
     */
    public static String getBase64decode(String content) {
        return new String(Base64.decode(content, 0));
    }

    /**
     * Base62 encoding
     * @param content encoding string
     * @return Base62-encoded string
     */
    public static String getBase62Encoding(int content) {
        final char[] BASE62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        final StringBuilder sb = new StringBuilder(1);
        do {
            sb.insert(0, BASE62[content % 62]);
            content /= 62;
        } while (content > 0);
        return sb.toString();
    }

    /**
     * URL Base64 decoding
     *
     * @param content encoding string
     * @return Base64-encoded url string
     */
    public static String getURLEncode(String content) {
        try {
            return URLEncoder.encode(content, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * URL Base64 decoding
     *
     * @param content decoding string
     * @return Base64-decoded url string
     */
    public static String getURLDecode(String content) {
        try {
            return URLDecoder.decode(content, UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * MD5 encoding
     *
     * @param content encoding string
     * @return MD5-encoded string
     */
    public static String getMD5encode(String content) {
        MessageDigest m = null;
        String hash = null;

        try {
            m = MessageDigest.getInstance("MD5");
            m.update(content.getBytes(), 0, content.length());
            hash = new BigInteger(1, m.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash;
    }
}
