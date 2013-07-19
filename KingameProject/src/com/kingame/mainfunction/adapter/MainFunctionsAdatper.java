package com.kingame.mainfunction.adapter;

import java.util.List;

import com.kingame.barcode.activity.CaptureActivity;
import com.kingame.entity.Function;
import com.kingame.mainfunction.activity.R;
import com.kingame.util.ConnectivityUtil;
import com.kingame.web.activity.WebFantaxideaActivity;
import com.kingame.web.activity.WebKingameMainItemActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainFunctionsAdatper extends BaseAdapter {

	Context context = null;
	List<Function> functions = null;
	
	public MainFunctionsAdatper(Context context, List<Function> functions) {
		this.context = context;
		this.functions = functions;
	}
	
	@Override
	public int getCount() {
		return functions.size();
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
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = null;
		ImageView functionImageView = null;
		TextView functionTextView = null;
		
		if (convertView == null) {
			view = inflater.inflate(R.layout.main_function_item_layout, null);
		} else {
			view = (View) convertView;
		}
		
		// 显示功能的自定义视图组件
		functionImageView = (ImageView) view.findViewById(R.id.functionImageView);
		functionTextView = (TextView) view.findViewById(R.id.functionTextView);
		
		Function function = functions.get(position);
		functionTextView.setText(function.getFunctionName());
		functionImageView.setImageResource(function.getFunctionImageId());
		
		switch (position) {
		case 0:
			view.setOnClickListener(new BarcodeFunctionListener());
			break;
		case 1:
			view.setOnClickListener(new QRcodeFunctionListener());
			break;
		case 2:
			view.setOnClickListener(new FantaxideaWebFunctionListener());
			break;
		case 3:
			view.setOnClickListener(new KingameWebFunctionListener());
			break;
		default:
			break;
		}
		return view;
	}
	
	private class BarcodeFunctionListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, CaptureActivity.class);
			context.startActivity(intent);
		}
	}
	
	private class QRcodeFunctionListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, CaptureActivity.class);
			context.startActivity(intent);
		}
	}
	
	private class FantaxideaWebFunctionListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (ConnectivityUtil.isNetworkAvailable(context)) {
				Intent intent = new Intent(context, WebFantaxideaActivity.class);
				context.startActivity(intent);
			} else {
				Toast.makeText(context, R.string.disconnceted, Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	private class KingameWebFunctionListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(context, WebKingameMainItemActivity.class);
			context.startActivity(intent);
		}
	}
	
}
