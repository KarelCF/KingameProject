package com.kingame.slidingmenu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import com.kingame.mainfunction.activity.R;
import com.kingame.slidingmenu.fragments.FirstSlidingMenuFragment;
import com.kingame.slidingmenu.fragments.SlidingMenuFragment;

public class BaseActivity extends SlidingFragmentActivity {
	
	private int mTitleRes;
	protected FirstSlidingMenuFragment mFrag;
    protected long waitTime = 2500;
    protected long touchTime = 0;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		// 初始化BaseActivity时默认设置的SlidingMenu样式为SampleListFragment
		setBehindContentView(R.layout.sliding_menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new FirstSlidingMenuFragment(BaseActivity.this);
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (FirstSlidingMenuFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu 定制SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		
		// 左上角返回按钮
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		// 返回此Activity时关闭滑动菜单且无滑动动画
		getSlidingMenu().showContent(false);
		SlidingMenuFragment.setLastPos(-1);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
