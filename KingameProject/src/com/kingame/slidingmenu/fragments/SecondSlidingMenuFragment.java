package com.kingame.slidingmenu.fragments;

import com.kingame.entity.SubFunction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class SecondSlidingMenuFragment extends SlidingMenuFragment {
	
	public SecondSlidingMenuFragment(Context context) {
		super(context);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// ��˻���list�˵����ѡ��
		MenuFragmentAdapter adapter = new MenuFragmentAdapter(getActivity());
			adapter.add(new SubFunction("����5" , android.R.drawable.ic_menu_search));
			adapter.add(new SubFunction("����6" , android.R.drawable.ic_menu_search));
			adapter.add(new SubFunction("����7" , android.R.drawable.ic_menu_search));
			adapter.add(new SubFunction("����8" , android.R.drawable.ic_menu_search));
		setListAdapter(adapter);
	}
	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		// �ж��Ƿ����SlidingMenu�е�ͬһѡ��,����,��ֻ������SlidingMenu
		/*if (position == SlidingMenuFragment.getLastPos()) {
			SlidingFragmentActivity activity = (SlidingFragmentActivity) context;
			activity.getSlidingMenu().showContent();
			return;
		}*/
		switch (position) {
		case 0:
			Toast.makeText(context, "coming very soon", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(context, "coming very soon", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(context, "coming very soon", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(context, "coming very soon", Toast.LENGTH_SHORT).show();
		default:
			break;
		}
	}
	
}
