package com.example.pc1.filescanning.Util;

import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {
    private static String[] MS = {"午夜","深夜","清晨","早上","中午","下午","傍晚","晚上"};
    /*
    0:00-1:00
    1:00-5:00
    5:00-6:30
    6:30-12:00
    12:00-13:00
    13:00-17:30
    17:30-20:00
    20:00-24:00
    */
    public static String getTime(long time){
        Date tt = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        StringBuilder sb = new StringBuilder(sdf.format(tt));
        sb.append(" ");
        sb.append(getString(tt.getHours(),tt.getMinutes()));
        sdf = new SimpleDateFormat("h:mm");
        sb.append(sdf.format(time));
        return sb.toString();
    }
    private static String getString(int hh,int mm){
        int flag;
        if(hh>=0&&hh<1){
            flag = 0;
        }else if(hh>=1&&hh<5){
            flag = 1;
        }else if(hh==5||hh == 6 && mm<=30){
            flag = 2;
        }else if(hh == 6 && mm>30 ||hh>6&&hh<12){
            flag = 3;
        }else if(hh == 12){
            flag = 4;
        }else if(hh>12&hh<17||hh == 17 && mm<=30){
            flag = 5;
        }else if(hh == 17 && mm>=30 ||hh>17&&hh<=20){
            flag = 6;
        }else{
            flag = 7;
        }
        return MS[flag];
    }
    public static String getSize(long size){
        StringBuilder sb = new StringBuilder();
        int t = 0;
        double length = size;
        while(length>1000){
            length/=1000;
            t++;
        }
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        sb.append(nf.format(length));
        switch (t){
            case 0:sb.append("B");break;
            case 1:sb.append("kB");break;
            case 2:sb.append("MB");break;
            default:sb.append("GB");break;
        }
        return sb.toString();
    }

}
