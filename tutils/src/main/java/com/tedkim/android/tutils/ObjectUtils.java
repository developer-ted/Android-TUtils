package com.tedkim.android.tutils;

import java.util.List;
import java.util.Map;

public class ObjectUtils {

    public static boolean isEmpty(Object s) {
        if (s == null) {
            return true;
        }
        if ((s instanceof String) && (((String) s).trim().length() == 0)) {
            return true;
        }
        if (s instanceof Map) {
            return ((Map<?, ?>) s).isEmpty();
        }
        if (s instanceof List) {
            return ((List<?>) s).isEmpty();
        }
        return s instanceof Object[] && (((Object[]) s).length == 0);
    }
}
