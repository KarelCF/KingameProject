package com.kingame.slidingmenu.fragments;

import com.kingame.entity.SubFunction;
import com.kingame.mainfunction.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * 此类为主页SlidingMenu的父类
 */
@SuppressLint("ValidFragment")
public class SlidingMenuFragment extends ListFragment {
	
	// lastPos 用来记录哪个SlidingMenu按钮被按下
	private static int lastPos = -1;
	
	public static int getLastPos() {
		return lastPos;
	}
	
	public static void setLastPos(int lastPos) {
		SlidingMenuFragment.lastPos = lastPos;
	}

	// 主activity环境
	protected Context context = null;
	
	public SlidingMenuFragment(Context context) {
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sliding_second_list, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	/**
	 * 此类为填充主页SlidingMenu的定制ArrayAdapter
	 */
	public class MenuFragmentAdapter extends ArrayAdapter<SubFunction> {

		public MenuFragmentAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.sliding_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).getSubFunctionImageId());
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).getSubFunctionName());
			return convertView;
		}

	}
	
}
