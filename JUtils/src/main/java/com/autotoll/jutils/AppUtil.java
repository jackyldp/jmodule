package com.autotoll.jutils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * app相关辅助类
 */
public class AppUtil {
    /**
     * 获取应用程序名称
     *
     * @param context
     * @return
     */
    public static String getAppName(Context context) {

        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序版本名称信息
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序的版本Code信息
     *
     * @param context
     * @return 版本code
     */
    public static long getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getUDID(Context context) {
//        try {
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            if (tm.getDeviceId() == null || "".equals(tm.getDeviceId().trim())) {
//                return "android";
//            }
//            return tm.getDeviceId();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//        return "android";
        //google 推荐获取设备唯一ID标示的方式,主要是为了6.0以后用户拒绝授权READ_PHONE_STATE会出现问题的情况
        //androidID可能由于机型原因返回null
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean isEmail(String email) {
        if (email == null || "".equals(email)) {
            return true;
        }
        /*String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();*/
        String format = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(format, email);
    }
    /**
     * 判断应用是否在运行
     * @param context
     * @param packName 调用程序的包名，appid
     * @return
     */
    public static boolean isRun(String packName,Context context){
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();//getRunningTasks(100);
        boolean isAppRunning = false;
//        String MY_PKG_NAME ="com.autotoll.accesscontrol.app";
        //100表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行
        for (ActivityManager.RunningAppProcessInfo info : list) {
            if (info.processName.equals(packName) || info.importance== ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                isAppRunning = true;
                break;
            }
        }
        Log.i("ActivityService isRun()", "com.ad 程序  ...isAppRunning......"+isAppRunning);
        return isAppRunning;
    }
}