package com.kingame.web.fragments.introduction;

import java.util.ArrayList;
import java.util.List;

import com.kingame.entity.WebTextContent;
import com.kingame.mainfunction.activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class InfoTextAdapter extends BaseAdapter {
	
	private Context context = null;
	private List<WebTextContent> infoList = new ArrayList<WebTextContent>();
	
	public InfoTextAdapter(Context context, List<WebTextContent> infoList) {
		this.infoList = infoList;
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return infoList.size();
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
		TextView text = null;
		ImageButton button = null;
		LayoutInflater inflater = LayoutInflater.from(context);
		if (convertView == null) {
			view = inflater.inflate(R.layout.info_text_block, null);
		} else {
			view = convertView;
		}
		
		title = (TextView) view.findViewById(R.id.title);
		text = (TextView) view.findViewById(R.id.text);
		button = (ImageButton) view.findViewById(R.id.button);
		
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(context, "这是标题 " + index , Toast.LENGTH_SHORT).show();
			}
		});
		
		title.setText(infoList.get(position).getTitle());
		text.setText(infoList.get(position).getText());
		
		return view;
		
	}
	
}
