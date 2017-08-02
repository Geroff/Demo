package com.lgf.common.lib.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Geroff on 2017/8/2.
 */

public class GlobalField {
    private static SharedPreferences getSharedPreference(Context context) {
        SharedPreferences sharedPreferences = context == null ? null : context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    public static void saveField(Context context, String key, long value) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putLong(key, value);
                editor.apply();
            }
        }
    }

    public static long restoreFieldLong(Context context, String key, long defaultValue) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        long value = sharedPreferences == null ? defaultValue : sharedPreferences.getLong(key, defaultValue);
        return value;
    }

    public static void saveField(Context context, String key, int value) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putInt(key, value);
                editor.apply();
            }
        }
    }

    public static int restoreFieldInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        int value = sharedPreferences == null ? defaultValue : sharedPreferences.getInt(key, defaultValue);
        return value;
    }

    public static void saveField(Context context, String key, float value) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putFloat(key, value);
                editor.apply();
            }
        }
    }

    public static float restoreFieldInt(Context context, String key, float defaultValue) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        float value = sharedPreferences == null ? defaultValue : sharedPreferences.getFloat(key, defaultValue);
        return value;
    }

    public static void saveField(Context context, String key, String value) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putString(key, value);
                editor.apply();
            }
        }
    }

    public static String restoreFieldString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        String value = sharedPreferences == null ? defaultValue : sharedPreferences.getString(key, defaultValue);
        return value;
    }

    public static void saveField(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (editor != null) {
                editor.putBoolean(key, value);
                editor.apply();
            }
        }
    }

    public static boolean restoreFieldBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = GlobalField.getSharedPreference(context);
        boolean value = sharedPreferences == null ? defaultValue : sharedPreferences.getBoolean(key, defaultValue);
        return value;
    }
}
