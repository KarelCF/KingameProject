package com.kingame.barcode.activity;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.kingame.barcode.camera.CameraManager;
import com.kingame.barcode.decoding.CaptureActivityHandler;
import com.kingame.barcode.decoding.InactivityTimer;
import com.kingame.barcode.view.ViewfinderView;
import com.kingame.mainfunction.activity.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/* 
 * 本Activity的启动模式为singleInstance，可以让其独立于主activity的任务栈
 * 而自己独立为新任务的根Activity
 */
public class CaptureActivity extends SherlockActivity implements Callback {

	private CaptureActivityHandler handler;
	private Vector<BarcodeFormat> decodeFormats;
	private InactivityTimer inactivityTimer;
	private ViewfinderView viewfinderView;
	private ImageButton flashSwitch;
	
	private String characterSet;
	private boolean hasSurface;
	private boolean vibrate;
	private boolean isFlashOn = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_layout);
		
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
		flashSwitch = (ImageButton) findViewById(R.id.flashBtn);
		flashSwitch.setOnClickListener(new FlashSwitchListener());
	}
	
	private class FlashSwitchListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (!isFlashOn) {
				flashSwitch.setBackgroundResource(R.drawable.button_flash_selected_selector);
				CameraManager.LightOn();
				isFlashOn = true;
			} else {
				flashSwitch.setBackgroundResource(R.drawable.button_flash_unselected_selector);
				CameraManager.LightOff();
				isFlashOn = false;
			}
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
		}
		decodeFormats = null;
		characterSet = null;
		vibrate = true;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (Exception e) {
			return;
		}
		if (handler == null)
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();
	}
	
	//处理返回的数据
	public void handleDecode(Result obj, Bitmap barcode) {
		inactivityTimer.onActivity();
		viewfinderView.drawResultBitmap(barcode);
		startVibrate();
		String codeInfo = obj.getBarcodeFormat().toString() + ":" + obj.getText();
		preparedIntentForPreview(codeInfo, barcode);
	}
	
	private static final long VIBRATE_DURATION = 200L;
	
	private void startVibrate() {
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}
	
	private void preparedIntentForPreview(String codeInfo, Bitmap codeImg) {
		Intent previewIntent = new Intent(CaptureActivity.this, PreviewInfoActivity.class);
		previewIntent.putExtra("codeInfo", codeInfo);
		byte[] codeImgBuffer = serializeBitmap(codeImg); 
		previewIntent.putExtra("codeImg", codeImgBuffer);
		startPreviewActivity(previewIntent);
	}
	
	private byte[] serializeBitmap(Bitmap codeImg) {
		// 将bitmap序列化
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		codeImg.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] codeImgBuffer = baos.toByteArray();
		return codeImgBuffer;
	}
	
	private void startPreviewActivity(Intent previewIntent) {
		CaptureActivity.this.startActivity(previewIntent);
		CaptureActivity.this.finish();
		overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
	}
	
}