package com.kingame.web.activity;

import com.kingame.entity.WebKingameMainItem;
import com.kingame.mainfunction.activity.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WebKingameMainItemAdapter extends BaseAdapter {
	
	private Context context = null;
	private String[] kingameMainItems = WebKingameMainItem.getKingameMainItems();
	private int[] kingameMainItemsPics = WebKingameMainItem.getKingameMainItemsPics();
	
	public WebKingameMainItemAdapter(Context context) {
		this.context = context;
	}
	
	// 下面的方法一定要复写,否则没东西会被显示出来
	@Override
	public int getCount() {
		return kingameMainItemsPics.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int index = position;
		View view = null;
		TextView title = null;
		ImageView image = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		
		if (convertView == null) {
			view = inflater.inflate(R.layout.web_kingame_mainitem, null);
		} else {
			view = convertView;
		}

		title = (TextView) view.findViewById(R.id.mainitem_title);
		image = (ImageView) view.findViewById(R.id.mainitem_image);
		title.setText(kingameMainItems[position]);
		image.setImageResource(kingameMainItemsPics[position]);
		
		view.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, WebKingameSubItemActivity.class);
				intent.putExtra("mainItemIndex", index);
				context.startActivity(intent);
			}
		});
		return view;
	}
	
}
