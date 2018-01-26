/* 
 * 文件名: DialogUtil.java
 * 包路径: com.dvp.androiddialog
 * 创建描述  
 *        创建人：田翔 
 *        创建日期：2012-10-8 下午02:38:05
 *        内容描述：
 * 修改描述  
 *        修改人：
 *        修改日期：
 *        修改内容:
 * 版本: V1.0   
 */
package com.basicproject.demo.common.utils;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.basicproject.demo.R;


/**
 * 功能描述: 对话框的工具类，提供快速创建对话框的方法
 */
public class DialogUtil {
	
	private static Integer dialogStyle;
	private static Integer dialogLayout;
	private static Integer dialogText;
	
	/**
	 * 功能描述: 根据参数生成对话框，并返回
	 * @param context 上下文对象
	 * @param title 标题
	 * @param message 对话框内容
	 * @param pButtonName 第一个button的名字
	 * @param pButtonListener 第一个button的监听器
	 * @param nButtonName 第二的button的名字
	 * @param nButtonListener 第二个button的监听器
	 * @param cancelable 手机中的返回键功能，true为默认，flase为禁止返回键的功能
	 * @return
	 */
	public static Dialog getNormalDialog(final Context context, String title, String message, View view, String pButtonName,
                                         OnClickListener pButtonListener, String nButtonName, OnClickListener nButtonListener, Boolean cancelable){
		//新建对话框提示是否退出
		Builder builder=new Builder(context);
		//设置标题
		if(title!=null){
			builder.setTitle(title);
		}
		//设置对话框内容
		if(message!=null){
			builder.setMessage(message);
		}
		//设置对话框中的view
		if(message==null && view!=null){
			builder.setView(view);
		}
		//设置第一个button
		if(pButtonName!=null && pButtonListener!=null){
			builder.setPositiveButton(pButtonName, pButtonListener);
		}
		//设置第二个button
		if(nButtonName!=null && nButtonListener==null){
			//点击取消不做任何反应
			builder.setNegativeButton(nButtonName, null);
		}else if(nButtonName!=null && nButtonListener!=null){
			//点击取消不做任何反应
			builder.setNegativeButton(nButtonName, nButtonListener);
		}
		//设置是否禁止返回键的功能
		if(cancelable!=null){
			builder.setCancelable(cancelable);
		}
		//生成对话框，并返回
		Dialog dialog=builder.create();
		return dialog;
	}
	
	/**
	 * 功能描述:	按指定主题和视图来创建圆形进度对话框
	 * @param context 上下文对象
	 * @param style	      对话框样式
	 * @param layout   进度条内容View
	 * @param textid	提示信息的TextView的id
	 * @param text		进度条提示信息
	 * @return	Dialog对象
	 */
	public static Dialog getRoundProgressBar(Context context, int style, int layout, int textid, String text) {
		if(dialogStyle==null){
			dialogStyle=style;
		}
		if(dialogLayout==null){
			dialogLayout=layout;
		}
		if(dialogText==null){
			dialogText=textid;
		}
		// 新建dialog，并设置主题
		Dialog dialog = new Dialog(context, dialogStyle);
		// 设置dialog显示的视图对象
		dialog.setContentView(dialogLayout);
		dialog.setCanceledOnTouchOutside(false);
		if(text!=null && !text.equals("")){
			TextView textView=(TextView)dialog.findViewById(dialogText);
			System.out.println("@@@@@@@@@@@@@@======="+textView);
			textView.setText(text);
		}
		//禁止back键取消进度条
		//dialog.setCancelable(false);
		// 返回dialog对象
		return dialog;
	}



	public static Dialog getRoundProgressBar(Context context) {
		dialogStyle= R.style.alert_dialog;
		// 新建dialog，并设置主题
		Dialog dialog = new Dialog(context, dialogStyle);
		// 设置dialog显示的视图对象
		LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=layoutInflater.inflate(R.layout.loading_layout,null);
		dialog.setContentView(view);
		//禁止back键取消进度条
		//dialog.setCancelable(false);
		// 返回dialog对象
		return dialog;
	}
	
	//
	
	
	
	/**
	 * 功能描述: 按指定主题和视图来创建圆形进度对话框
	 * @param context 上下文对象
	 * @param roundBar 进度条对象
	 * @param style		进度条风格
	 * @param layout	进度条布局
	 * @param textid	显示提示信息的TextView对象
	 * @param text		进度条提示信息
	 * @return	对话框对象
	 */
	public static Dialog setRoundProgressBar(Context context, Dialog roundBar, int style, int layout, int textid, String text) {
		if(roundBar==null){
			getRoundProgressBar(context, style, layout, textid, text);
		}else{
			if(text!=null && !text.equals("")){
				TextView textView=(TextView)roundBar.findViewById(textid);
				textView.setText(text);
			}
		}
		//禁止back键取消进度条
		//dialog.setCancelable(false);
		// 返回dialog对象
		return roundBar;
	}
	
	//薛 add  禁止取消  点击屏幕取消不掉    点击back键可以---------------------------------------------------
	
	public static Dialog setRoundProgressBarNotCancle(Context context, Dialog roundBar, int style, int layout, int textid, String text) {
		if(roundBar==null){
			getRoundProgressBar(context, style, layout, textid, text);
		}else{
			if(text!=null && !text.equals("")){
				TextView textView=(TextView)roundBar.findViewById(textid);
				textView.setText(text);
			}
		}
		//禁止back键取消进度条
		//roundBar.setCancelable(false);
		roundBar.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失 
		// 返回dialog对象
		return roundBar;
	}
	
	
	public static Dialog setRoundProgressBarNotCancleNotBack(Context context, Dialog roundBar, int style, int layout, int textid, String text) {
		if(roundBar==null){
			getRoundProgressBar(context, style, layout, textid, text);
		}else{
			if(text!=null && !text.equals("")){
				TextView textView=(TextView)roundBar.findViewById(textid);
				textView.setText(text);
			}
		}
		//禁止back键取消进度条
		roundBar.setCancelable(false);
		roundBar.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失 
		// 返回dialog对象
		return roundBar;
	}
	
	


	public  static Toast toast;//定义一个全局吐司  ，如果为空的情况才让他创建  避免多次创建   看着麻烦
	public static void showSingleToas(Context context, String content){
		if (toast==null){
			toast= Toast.makeText(context,content, Toast.LENGTH_SHORT);
		}else{
			toast.setText(content);
			toast.setDuration(Toast.LENGTH_SHORT);
		}
		toast.show();
	}


	public static AlertDialog.Builder showNetWorkDialog(Context context){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(R.string.tips);
		builder.setMessage(R.string.download_tips);
		builder.setNegativeButton(R.string.cancel, null);
		Dialog dialog = builder.create();
		dialog.setCanceledOnTouchOutside(false);
		return builder;
	}



	public static AlertDialog.Builder showDialog(Context context){
		AlertDialog.Builder builders=new AlertDialog.Builder(context);
		///AlertDialog alertDialog = builders.create();
		return builders;
	}


}
