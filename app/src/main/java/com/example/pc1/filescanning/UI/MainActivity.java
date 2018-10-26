
package com.example.pc1.filescanning.UI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.pc1.filescanning.Bean.AppData;
import com.example.pc1.filescanning.Bean.Filepo;
import com.example.pc1.filescanning.Util.GetAppInfo;
import com.example.pc1.filescanning.Util.Tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    HashMap<String,ArrayList<Filepo>> hm ;
    String[] type = {"img","aud","vid","txt","zip","apk"};
    AppData appData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
        appData = (AppData)this.getApplication();
        LoadView();
    }
    private void LoadView(){
        hm = appData.getHm();
        TextView t1 = findViewById(R.id.ptnum);
        TextView t2 = findViewById(R.id.msnum);
        TextView t3 = findViewById(R.id.vdnum);
        TextView t4 = findViewById(R.id.txtnum);
        TextView t5 = findViewById(R.id.zipnum);
        TextView t6 = findViewById(R.id.appnum);
        t1.setText("("+(hm.get(type[0]) == null?0:hm.get(type[0]).size())+")");
        t2.setText("("+(hm.get(type[1]) == null?0:hm.get(type[1]).size())+")");
        t3.setText("("+(hm.get(type[2]) == null?0:hm.get(type[2]).size())+")");
        t4.setText("("+(hm.get(type[3]) == null?0:hm.get(type[3]).size())+")");
        t5.setText("("+(hm.get(type[4]) == null?0:hm.get(type[4]).size())+")");
        t6.setText("("+(hm.get(type[5]) == null?0:hm.get(type[5]).size())+")");
    }
    public void onClick(View v) {
        int id;
        switch (v.getId()){
            case R.id.pt:
                id = 0;
                break;
            case R.id.ms:
                id = 1;
                break;
            case R.id.vd:
                id = 2;
                break;
            case R.id.txt:
                id = 3;
                break;
            case R.id.zip:
                id = 4;
                break;
            case R.id.apks:
                id = 5;
                break;
            default:id = 0;
        }
        Log.i("system.out","你将转移到"+id);
        Intent intent = new Intent(this,ListActivity.class);
        intent.putExtra("listdata",type[id]);
        startActivity(intent);
    }
    //刷新界面
    @Override
    protected void onResume() {
        super.onResume();
        appData.UpHm();
        LoadView();
        Log.i("system.out","欢迎来到主界面");
    }
    //获得权限
    private void getPermission() {
        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    124);
        }
    }
}
