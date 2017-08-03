package com.lgf.common.lib.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
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
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static String getImei(Context context, String defaultValue) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            String imei = telephonyManager.getDeviceId();
            if (!TextUtils.isEmpty(imei)) {
                return imei;
            }
        }
        return defaultValue;
    }

    /**
     * "imsi": "国际移动用户识别码"
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static String getImsi(Context context, String defaultValue) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getSubscriberId();
        }

        return defaultValue;
    }

    /**
     * "phoneNumber": "手机号码"
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static String getPhoneNumber(Context context, String defaultValue) {
        if (context != null) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            return telephonyManager.getLine1Number();
        }

        return defaultValue;
    }

    /**
     * 获取手机uuid，相当于加密androidId
     */
    public static String getDeviceUUID(Context context) {
        String defaultValue = "9774d56d682e549e";
        UUID uuid = null;
        String androidId = getAndroidId(context, defaultValue);
        if (defaultValue.equals(androidId)) {
            String deviceId = getImei(context, null);
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

    /**
     * 获取随机的UUID
     *
     * @return
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 获取手机的androidId
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static String getAndroidId(Context context, String defaultValue) {
        if (context != null) {
            try {
                return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        return defaultValue;
    }

    /**
     * "mac": "手机MAC地址"
     */
    public static String getMacAddress(Context context) throws Throwable {
        if (Build.VERSION.SDK_INT >= 23) {
            return getMacByReflect();
        } else {
            if (context != null) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                return wifiInfo.getMacAddress();
            } else {
                return "00:00:00:00:00:00";
            }
        }
    }

    private static String getMacByReflect() throws Throwable {
        String mac = null;

        Class<?> clazz = Class.forName("android.os.SystemProperties");
        Method get = clazz.getMethod("get", String.class);
        String params = "wifi.interface";
        String interfaceName = (String) get.invoke(clazz, params);

        mac = getMacByName(interfaceName);
        if (TextUtils.isEmpty(mac)) {
            mac = getMacByFile(interfaceName);
        }

        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toLowerCase();
        }

        return mac;
    }

    private static String getMacByName(String interfaceName) throws Throwable {
        NetworkInterface networkInterface = NetworkInterface.getByName(interfaceName);
        byte[] addressBytes = networkInterface.getHardwareAddress();
        if (addressBytes == null || addressBytes.length == 0) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (byte addressByte : addressBytes) {
            stringBuilder.append(String.format("%02X:", addressByte));
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }

        return stringBuilder.toString();
    }

    private static String getMacByFile(String interfaceName) {
        StringBuilder buffer = new StringBuilder();
        String fileName = "/sys/class/net/" + interfaceName + "/address";
        InputStream is = null;
        BufferedReader br = null;
        try {
            is = new FileInputStream(new File(fileName));
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return buffer.toString();
    }

    /**
     * 获取应用的版本号
     *
     * @param context
     * @return
     */
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

    /**
     * 获取应用的版本
     *
     * @param context
     * @param defaultValue
     * @return
     */
    public static String getAppVersionName(Context context, String defaultValue) {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                return pi.versionName;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;
    }

    /**
     * 获取标签<meta-data>元数据的中name对应的value值
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    private String getTokenFromMetaData(Context context, String key, String defaultValue) {
        if (context == null) {
            return defaultValue;
        }

        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (applicationInfo != null) {
                Bundle metaData = applicationInfo.metaData;
                if (metaData != null) {
                    return metaData.getString(key);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    /**
     * 实现文本复制功能
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        if (context == null || TextUtils.isEmpty(content)) {
            return;
        }
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText("content", content));
    }

    /**
     * 实现粘贴功能
     *
     * @param context
     * @return
     */
    public static String paste(Context context) {
        if (context == null) {
            return "";
        }
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        return cmb.getPrimaryClip().toString().trim();
    }

    /**
     * 进入app的设置应用界面
     *
     * @param context
     */
    public static void gotoAppSetting(Context context) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 设置图片的颜色
     *
     * @param drawable
     * @param colors
     * @return
     */
    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    /**
     * 分辨率转换dp转px
     *
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        if (context == null) {
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
