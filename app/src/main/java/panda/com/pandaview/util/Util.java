package panda.com.pandaview.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static boolean RELEASE = true;
    public static boolean DEBUG = false;
    public static String mDebugTag = "PPNailDebug";
    public static String mNetworkDebugTag = "PPNailNetworkDebug";

    private static int mScreenWidth = 0;
    private static int mScreenHeight = 0;

    public static void setEditTextLeftCompound(TextView edit, Context context, int resID, int size) {
        Drawable drawable = context.getResources().getDrawable(resID);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        edit.setCompoundDrawablePadding(dp2px(context, 15));
        edit.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setEditTextRightCompound(TextView edit, Context context, int resID, int size) {
        Drawable drawable = context.getResources().getDrawable(resID);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        edit.setCompoundDrawablePadding(dp2px(context, 5));
        edit.setCompoundDrawables(null, null, drawable, null);
    }

    public static int getScreenWidth(Context context) {
        if (mScreenWidth == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenWidth = dm.widthPixels;
        }

        return mScreenWidth;
    }

    public static int getScreenHeight(Context context) {
        if (mScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenHeight = dm.heightPixels;
        }

        return mScreenHeight;
    }

    public static int getScreenHeight(Dialog dialog) {
        if (mScreenHeight == 0) {
            DisplayMetrics dm = new DisplayMetrics();
            dialog.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
            mScreenHeight = dm.heightPixels;
        }
        return mScreenHeight;
    }

    public static int px2Dip(int px, Context context) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, r.getDisplayMetrics());
    }

    public static int dp2px(Context context, float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static void log(String message) {
        if (DEBUG) {
            int maxLogSize = 1000;
            for (int i = 0; i <= message.length() / maxLogSize; i++) {
                int start = i * maxLogSize;
                int end = (i + 1) * maxLogSize;
                end = end > message.length() ? message.length() : end;
                Log.i(Util.mDebugTag, message.substring(start, end));
            }
        }
    }

    public static void logNetwork(String message) {
        int maxLogSize = 1000;
        for (int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i + 1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Log.i(Util.mNetworkDebugTag, message.substring(start, end));
        }
    }

    public static void displayToastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void displayToastLong(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    // 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
    public static boolean hasBind(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        String flag = sp.getString("bind_flag", "");
        if ("ok".equalsIgnoreCase(flag)) {
            return true;
        }

        return false;
    }

    public static void setBind(Context context, boolean flag, String appid, String userId, String channelId) {
        String flagStr = "not";
        if (flag) {
            flagStr = "ok";
        }
        String responseString = "setBind flag=" + flag + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId;
        Log.d(Util.class.getSimpleName(), responseString);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("bind_flag", flagStr);
        editor.putString("appid", appid);
        editor.putString("channel_id", channelId);
        editor.putString("user_id", userId);
        editor.commit();
    }

    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(),
                            PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return apiKey;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideInput(Activity context) {
        InputMethodManager input = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (input == null) return;
        View focus = context.getCurrentFocus();
        if (focus == null) return;
        input.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
    /*
     * 可接受的电话格式有：
     */
        String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{5})$";
    /*
     * 可接受的电话格式有：
     */
        String expression2 = "^\\(?(\\d{3})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";

        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);

        Pattern pattern2 = Pattern.compile(expression2);
        Matcher matcher2 = pattern2.matcher(inputStr);
        if (matcher.matches() || matcher2.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public static boolean isAppOnForeground(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = manager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // 应用程序位于堆栈的顶层
            String src = context.getPackageName();
            String des = tasksInfo.get(0).topActivity.getPackageName();
            if (src.equalsIgnoreCase(des)) {
                return true;
            }
        }
        return false;
    }

    public static String getChannel(Context context) {
        String channel = "android";
        ApplicationInfo ai;
        try {
            ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            channel = ai.metaData.get("CHANNEL").toString();
        } catch (PackageManager.NameNotFoundException e) {
        }

        return channel;
    }

    public static String getUID(Context context) {
        //1 compute IMEI
        TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
        String m_szImei = TelephonyMgr.getDeviceId(); // Requires READ_PHONE_STATE

        //2 compute DEVICE ID
        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits
        //3 android ID - unreliable
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);

        //4 wifi manager, read MAC address - requires  android.permission.ACCESS_WIFI_STATE or comes as null
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        String m_szWLANMAC = wm.getConnectionInfo().getMacAddress();

        //5 Bluetooth MAC address  android.permission.BLUETOOTH required
//        BluetoothAdapter m_BluetoothAdapter = null; // Local Bluetooth adapter
//        m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        String m_szBTMAC = m_BluetoothAdapter.getAddress();

        //6 SUM THE IDs
        String m_szLongID = m_szImei + m_szDevIDShort + m_szAndroidID + m_szWLANMAC;
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        byte p_md5Data[] = m.digest();

        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF) m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }
        m_szUniqueID = m_szUniqueID.toUpperCase();

        return m_szUniqueID;
    }

    private static long startTime = 0;

    public static void startTime() {
        startTime = System.nanoTime();
    }

    public static void endTime(String tag, String message) {
        long nowTime = System.nanoTime();
        long consumingTime = nowTime - startTime;

        Log.i(tag, message + consumingTime / 1000 + "微秒");
//        System.out.println(message + consumingTime);
//        System.out.println(message + consumingTime/1000 + "微秒");
        startTime = nowTime;
    }

    public static int stringToResID(Context context, String resourcesName, String type) {
        //获取layout的id
        return context.getResources().getIdentifier(resourcesName, type, context.getPackageName());
    }

    public static boolean isToday(Calendar cal) {
        return isSameDay(cal, Calendar.getInstance());
    }

    public static boolean isSameDay(Calendar cal1, Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        return (cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
    }

    public static Calendar getCalendarFromDateString(String date) {
        Calendar cal = Calendar.getInstance();
//        2015-03-25 11:01:30
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            cal.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();

            return null;
        }

        return cal;
    }

    /**
     * 将字符串全部改成全角
     *
     * @param input 半角字符串
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);
            }
        }
        return new String(c);
    }


    public static void saveObject(Context context, String name, Object sod) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(name, Activity.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(sod);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }


    public static Object getObject(Context context, String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }

    /**
     * 判断TextView是否超过rows行数
     *
     * @param textView
     * @param rows
     * @return 1是超过，0是不超过
     */
    public static int isManyRows(TextView textView, int rows) {
        Layout l = textView.getLayout();
        if (l != null) {
            int lines = l.getLineCount();
            if (lines > rows) {
                return 1;
            }
        }
        return 0;
    }
}
