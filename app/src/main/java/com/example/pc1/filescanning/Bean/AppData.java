package com.example.pc1.filescanning.Bean;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.pc1.filescanning.UI.R;
import com.example.pc1.filescanning.Util.GetAppInfo;
import com.example.pc1.filescanning.Util.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AppData extends Application{
    public static HashMap<String,ArrayList<Filepo>> hm = new HashMap<>();
    private static String[] type = {"img","aud","vid","txt","zip","apk"};
    private File file = Environment.getExternalStorageDirectory();
    @Override
    public void onCreate() {
        super.onCreate();
        if(hm == null){
            hm = new HashMap<>();
            UpHm();
        }

    }

    public HashMap<String, ArrayList<Filepo>> getHm() {
        return hm;
    }

    public void UpHm() {
        hm.clear();
        getFile(file);
        getApk();
        Log.i("system.out","你获得了数据");
    }
    public boolean checkApk(Filepo filepo){
        if(filepo == null) return false;
        if(!new File(filepo.getUrl()).exists()){
            Log.i("system.out","文件卸载成功！");
            ArrayList<Filepo> list = hm.get("apk");
            if(list.remove(filepo)){
                hm.put("apk",list);
                Log.i("system.out","文件记录移除成功！");
                return true;
            }else{
                Log.i("system.out","出现了一个错误：不能从数组中移除该文件记录！");
                return false;
            }
        }
        return false;

    }
    private void getApk(){
        PackageManager packageManager = getPackageManager();
        List<PackageInfo> packageInfoList = packageManager.getInstalledPackages(0);
        GetAppInfo getAppInfo = new GetAppInfo(packageManager,packageInfoList);
        hm.put("apk", getAppInfo.getApps());
    }
    private void getFile(File file) {
        File[] files = file.listFiles();
        if(files == null){
            Log.i("System.out","file.path:"+file.getPath());
            return;
        }
        ArrayList<Filepo> list;
        for(File file1:files) {
            if(!file1.isHidden()) {
                if (file1.isDirectory()) {
                    getFile(file1);
                } else if (file1.isFile()) {
                    String str = file1.getName();
                    String[] ss = str.split("\\.");
                    Log.i("System.out.println", ss[1]);
                    Drawable drawable;
                    int id = -1;
                    switch (ss[ss.length - 1]) {
                        case "jpg":
                        case "png":
                        case "gif":
                            id = 0;
                            drawable = Drawable.createFromPath(file1.getPath());
                            break;
                        case "mp3":
                        case "wav":
                            drawable = this.getResources().getDrawable(R.drawable.file_audio);
                            id = 1;
                            break;
                        case "mp4":
                            drawable = this.getResources().getDrawable(R.drawable.file_videox);
                            id = 2;
                            break;
                        case "doc":
                        case "ppt":
                        case "xls":
                        case "pdf":
                        case "txt":
                            drawable = this.getResources().getDrawable(R.drawable.file_txt);
                            id = 3;
                            break;
                        case "zip":
                            drawable = this.getResources().getDrawable(R.drawable.file_zip);
                            id = 4;
                            break;
                        default: drawable = this.getResources().getDrawable(R.drawable.logo);
                            break;
                    }
                    if(id != -1){
                        list = hm.get(type[id]) == null ? new ArrayList<Filepo>() : hm.get(type[id]);
                        list.add(new Filepo(file1.getName(),type[id],file1.getPath(), Tools.getSize(file1.length()),drawable,Tools.getTime(file1.lastModified())));
                        hm.put(type[id], list);
                    }

                }
            }
        }
    }

}
