package com.kingame.web.activity;


import android.os.Bundle;
import android.widget.ListView;

import com.kingame.mainfunction.activity.R;
import com.kingame.slidingmenu.activity.BaseActivity;

public class WebKingameMainItemActivity extends BaseActivity {
	
	private ListView mainItemListView = null;
	private WebKingameMainItemAdapter mainItemAdapter = null;
	
	public WebKingameMainItemActivity() {
		super(R.string.webkingamemainitemactivity_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_kingame_mainitem_list);
		
		mainItemListView = (ListView) findViewById(R.id.mainitem_list);
		mainItemListView.setDivider(null);
		mainItemAdapter = new WebKingameMainItemAdapter(this);
		mainItemListView.setAdapter(mainItemAdapter);
	}		
	
}
