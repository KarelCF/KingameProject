package com.kingame.util;

import android.content.Context;
import android.net.ConnectivityManager;

/*
 * Õ¯¬Á¡¨Ω”ºÏ≤‚¿‡
 */
public class ConnectivityUtil {
	
	private static boolean isNetWorkAvailable;
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivityManager.getActiveNetworkInfo() == null) {
			isNetWorkAvailable = false;
		} else {
			isNetWorkAvailable = true;
		}
		return isNetWorkAvailable;
	}
	
}
