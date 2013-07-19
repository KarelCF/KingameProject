package com.kingame.web.fragments.adapter;

import java.util.ArrayList;

import com.kingame.entity.WebKingameSubItem;
import com.kingame.web.fragments.OtherTextFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContactUsItemsFragmentAdapter extends FragmentPagerAdapter {
	
	//指示器的标题
	private static String[] fragmentsIndicator = WebKingameSubItem.getKingameContactUsItems();
	
	private int fragmentsTotal = fragmentsIndicator.length;
	
	private ArrayList<Fragment> fragments;

	public ContactUsItemsFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(OtherTextFragment.newInstance("伤感呢.."));
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