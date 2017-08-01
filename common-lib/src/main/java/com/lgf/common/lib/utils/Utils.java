package com.lgf.common.lib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.util.UUID;

import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by Geroff on 2017/8/1.
 */

public class Utils {
    /**
     * "model": "手机型号"
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * "manufacture": "手机厂商"
     */
    public static String getManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * "system": "Android 版本号"
     */
    public static String getAndroidSystemVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * "sdk": "Android SDK版本号"
     */
    public static int getAndroidSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * "imei": "国际移动设备标识"
     */
    public static String getImei(Context context) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
        }
        return "";
    }

    /**
     * "imsi": "国际移动用户识别码"
     */
    public static String getImsi(Context context) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        }

        return "";
    }

    /**
     * "phoneNumber": "手机号码"
     */
    public static String getPhoneNumber(Context context) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }

        return "";
    }

    /**
     * 获取手机uuid
     */
    public static String getDeviceUUID(Context context) {
        UUID uuid = null;
        String androidId = getAndroidId(context);
        // don't use wrong android id
        if (androidId.equals("9774d56d682e549e")) {
            String deviceId = getImei(context);
            if (TextUtils.isEmpty(deviceId)) {
                uuid = UUID.randomUUID();
            } else {
                uuid = UUID.nameUUIDFromBytes(deviceId.getBytes());
            }
        } else {
            uuid = UUID.nameUUIDFromBytes(androidId.getBytes());
        }
        return uuid.toString();
    }

    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getAndroidId(Context context) {
        String androidId = "9774d56d682e549e";
        if (context != null) {
            try {
                androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return androidId;
    }

    public static int getAppVersionCode(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                return pi.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static String getAppVersionName(Context context) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                return pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return "0.0.0";
    }
}
