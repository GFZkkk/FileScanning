package com.example.pc1.filescanning.Util;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.pc1.filescanning.Bean.Filepo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GetAppInfo {
    private List<PackageInfo> packageInfo;
    private PackageManager packageManager;
    private ArrayList<Filepo> list;

    public GetAppInfo(PackageManager packageManager, List<PackageInfo> packageInfo) {
        this.packageInfo = packageInfo;
        this.packageManager = packageManager;
        list = new ArrayList<Filepo>();
    }


    public ArrayList<Filepo> getApps() {
        for (PackageInfo info : packageInfo) {
            Filepo filepo = new Filepo();
            ApplicationInfo appInfo = info.applicationInfo;
            //去除系统应用
            if (!filterApp(appInfo)) {
                continue;
            }
            //拿到应用程序的图标
            filepo.setImage(appInfo.loadIcon(packageManager));
            //拿到应用程序的程序名
            filepo.setName(appInfo.loadLabel(packageManager).toString());
            //拿到应用程序的包名
            String packageName = appInfo.packageName;
            filepo.setPackageName(packageName);
            //拿到应用程序apk路径
            filepo.setUrl(appInfo.sourceDir);
            //获取应用程序启动意图
            filepo.setIntent(packageManager.getLaunchIntentForPackage(packageName));
            filepo.setSize(Tools.getSize(new File((info.applicationInfo.sourceDir)).length()));
            filepo.setModifytime(Tools.getTime(info.lastUpdateTime));
            filepo.setType("apk");
            list.add(filepo);
        }
        return list;
    }
    public boolean filterApp(ApplicationInfo info) {
        //有些系统应用是可以更新的，如果用户自己下载了一个系统的应用来更新了原来的，它还是系统应用，这个就是判断这种情况的
        if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return true;
            //判断是不是系统应用
        } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            return true;
        }
        return false;
    }
}
