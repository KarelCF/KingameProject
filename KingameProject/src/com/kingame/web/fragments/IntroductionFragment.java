package com.kingame.web.fragments;

import java.util.ArrayList;
import java.util.List;

import com.kingame.entity.WebTextContent;
import com.kingame.mainfunction.activity.R;
import com.kingame.web.fragments.introduction.InfoTextAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ViewFlipper;

/**
 * 此类为指示器下方主体内容 
 */
@SuppressLint("ValidFragment")
public final class IntroductionFragment extends Fragment implements OnTouchListener {

	private View introduction_view = null;
	private View viewFlipper_view = null;
	private Context context = null;
	
	// 上方自动换图控件与手势识别类(稍后要添加进ListView中)
	private ViewFlipper flipper = null;
	private GestureDetector gestureDetector = null;
	
	// 下方自定义视图块相关,主要显示文字内容
	private List<WebTextContent> infoList = new ArrayList<WebTextContent>();
	private InfoTextAdapter adapter = null;
	private ListView listView = null;
	
	// 填充ListView的数据
	private String[] infoTitles = new String[] {"标题0", "标题1", "标题2", "标题3"};
	private String[] infoTexts = new String[] {
		"smart smart smart smart smart smart smart smart", 
		"artisan artisan artisan artisan artisan artisan artisan", 
		"smart smart smart smart smart smart smart smart", 
		"artisan artisan artisan artisan artisan artisan artisan" 
	};
	
	// 设定手势滑动速度与距离阈值
    private static final int FLING_MIN_DISTANCE = 80;  
    private static final int FLING_MIN_VELOCITY = 150;  
	
	
	public IntroductionFragment(Context context) {
		this.context =  context;
		for (int i = 0; i < infoTitles.length; i++) {
			WebTextContent info = new WebTextContent();
			info.setTitle(infoTitles[i]);
			info.setText(infoTexts[i]);
			infoList.add(info);
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		introduction_view = inflater.inflate(R.layout.web_about_introduction, container, false);
		viewFlipper_view = inflater.inflate(R.layout.web_about_introduction_viewflipper, null);
		
		listView = (ListView) introduction_view.findViewById(R.id.aboutInfoList);
		flipper = (ViewFlipper) viewFlipper_view.findViewById(R.id.introduction_viewFlipper);
		// 如果不加下句,将抛出ClassCastException(要给被添加进ListView的控件设置ListView的布局参数)
		flipper.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 400));
		initViewFlipper();
		
		// 把flipper扔进listview的头部,解决了ViewFlipper与Listview分离的情况
		listView.addHeaderView(flipper);
		
		// ListView分割线消失且点击时不出现分块背景
		listView.setDivider(null);
		// 准备ListView的自定义适配器
		adapter = new InfoTextAdapter(context, infoList);
		listView.setAdapter(adapter);
		return introduction_view;
	}
	
	private void initViewFlipper() {
		// 开启自动启动并设置时间间隔
		flipper.setAutoStart(true);
		flipper.setFlipInterval(4000);
		 // 生成手势识别类实例  
        gestureDetector = new GestureDetector(context, new GerstureListener());
        // 给ViewFlipper设置一个TouchListener
        flipper.setOnTouchListener(this);  
        // 允许长按ViewFlipper
        flipper.setLongClickable(true);  
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
	
	private class GerstureListener implements OnGestureListener {
	    public boolean onDown(MotionEvent e) {
	    	// 非常重要！通知父控件此操作为子控件,请不要干扰
	    	flipper.getParent().requestDisallowInterceptTouchEvent(true);
	        return false;
	    }
	      
	   /** 
	     * 用户按下触摸屏、快速移动后松开即触发这个事件  
	     * e1：第1个ACTION_DOWN MotionEvent  
	     * e2：最后一个ACTION_MOVE MotionEvent  
	     * velocityX：X轴上的移动速度，像素/秒  
	     * velocityY：Y轴上的移动速度，像素/秒  
	     * 触发条件 ：  
	     * X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
	     */  
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	            float velocityY) {
	    	if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE  
	    		&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
	    		// 左滑时,按照规定动画切换
	            flipper.setInAnimation(context, R.anim.anim_in_left);  
	            flipper.setOutAnimation(context, R.anim.anim_out_left);  
	            flipper.showNext();  
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE  
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
            	// 右滑时,按照规定动画切换
                flipper.setInAnimation(context, R.anim.anim_in_right);  
                flipper.setOutAnimation(context, R.anim.anim_out_right);  
                flipper.showPrevious();  
            }  
	    	// 滑动动作完毕后,设置回右进左出的动画
	    	flipper.setInAnimation(context, R.anim.anim_in_left);  
            flipper.setOutAnimation(context, R.anim.anim_out_left);
	        return false;  
	    }
	    
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
    }


	
}
