package com.example.pc1.filescanning.UI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pc1.filescanning.Bean.AppData;
import com.example.pc1.filescanning.Bean.Filepo;
import com.example.pc1.filescanning.Util.DeleteFileUtil;

import java.io.File;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView ll;
    ArrayList<Filepo> list;
    AppData appData;
    Filepo filepo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ll = findViewById(R.id.lv);
        LoadList();
    }
    private void LoadList(){
        appData = (AppData)this.getApplication();
        list = appData.getHm().get(getIntent().getStringExtra("listdata"));
        if(list!=null){
            ll.setAdapter(new MyAdepter());
        }else{
            TextView ts = findViewById(R.id.none);
            ts.setVisibility(View.VISIBLE);
        }
    }


    class MyAdepter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null){
                convertView = View.inflate(ListActivity.this,R.layout.listview_item,null);
                holder = new ViewHolder();
                holder.im = convertView.findViewById(R.id.img);
                holder.name = convertView.findViewById(R.id.name);
                holder.info = convertView.findViewById(R.id.info);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            Filepo f = list.get(position);
            holder.im.setImageDrawable(f.getImage());
            holder.name.setText(f.getName());
            holder.info.setText(f.getModifytime()+" "+f.getSize());
            convertView.setOnTouchListener(new MyTouchListener( f ));
            return convertView;
        }
    }
    static class ViewHolder{
        public ImageView im;
        public TextView name;
        public TextView info;
    }
    class MyTouchListener implements  View.OnTouchListener {
        private Filepo pojo = null;
        public MyTouchListener(Filepo pojo) {
            //得到此次操作的文件
            this.pojo = pojo;
        }
        //判断移动的距离
        float startPositionX = 0.0F;
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN ) {
                startPositionX = motionEvent.getX();
            }else if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                float endPositionX = motionEvent.getX();
                float diff = endPositionX - startPositionX;
                if( diff > 100 ) {
                    //删除事件
                    Delete();
                }else{
                    //点击事件
                    OnClick();
                }
            }
            return true;
        }

        private void Delete(){
            new AlertDialog.Builder(ListActivity.this)
                    .setTitle("系统提示")
                    .setMessage("是否要删除此文件")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(pojo.getType().equals("apk")){
                                //调用apk卸载函数
                                filepo = pojo;
                                startActivity(new Intent(Intent.ACTION_DELETE,
                                        Uri.fromParts("package", pojo.getPackageName(),null)));
                                Log.i("system.out","这是个apk");
                            }else{
                                updateList(DeleteFileUtil.delete(pojo.getUrl()),pojo);
                                Log.i("system.out","这不是apk");
                            }

                        }
                    })
                    .setNegativeButton("否", null)
                    .show();
        }

        private void OnClick(){
            Filepo filepo = pojo;
            startActivity(filepo.getIntent());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("system.out","卸载界面结束");
        if(filepo == null)
            Log.i("system.out","没有获取到所要删除的文件");
        else
            Log.i("system.out",filepo.getName());
        updateList(appData.checkApk(filepo),filepo);
        filepo = null;
    }

    //      删除后更新列表
    private void updateList(boolean flag,Filepo pojo){
        if(flag){
            list.remove(pojo);
            ll.setAdapter(new MyAdepter());
            Log.i("system.out","删除成功");
        }else{
            Log.i("system.out","删除失败");
        }
    }
}
