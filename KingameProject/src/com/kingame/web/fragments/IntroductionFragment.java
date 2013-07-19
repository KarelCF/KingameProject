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
 * ����Ϊָʾ���·��������� 
 */
@SuppressLint("ValidFragment")
public final class IntroductionFragment extends Fragment implements OnTouchListener {

	private View introduction_view = null;
	private View viewFlipper_view = null;
	private Context context = null;
	
	// �Ϸ��Զ���ͼ�ؼ�������ʶ����(�Ժ�Ҫ��ӽ�ListView��)
	private ViewFlipper flipper = null;
	private GestureDetector gestureDetector = null;
	
	// �·��Զ�����ͼ�����,��Ҫ��ʾ��������
	private List<WebTextContent> infoList = new ArrayList<WebTextContent>();
	private InfoTextAdapter adapter = null;
	private ListView listView = null;
	
	// ���ListView������
	private String[] infoTitles = new String[] {"����0", "����1", "����2", "����3"};
	private String[] infoTexts = new String[] {
		"smart smart smart smart smart smart smart smart", 
		"artisan artisan artisan artisan artisan artisan artisan", 
		"smart smart smart smart smart smart smart smart", 
		"artisan artisan artisan artisan artisan artisan artisan" 
	};
	
	// �趨���ƻ����ٶ��������ֵ
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
		// ��������¾�,���׳�ClassCastException(Ҫ������ӽ�ListView�Ŀؼ�����ListView�Ĳ��ֲ���)
		flipper.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, 400));
		initViewFlipper();
		
		// ��flipper�ӽ�listview��ͷ��,�����ViewFlipper��Listview��������
		listView.addHeaderView(flipper);
		
		// ListView�ָ�����ʧ�ҵ��ʱ�����ַֿ鱳��
		listView.setDivider(null);
		// ׼��ListView���Զ���������
		adapter = new InfoTextAdapter(context, infoList);
		listView.setAdapter(adapter);
		return introduction_view;
	}
	
	private void initViewFlipper() {
		// �����Զ�����������ʱ����
		flipper.setAutoStart(true);
		flipper.setFlipInterval(4000);
		 // ��������ʶ����ʵ��  
        gestureDetector = new GestureDetector(context, new GerstureListener());
        // ��ViewFlipper����һ��TouchListener
        flipper.setOnTouchListener(this);  
        // ������ViewFlipper
        flipper.setLongClickable(true);  
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}
	
	private class GerstureListener implements OnGestureListener {
	    public boolean onDown(MotionEvent e) {
	    	// �ǳ���Ҫ��֪ͨ���ؼ��˲���Ϊ�ӿؼ�,�벻Ҫ����
	    	flipper.getParent().requestDisallowInterceptTouchEvent(true);
	        return false;
	    }
	      
	   /** 
	     * �û����´������������ƶ����ɿ�����������¼�  
	     * e1����1��ACTION_DOWN MotionEvent  
	     * e2�����һ��ACTION_MOVE MotionEvent  
	     * velocityX��X���ϵ��ƶ��ٶȣ�����/��  
	     * velocityY��Y���ϵ��ƶ��ٶȣ�����/��  
	     * �������� ��  
	     * X�������λ�ƴ���FLING_MIN_DISTANCE�����ƶ��ٶȴ���FLING_MIN_VELOCITY������/��
	     */  
	    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
	            float velocityY) {
	    	if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE  
	    		&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
	    		// ��ʱ,���չ涨�����л�
	            flipper.setInAnimation(context, R.anim.anim_in_left);  
	            flipper.setOutAnimation(context, R.anim.anim_out_left);  
	            flipper.showNext();  
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE  
                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
            	// �һ�ʱ,���չ涨�����л�
                flipper.setInAnimation(context, R.anim.anim_in_right);  
                flipper.setOutAnimation(context, R.anim.anim_out_right);  
                flipper.showPrevious();  
            }  
	    	// ����������Ϻ�,���û��ҽ�����Ķ���
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
