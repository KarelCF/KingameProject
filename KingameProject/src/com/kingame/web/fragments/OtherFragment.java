package com.kingame.web.fragments;

import com.kingame.mainfunction.activity.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * 此类为指示器下方主体内容 
 */
@SuppressLint("ValidFragment")
public final class OtherFragment extends Fragment {

	private View view = null;
	private View animView = null;
	private Button animBtn = null;
	private Context context = null;
	
	private ViewGroup viewGroup = null;
	
	public OtherFragment(Context context) {
		this.context =  context;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.other_activity_layout, container, false);
		viewGroup = container;
		animBtn = (Button) view.findViewById(R.id.animBtn);
		animBtn.setOnClickListener(new AnimBtnListener());
		return view;
	}
	
	private class AnimBtnListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			showAnimView();
		}
		
	}
	
	// 获得演示动画用的组件
	private View buildAnimView(){
		LayoutInflater inflater = LayoutInflater.from(context);
		animView = inflater.inflate(R.layout.other_activity_anim_layout, viewGroup, false);
		animView.findViewById(R.id.animHideBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideAnimView(getAnimScene());
			}
		});
		return animView;
		}
	
	// 开始进行动画
	private void startViewAnimation(View v,int id){
		Animation animation = AnimationUtils.loadAnimation(context, id);
		v.startAnimation(animation);
	}
	
	// 显示组件
	private void showAnimView(){
		View v = buildAnimView();
		//@android:anim/bounce_interpolator 
		startViewAnimation(v, R.anim.in_translate_top);
		getScene().addView(v);
	}
	
	// 隐藏组件
	private void hideAnimView(ViewGroup v){
		View view = v.getChildAt(v.getChildCount() - 1);
		//@android:anim/bounce_interpolator 
		startViewAnimation(view, R.anim.out_translate_top);
		getScene().removeView(view);
	}
	
	private ViewGroup getScene(){
		return ((ViewGroup) (this.getActivity().findViewById(R.id.other_layout)));
	}
	
	private ViewGroup getAnimScene(){
		return ((ViewGroup) (this.getActivity().findViewById(R.id.other_layout)));
	}

}
