package com.basicproject.demo.common.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.basicproject.demo.R;
import com.basicproject.demo.main.APP;


/**
 * 作者：薛
 * 时间：2017/4/17 09:36
 * 吐司工具类
 */
public  class  ToastUtil {

    private static Toast toast;

    /**
     * 自定义Toast，加载布局显示Toast(实例只有一个，只创建一次)
     */
    public static void  showViewToast(CharSequence text) {
        View v = LayoutInflater.from(APP.getContext()).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        if (toast == null) {
            toast = new Toast(APP.getContext());
            textView.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }else{
            textView.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }

    }


    /**
     * 引用String.xml
     */
    public static void  showViewToast(int resId) {
        View v = LayoutInflater.from(APP.getContext()).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        if (toast == null) {
            toast = new Toast(APP.getContext());
            textView.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }else{
            textView.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }

    }


    public static void  showViewToast(String  resId,int time) {
        View v = LayoutInflater.from(APP.getContext()).inflate(R.layout.toast_layout, null);
        TextView textView = (TextView) v.findViewById(R.id.textView1);
        if (toast == null) {
            toast = new Toast(APP.getContext());
            textView.setText(resId);
            toast.setDuration(time);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }else{
            textView.setText(resId);
            toast.setDuration(time);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.setView(v);
            toast.show();
        }

    }


    /**
     * 只显示一次Toast，只改变内容,避免多次创建
     */
    public static void  showToast( CharSequence content){
        if (toast==null){
            toast= Toast.makeText(APP.getContext(),content, Toast.LENGTH_SHORT);
        }else{
            toast.setText(content);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    /**
     * 引用String.xml
     */
    public static void  showToast(  String resId){
        if (toast==null){
            toast= Toast.makeText(APP.getContext(),resId, Toast.LENGTH_SHORT);
        }else{
            toast.setText(resId);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    /**
     * 普通的Toast
     */
    public static void makeText( String content){
        toast= Toast.makeText(APP.getContext(),content, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * 引用String.xml
     */
    public static void makeText(int resId){
        toast= Toast.makeText(APP.getContext(),resId, Toast.LENGTH_SHORT);
        toast.show();
    }


    //取消Toast
    public void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
