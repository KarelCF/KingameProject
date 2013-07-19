package com.kingame.entity;

public class WebKingameSubItem {
	
	private static String[] kingameAboutItems = new String[] { "公司简介", "经典团队", "办公环境", "企业文化", "经典大记事"};
	private static String[] kingameDynamicsItems = new String[] { "公司动态", "媒体新闻"};
	private static String[] kingameProductsItems = new String[] { "裁决", "侠天下", "刀剑西游", "其它"};
	private static String[] kingameCooperationItems = new String[] {"商务合作"};
	private static String[] kingameRecruitmentItems = new String[] { "策划类", "美术类", "程序类", "运营类", "管理类"};
	private static String[] kingameContactUsItems = new String[] {"联系我们"};
	
	public static String[] getKingameAboutItems() {
		return kingameAboutItems;
	}

	public static String[] getKingameDynamicsItems() {
		return kingameDynamicsItems;
	}

	public static String[] getKingameProductsItems() {
		return kingameProductsItems;
	}

	public static String[] getKingameCooperationItems() {
		return kingameCooperationItems;
	}

	public static String[] getKingameRecruitmentItems() {
		return kingameRecruitmentItems;
	}

	public static String[] getKingameContactUsItems() {
		return kingameContactUsItems;
	}
	
}
