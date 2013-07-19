package com.kingame.barcode.activity;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.kingame.mainfunction.activity.R;
import com.kingame.slidingmenu.activity.BaseActivity;
import com.kingame.util.ConnectivityUtil;
import com.kingame.util.HttpUtil;
import com.kingame.util.ShareInfoUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewInfoActivity extends BaseActivity {
	
	// 消息传递机制控制网络操作进程中的进度条
	private static final int SETPROGRESS = 0;
	private static final int FINISH = 1;
	private UpdateProgressHandler updateProgressHandler = new UpdateProgressHandler();
	private ProgressDialog progressDialog;
	
	private ImageView codeImage;
	private TextView codeText;
	private Button sendBtn;
	private Button cancelBtn;
	
	// 条码信息
	private byte[] codeImgBuffer;
	private String codeInfo;
	
	// 服务器返回的结果
	private static String result = null;
	
	public PreviewInfoActivity() {
		super(R.string.previewinfoactivity_name);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_preview_layout);
		// 左上角返回按钮
		getSupportActionBar().setHomeButtonEnabled(true);
		
		codeImage = (ImageView) findViewById(R.id.codeImageView);
		codeText = (TextView) findViewById(R.id.codeTextView);
		sendBtn = (Button) findViewById(R.id.sendBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		sendBtn.setOnClickListener(new SendBtnListener());
		cancelBtn.setOnClickListener(new CancelBtnListener());
		
		Intent previewIntent = PreviewInfoActivity.this.getIntent();
		// 取出intent信息
		codeInfo = (String) previewIntent.getCharSequenceExtra("codeInfo");
		codeImgBuffer = previewIntent.getByteArrayExtra("codeImg");
		
		// 序列化数据转回为bitmap
		Bitmap codeImg = BitmapFactory.decodeByteArray(codeImgBuffer, 0, codeImgBuffer.length);
		
		//显示数据
		codeImage.setImageBitmap(codeImg);
		codeText.setText(codeInfo);
			
	}
	
	private class SendBtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (ConnectivityUtil.isNetworkAvailable(PreviewInfoActivity.this))
				sendToServer();
			else
				Toast.makeText(PreviewInfoActivity.this, R.string.disconnceted, Toast.LENGTH_LONG).show();
		}
	}
	
	private class CancelBtnListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(PreviewInfoActivity.this, CaptureActivity.class);
			PreviewInfoActivity.this.startActivity(intent);
			PreviewInfoActivity.this.finish();
		}
	}
	
	private void sendToServer() {
		// 网络操作扔到进程中
		new Thread(new Runnable() {
			@Override
			public void run() {
				// 发送信息启动旋转进度条
				Message startNetmsg = PreviewInfoActivity.this.updateProgressHandler.obtainMessage(SETPROGRESS, 0);
				PreviewInfoActivity.this.updateProgressHandler.sendMessage(startNetmsg);
				// 网络操作
				String url = HttpUtil.BASE_URL + "servlet/ReceiveCodeInfoServlet";
				result = HttpUtil.queryStringForPost(url, codeInfo, codeImgBuffer);
				// 发送信息启动结束进度条
				Message endNetMsg = PreviewInfoActivity.this.updateProgressHandler.obtainMessage(FINISH, 0);
				PreviewInfoActivity.this.updateProgressHandler.sendMessage(endNetMsg);
			}
		}).start();
	}
	
	private class UpdateProgressHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SETPROGRESS:
				PreviewInfoActivity.this.progressDialog = ProgressDialog.show(PreviewInfoActivity.this, "等会儿...", "传着数据呢...", true);
				break;
			case FINISH:
				PreviewInfoActivity.this.progressDialog.dismiss();
				if (result != null && "0".equals(result)) {
					Toast.makeText(PreviewInfoActivity.this, "未能成功上传", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(PreviewInfoActivity.this, result, Toast.LENGTH_SHORT).show();
				}
				break;
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.previewinfoactivity_menu, menu);
		MenuItem shareItem = menu.findItem(R.id.settings_share);
		/*
		 * 注意要在菜单布局文件中将此按钮设置一个actionProviderClass属性
		 * 值为com.actionbarsherlock.widget.ShareActionProvider
		 * 否则此处无法添加actionProvider
		 * 并且绑定actionProvider只在onCreateOptionsMenu()方法中进行方有效
		 */
		ShareActionProvider actionProvider = (ShareActionProvider) shareItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        actionProvider.setShareIntent(ShareInfoUtil.createIntentForShare());
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.settings_more:
			Toast.makeText(PreviewInfoActivity.this, "更多功能，敬请期待", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
