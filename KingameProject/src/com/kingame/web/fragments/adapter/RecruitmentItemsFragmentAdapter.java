package com.kingame.web.fragments.adapter;

import java.util.ArrayList;

import com.kingame.entity.WebKingameSubItem;
import com.kingame.web.fragments.OtherFragment;
import com.kingame.web.fragments.OtherTextFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class RecruitmentItemsFragmentAdapter extends FragmentPagerAdapter {
	
	//ָʾ���ı���
	private static String[] fragmentsIndicator = WebKingameSubItem.getKingameRecruitmentItems();
	
	private int fragmentsTotal = fragmentsIndicator.length;
	
	private ArrayList<Fragment> fragments;

	public RecruitmentItemsFragmentAdapter(FragmentManager fm, Context context) {
		super(fm);
		fragments = new ArrayList<Fragment>();
		fragments.add(new OtherFragment(context));
		fragments.add(OtherTextFragment.newInstance("Hello"));
		fragments.add(OtherTextFragment.newInstance("�˸���.."));
		fragments.add(OtherTextFragment.newInstance("�黳.."));
		fragments.add(OtherTextFragment.newInstance("excellent.."));
	}
	
	// ��Ϊ����ҳ��ʱ�Զ���ȡ��Ӧfragment�Ļص�����
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