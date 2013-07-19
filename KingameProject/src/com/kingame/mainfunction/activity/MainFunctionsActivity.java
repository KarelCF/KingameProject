package com.kingame.mainfunction.activity;

import java.util.ArrayList;
import java.util.List;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kingame.entity.Function;
import com.kingame.mainfunction.adapter.MainFunctionsAdatper;
import com.kingame.slidingmenu.activity.BaseActivity;
import com.kingame.slidingmenu.fragments.SecondSlidingMenuFragment;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.Toast;

public class MainFunctionsActivity extends BaseActivity {

	public MainFunctionsActivity() {
		super(R.string.app_name);
	}
	
	private GridView mainFunctionGridView = null;
	private List<Function> functions = new ArrayList<Function>();
	private MainFunctionsAdatper adapter = null;
	private String[] functionName = {"扫描条形码", "扫描二维码", "沸腾文化", "经典科技", "功能5", "功能6"};
	private int[] functionImageId = {R.drawable.pic_barcode, R.drawable.pic_qrcode, 
			R.drawable.pic_fantaxidea, R.drawable.pic_other, R.drawable.pic_other, R.drawable.pic_other}; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_function_layout);
		// 获取主GridView
		mainFunctionGridView = (GridView) findViewById(R.id.mainFunctionGridView);
		// 获取功能列表
		functions = getFuctions(functionName, functionImageId);
		// 设置adapter填充主GridView
		adapter = new MainFunctionsAdatper(MainFunctionsActivity.this, functions);
		mainFunctionGridView.setAdapter(adapter);
		
		// 初始化右边sildingmenu
		initialRightSlidingMenu();
	}
	
	
	private void initialRightSlidingMenu() {
		
		/* 
		 * 设置右边菜单的布局fragment(若不是从setMenu的R资源变更到指定fragment,
		 * 就会默认变为BaseActvity中初始的SampleListFragment)
		 */
		getSlidingMenu().setSecondaryMenu(R.layout.sliding_menu_frame_second);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame_second, new SecondSlidingMenuFragment(MainFunctionsActivity.this))
		.commit();
		
		// 设置滑动菜单参数
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
	}
	
	private List<Function> getFuctions(String[] functionName, int[] functionImageId) {
		List<Function> functionList = new ArrayList<Function>();
		for (int i = 0; i < functionName.length; i++) {
			Function function = new Function();
			function.setFunctionImageId(functionImageId[i]);
			function.setFunctionName(functionName[i]);
			functionList.add(function);
		}
		return functionList;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.functionchoosedactivity_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings_more:
			getSlidingMenu().showSecondaryMenu();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
			long currentTime = System.currentTimeMillis();
			if((currentTime - touchTime) >= waitTime) {
				Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
				touchTime = currentTime;
			}else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
