package com.github.braully.util;

import com.github.braully.persistence.IEntity;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author braullyrocha
 */
public class UtilValidation {

    public static synchronized boolean hasData(Collection collection) {
        return collection != null && !collection.isEmpty();
    }

    public static synchronized boolean hasData(Map collection) {
        return collection != null && !collection.isEmpty();
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static synchronized boolean isValid(Object value) {
        if (value instanceof String) {
            return isStringValid((String) value);
        }
        return value != null;
    }

    public static boolean isStringValid(String... strs) {
        boolean ret = false;
        if (strs != null && strs.length > 0) {
            ret = true;
            for (String str : strs) {
                if (str == null || str.trim().isEmpty()) {
                    ret = false;
                    break;
                }
            }
        }
        return ret;
    }

    public static boolean isPersisted(IEntity ent) {
        if (ent == null) {
            return false;
        }
        return ent.isPersisted();
    }

    public static boolean is(Boolean ret) {
        if (ret == null) {
            return false;
        }
        return ret;
    }

    public static boolean is(String standalone) {
        if (standalone == null) {
            return false;
        }
        boolean ret = false;
        try {
            ret = Boolean.valueOf(standalone);
        } catch (Exception e) {

        }
        return ret;
    }

    public static boolean isNotNull(Object... os) {
        if (os == null) {
            return false;
        }
        for (Object o : os) {
            if (o == null) {
                return false;
            }
        }
        return true;
    }
}
