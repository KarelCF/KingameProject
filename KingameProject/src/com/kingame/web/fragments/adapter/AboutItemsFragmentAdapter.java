package com.kingame.web.fragments.adapter;

import java.util.ArrayList;

import com.kingame.entity.WebKingameSubItem;
import com.kingame.web.fragments.IntroductionFragment;
import com.kingame.web.fragments.OtherTextFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class AboutItemsFragmentAdapter extends FragmentPagerAdapter {
	
	//指示器的标题
	private static String[] fragmentsIndicator = WebKingameSubItem.getKingameAboutItems();
	
	private int fragmentsTotal = fragmentsIndicator.length;
	
	private ArrayList<Fragment> fragments;

	public AboutItemsFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(new IntroductionFragment(context));
		fragments.add(OtherTextFragment.newInstance("Hello"));
		fragments.add(OtherTextFragment.newInstance("伤感呢.."));
		fragments.add(OtherTextFragment.newInstance("情怀.."));
		fragments.add(OtherTextFragment.newInstance("excellent.."));
	}
	
	// 此为滑动页面时自动获取相应fragment的回调方法
	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return fragmentsIndicator[position % fragmentsTotal];
	}

	@Override
	public int getCount() {
		return fragmentsTotal;
	}
}