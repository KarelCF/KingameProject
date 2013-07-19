package com.kingame.web.fragments;

import com.kingame.mainfunction.activity.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 此类为指示器下方主体内容 
 */
public final class OtherTextFragment extends Fragment {
	
	private String mContent = "???";
	private static final String KEY_CONTENT = "TestFragment:Content";
	
	private OtherTextFragment() {
		super();
	}
	
	public static OtherTextFragment newInstance(String content) {
		OtherTextFragment fragment = new OtherTextFragment();

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 10; i++) {
			builder.append(content).append(" ");
		}
		builder.deleteCharAt(builder.length() - 1);
		fragment.mContent = builder.toString();
		
		return fragment;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if ((savedInstanceState != null) && savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
		
		TextView text = new TextView(getActivity());
		text.setGravity(Gravity.CENTER);
		text.setText(mContent);
		text.setTextSize(20 * getResources().getDisplayMetrics().density);
		text.setPadding(20, 20, 20, 20);
		
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.setGravity(Gravity.CENTER);
		layout.setBackgroundResource(R.drawable.pic_bg);
		layout.addView(text);
		
		return layout;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
}
