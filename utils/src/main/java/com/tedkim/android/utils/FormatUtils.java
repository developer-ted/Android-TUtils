package com.tedkim.android.utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Class collection of format
 * Created by Ted
 */
public class FormatUtils {

    private static final String TAG = FormatUtils.class.getSimpleName();

    /**
     * Email validation
     *
     * @param target char sequence
     * @return true/false
     */
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Convert HashMap to json
     */
    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    /**
     * Convert HashMap to json
     */
    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    /**
     * Convert list to json
     */
    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    /**
     * Make json to object
     *
     * @param obj vo object
     * @return Json String
     */
    public static String toJson(Object obj) {
        return new Gson().toJson(obj);
    }

    /**
     * Make a vo to json object
     *
     * @param object json object
     * @param clazz  convert class
     * @return vo object
     */
    public static Object toJsonObject(Object object, Class clazz) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(json).getAsJsonObject();
        return gson.fromJson(jsonObject, clazz);
    }

    /**
     * Make a vo to json string
     *
     * @param json  json string
     * @param clazz convert class
     * @return vo object
     */
    public static Object toJsonString(String json, Class clazz) {
        Gson gson = new Gson();
        TypeAdapter adapter = gson.getAdapter(clazz);
        try {
            return adapter.fromJson(json);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get string term encoding UTF-8
     *
     * @param term term
     * @return encoding string term
     */
    public static String getEncodingTerm(String term) {
        String encodingTerm = "";

        if (term != null) {
            try {
                encodingTerm = URLEncoder.encode(term, "UTF-8").replace("+", "%20");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        log.d(TAG, "getEncodingTerm : encodingTerm = " + encodingTerm);
        return encodingTerm;
    }

    /**
     * Get string term decoding UTF-8
     *
     * @param term term
     * @return decoding string term
     */
    public static String getDecodingTerm(String term) {
        String decodingTerm = "";

        if (term != null) {
            try {
                decodingTerm = URLDecoder.decode(term, "UTF-8".replace("+", "%20"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        log.d(TAG, "getDecodingTerm : decodingTerm = " + decodingTerm);
        return decodingTerm;
    }

    /**
     * Make a phone number format
     *
     * @param phoneNumber number
     * @return phone number format
     */
    public static String makePhoneNumber(String phoneNumber) {
        String regEx = "(\\d{2,3})(\\d{3,4})(\\d{4})";
        if (!Pattern.matches(regEx, phoneNumber)) return null;
        return phoneNumber.replaceAll(regEx, "$1-$2-$3");
    }

    /**
     * GET 방식으로 서버에 요청을 할 Url을 만들어주는 메소드
     *
     * @param list Map List
     * @return Url address
     */
    public static String getDirectUrlSortParams(Map<String, String> list) {
        StringBuilder sb = new StringBuilder();

        Iterator<String> paramsIterator = list.keySet().iterator();

        for (int i = 0; i < list.size(); i++) {
            String key = paramsIterator.next();
            String value = list.get(key);

            sb.append("&");
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

    /**
     * GET 방식으로 서버에 요청을 할 Url을 만들어주는 메소드
     *
     * @param list Map List
     * @return Url address
     */
    public static String getDirectUrlParams(Map<String, String> list) {
        StringBuilder sb = new StringBuilder();

        Iterator<String> paramsIterator = list.keySet().iterator();

        for (int i = 0; i < list.size(); i++) {
            String key = paramsIterator.next();
            String value = list.get(key);

            if (i == 0) {
                sb.append("?");
            } else {
                sb.append("&");
            }
            sb.append(key);
            sb.append("=");
            sb.append(value);
        }
        return sb.toString();
    }

}
