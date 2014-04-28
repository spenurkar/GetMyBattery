package com.doctor.battery.utils;

import java.io.File;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

import com.doctor.battery.utils.ExecShell.SHELL_CMD;

/** @author Kevin Kowalewski */
public class Root {

    private static String LOG_TAG = Root.class.getName();

    public static boolean isDeviceRooted(Context ctx) {
        return checkRootMethod1() || checkRootMethod2() || checkRootMethod3() || checkRootMethod4(ctx);
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static  boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            return file.exists();
        } catch (Exception e) {} return false;
    }

    private static  boolean checkRootMethod3() {
        return new ExecShell().executeCommand(SHELL_CMD.check_su_binary)!=null;
    }
    
    private static boolean checkRootMethod4(Context context) {
        return isPackageInstalled("eu.chainfire.supersu", context);     
    }

    private static boolean isPackageInstalled(String packagename, Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}   