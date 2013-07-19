package com.kingame.entity;

import com.kingame.mainfunction.activity.R;

public class WebKingameMainItem {
	
	private static String[] kingameMainItems = new String[] { 
		"关于经典", 
		"经典动态", 
		"经典产品", 
		"商务合作", 
		"招聘信息", 
		"联系我们"
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
