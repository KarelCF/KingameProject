package com.kingame.util;

import android.content.Intent;

public class ShareInfoUtil {
	
	public static Intent createIntentForShare() {
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("image/*");
		return shareIntent;
	}
	
}
