package com.kingame.slidingmenu.fragments;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.kingame.entity.SubFunction;
import com.kingame.mainfunction.activity.MainFunctionsActivity;
import com.kingame.mainfunction.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class FirstSlidingMenuFragment extends Fragment {
	
	// lastPos ������¼�ĸ�SlidingMenu��ť������
	private static int lastPos = -1;
	
	private Intent intent = null;
	// ��activity����
	private Context context = null;
	
	public FirstSlidingMenuFragment(Context context) {
		this.context = context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.sliding_list, null);
		ListView list1 = (ListView) view.findViewById(R.id.list1);
		ListView list2 = (ListView) view.findViewById(R.id.list2);
		
		MenuFragmentAdapter adapter = new MenuFragmentAdapter(getActivity());
		MenuFragmentAdapter adapter2 = new MenuFragmentAdapter(getActivity());
		
		adapter.add(new SubFunction("��ҳ" , R.drawable.ic_launcher));
		adapter.add(new SubFunction("����1" , android.R.drawable.ic_menu_search));
		adapter.add(new SubFunction("����2" , android.R.drawable.ic_menu_search));
		adapter.add(new SubFunction("����3" , android.R.drawable.ic_menu_search));
		adapter.add(new SubFunction("����4" , android.R.drawable.ic_menu_search));
		list1.setAdapter(adapter);
		
		adapter2.add(new SubFunction("����5" , android.R.drawable.ic_menu_search));
		adapter2.add(new SubFunction("����6" , android.R.drawable.ic_menu_search));
		adapter2.add(new SubFunction("����7" , android.R.drawable.ic_menu_search));
		adapter2.add(new SubFunction("����8" , android.R.drawable.ic_menu_search));
		list2.setAdapter(adapter2);
		
		list1.setOnItemClickListener(new List1Listener());
		
		return view;
	}

	private class List1Listener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// �ж��Ƿ����SlidingMenu�е�ͬһѡ��,����,��ֻ������SlidingMenu
			if (position == lastPos) {
				SlidingFragmentActivity activity = (SlidingFragmentActivity) context;
				activity.getSlidingMenu().showContent();
				return;
			}
			
			switch (position) {
			case 0:
				intent = new Intent(context, MainFunctionsActivity.class);
				// �˴�ʹ�ӱ𴦷���FunctionChoosedActivityʱ,���taskջ������Activity
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
				lastPos = position; 
				break;
			default:
				Toast.makeText(context, "coming soon", Toast.LENGTH_SHORT).show();
				break;
			}
		}
		
	}
	
	/**
	 * ����Ϊ�����ҳSlidingMenu�Ķ���ArrayAdapter
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
