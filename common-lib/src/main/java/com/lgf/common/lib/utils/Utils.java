package com.lgf.common.lib.utils;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.graphics.drawable.DrawableCompat;
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
     * 获取手机uuid,相当于加密androidId
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
     * @return
     */
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
     * @return
     */
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
