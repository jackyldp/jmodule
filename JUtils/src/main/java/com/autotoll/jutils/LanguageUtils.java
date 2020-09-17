package com.autotoll.jutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;


import java.util.Locale;

/**
 * <p>
 * Describe:
 */
public class LanguageUtils {
    public final static String LANG_EN="en";
    public final static String LANG_TC="tc";
    public final static String LANG_SC="sc";

    /**
     * 使用：
     * application：
     *     @Override
     *     protected void attachBaseContext(Context base) {
     *         super.attachBaseContext(LanguageUtils.loadLanguage(base));
     *     }
     *
     *     @Override
     *     public void onConfigurationChanged(Configuration newConfig) {
     *         super.onConfigurationChanged(newConfig);
     *         LanguageUtils.loadLanguage(this.getBaseContext());
     *     }
     * BaseActivity:
     *      @Override
     *     protected void attachBaseContext(Context base) {
     *         super.attachBaseContext(LanguageUtils.loadLanguage(base));
     *
     *     }
     *
     *
     */

    public static Locale getCurrentLocale(Context ctx){
        String lang =getLang(ctx);

        Log.d("language","### loadLanguage current:"+lang);
        if("".equals(lang)){
            lang=getDefaultLanguage(ctx);
        }
        saveLang(ctx,lang);

        return getLocale(lang);
    }

    private static Locale getLocale(String lang){
        Locale locale ;

        if(LANG_SC.equals(lang)){
            locale=Locale.SIMPLIFIED_CHINESE;
        }else if(LANG_EN.equals(lang)){
            locale=Locale.ENGLISH;
        }else{
            locale=Locale.TRADITIONAL_CHINESE;
        }

        return locale;
    }


    public static String getDefaultLanguage(Context ctx){
        Locale locale = getLocale(ctx);
        String lang = LANG_TC;
        if (locale.getLanguage().toLowerCase().equals("zh")) {
            if(locale.getCountry().toLowerCase().equals("cn")){
                lang=LANG_SC;
            }else{
                lang=LANG_TC;
            }
        } else if (locale.getLanguage().toLowerCase().equals("en")) {
            lang=LANG_EN;
        }else{
            lang=LANG_TC;
        }
        Log.d("language","### getDefaultLanguage lang:"+lang);

        return lang;

    }
    /**
     * 获取手机设置的语言国家
     * @param context
     * @return
     */
    public static Locale getLocale(Context context) {

        Locale locale;
        Resources resources = context.getResources();
        //在7.0以上和7.0一下获取国家的方式有点不一样
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //  大于等于24即为7.0及以上执行内容
            locale = resources.getConfiguration().getLocales().get(0);
        } else {
            //  低于24即为7.0以下执行内容
            locale = resources.getConfiguration().locale;
        }

        return locale;
    }

    public static void setCurrentLang(Context ctx,String lang){
        Locale locale = getLocale(lang);
        updateResources(ctx,locale);
        saveLang(ctx,lang);
    }


    public static String getCurrentLang(Context ctx){
        return getLang(ctx);
    }

    public static Context loadLanguage(Context context) {
        return updateResources(context, getCurrentLocale(context));
    }

    private static Context updateResources(Context context, Locale locale) {

        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(locale);
        } else {
            configuration.locale = locale;
        }
//		KLog.i("=====>更新App设置的语言 = " + locale.toString());
//        configuration.fontScale=fontScale;
        // updateConfiguration
        DisplayMetrics dm = resources.getDisplayMetrics();
        resources.updateConfiguration(configuration, dm);
        return context;
    }

    public static void saveLang(Context context,String lang){
        Log.d("language","### saveLanguage lang:"+lang);
        SharedPreferences sp = context.getSharedPreferences("SP_LANGUAGE",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("language",lang);
        editor.commit();
    }
    public static String getLang(Context context){
        SharedPreferences sp = context.getSharedPreferences("SP_LANGUAGE",
                Context.MODE_PRIVATE);
        return sp.getString("language","");
    }

}
