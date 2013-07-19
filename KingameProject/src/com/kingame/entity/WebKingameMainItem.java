package com.kingame.entity;

import com.kingame.mainfunction.activity.R;

public class WebKingameMainItem {
	
	private static String[] kingameMainItems = new String[] { 
		"���ھ���", 
		"���䶯̬", 
		"�����Ʒ", 
		"�������", 
		"��Ƹ��Ϣ", 
		"��ϵ����"
	};
	
	private static int[] kingameMainItemsPics = new int[] { 
		R.drawable.pic_about,  
		R.drawable.pic_dynamics,
		R.drawable.pic_products,
		R.drawable.pic_cooperation,
		R.drawable.pic_recruit,
		R.drawable.pic_contact
	};

	public static String[] getKingameMainItems() {
		return kingameMainItems;
	}

	public static int[] getKingameMainItemsPics() {
		return kingameMainItemsPics;
	}
	
}
