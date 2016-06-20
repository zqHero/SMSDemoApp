package com.zhaoq.smsdemoapp;

import java.util.HashMap;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

public class MainActivity extends Activity {
	
	private Button mBtnBindPhone;
	
	private static String APPKey = "140c0b92a4dd8";
	private static String APPScrate="2920ea2910440cc7c603610fed58212b";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//初始化
		SMSSDK.initSDK(getApplicationContext(), APPKey, APPScrate);
		
		mBtnBindPhone = (Button) this.findViewById(R.id.btn_bind_phone);
		
		//设置  点击事件：
		mBtnBindPhone.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//注册手机号码
				RegisterPage registerPage = new RegisterPage();
				//注册回调事件
				registerPage.setRegisterCallback(new EventHandler(){
					//事件完成后  调用：
					@Override
					public void afterEvent(int event, int result, Object data) {
						//判断   结果是否已经完成   如完成  获取数据data
						if(result == SMSSDK.RESULT_COMPLETE){
							//获取数据
							HashMap<String, Object> maps = (HashMap<String, Object>) data;
							//国家  手机号信息  
							String country = (String) maps.get("country");
							
							String phone = (String) maps.get("phone");
							
							//提交信息
							submitUserInfo(country, phone);
						}
					}
				});
				//显示注册界面
				registerPage.show(getApplicationContext());
			}
		});
	}
	
	/**
	 * 提交用户信息
	 * @param country
	 * @param phone
	 */
	public void submitUserInfo(String country,String phone){
		//提交信息   uid
		Random r = new Random();
		String uid = Math.abs(r.nextInt())+"";//用户id  随机生成
		String nickName = "zhaoq";//昵称
		
		SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
	}
	
}
