package com.kingame.launch.activity;

import com.kingame.mainfunction.activity.MainFunctionsActivity;
import com.kingame.mainfunction.activity.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class LaunchScreenActivity extends Activity {
	
	private int releaseTime = 2300;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFormat(PixelFormat.RGBA_8888);
		// 使启动界面全屏显示,并且隐藏最上方的状态栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.launch_layout);
        
        new Handler().postDelayed(new Runnable() {
            public void run() {
                /* 启动加载画面 */
                Intent launchIntent = new Intent(LaunchScreenActivity.this, MainFunctionsActivity.class);
                LaunchScreenActivity.this.startActivity(launchIntent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                LaunchScreenActivity.this.finish();
            }
        }, releaseTime); //time for release
	}

}
