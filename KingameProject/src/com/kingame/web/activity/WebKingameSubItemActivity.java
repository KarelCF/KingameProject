package com.kingame.web.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.kingame.mainfunction.activity.R;
import com.kingame.slidingmenu.activity.BaseActivity;
import com.kingame.web.fragments.adapter.AboutItemsFragmentAdapter;
import com.kingame.web.fragments.adapter.ContactUsItemsFragmentAdapter;
import com.kingame.web.fragments.adapter.CooperationItemsFragmentAdapter;
import com.kingame.web.fragments.adapter.DynamicsItemsFragmentAdapter;
import com.kingame.web.fragments.adapter.ProductsItemsFragmentAdapter;
import com.kingame.web.fragments.adapter.RecruitmentItemsFragmentAdapter;
import com.viewpagerindicator.TabPageIndicator;

public class WebKingameSubItemActivity extends BaseActivity {
	
	private List<FragmentPagerAdapter> fragmentPagerAdapters = new ArrayList<FragmentPagerAdapter>();
	private FragmentPagerAdapter fragmentPagerAdapter = null;
	private ViewPager viewPager = null;
	private TabPageIndicator indicator = null;
	
	public WebKingameSubItemActivity() {
		super(R.string.webkingamesubitemactivity_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		int mainItemIndex = this.getIntent().getIntExtra("mainItemIndex", 0);
System.out.println(mainItemIndex);		
		// 准备本activiy的ViewPager要用的所有Adapter
		fragmentPagerAdapters.add(new AboutItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new DynamicsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new ProductsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new CooperationItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new RecruitmentItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new ContactUsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		// 选出被点击的adapter
		fragmentPagerAdapter = fragmentPagerAdapters.get(mainItemIndex);
		
		// 给ViewPager设置Adapter
		viewPager = (ViewPager) findViewById(R.id.viewPager); 
		viewPager.setAdapter(fragmentPagerAdapter);
		
		// indicator的设置
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		
		// 给indicator设置监听器(继承自ViewPager)
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
			
			@Override
			public void onPageSelected(int position) {
				// 做如下设置,可以使viewPager中从右测fragment向左滑到
				// 最左侧的fragment时才出现slidingmenu
				switch (position) {
				case 0:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
					break;
				default:
					getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
					break;
				}
			}
		});
	}
	
}
