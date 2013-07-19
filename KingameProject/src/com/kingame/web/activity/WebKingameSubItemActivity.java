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
		// ׼����activiy��ViewPagerҪ�õ�����Adapter
		fragmentPagerAdapters.add(new AboutItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new DynamicsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new ProductsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new CooperationItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new RecruitmentItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		fragmentPagerAdapters.add(new ContactUsItemsFragmentAdapter(this.getSupportFragmentManager(), WebKingameSubItemActivity.this));
		// ѡ���������adapter
		fragmentPagerAdapter = fragmentPagerAdapters.get(mainItemIndex);
		
		// ��ViewPager����Adapter
		viewPager = (ViewPager) findViewById(R.id.viewPager); 
		viewPager.setAdapter(fragmentPagerAdapter);
		
		// indicator������
		indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(viewPager);
		
		// ��indicator���ü�����(�̳���ViewPager)
		indicator.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {}
			
			@Override
			public void onPageSelected(int position) {
				// ����������,����ʹviewPager�д��Ҳ�fragment���󻬵�
				// ������fragmentʱ�ų���slidingmenu
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
