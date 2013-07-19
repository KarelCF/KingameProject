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
	
	// ��Ϣ���ݻ��ƿ���������������еĽ�����
	private static final int SETPROGRESS = 0;
	private static final int FINISH = 1;
	private UpdateProgressHandler updateProgressHandler = new UpdateProgressHandler();
	private ProgressDialog progressDialog;
	
	private ImageView codeImage;
	private TextView codeText;
	private Button sendBtn;
	private Button cancelBtn;
	
	// ������Ϣ
	private byte[] codeImgBuffer;
	private String codeInfo;
	
	// ���������صĽ��
	private static String result = null;
	
	public PreviewInfoActivity() {
		super(R.string.previewinfoactivity_name);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.capture_preview_layout);
		// ���ϽǷ��ذ�ť
		getSupportActionBar().setHomeButtonEnabled(true);
		
		codeImage = (ImageView) findViewById(R.id.codeImageView);
		codeText = (TextView) findViewById(R.id.codeTextView);
		sendBtn = (Button) findViewById(R.id.sendBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		sendBtn.setOnClickListener(new SendBtnListener());
		cancelBtn.setOnClickListener(new CancelBtnListener());
		
		Intent previewIntent = PreviewInfoActivity.this.getIntent();
		// ȡ��intent��Ϣ
		codeInfo = (String) previewIntent.getCharSequenceExtra("codeInfo");
		codeImgBuffer = previewIntent.getByteArrayExtra("codeImg");
		
		// ���л�����ת��Ϊbitmap
		Bitmap codeImg = BitmapFactory.decodeByteArray(codeImgBuffer, 0, codeImgBuffer.length);
		
		//��ʾ����
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
		// ��������ӵ�������
		new Thread(new Runnable() {
			@Override
			public void run() {
				// ������Ϣ������ת������
				Message startNetmsg = PreviewInfoActivity.this.updateProgressHandler.obtainMessage(SETPROGRESS, 0);
				PreviewInfoActivity.this.updateProgressHandler.sendMessage(startNetmsg);
				// �������
				String url = HttpUtil.BASE_URL + "servlet/ReceiveCodeInfoServlet";
				result = HttpUtil.queryStringForPost(url, codeInfo, codeImgBuffer);
				// ������Ϣ��������������
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
				PreviewInfoActivity.this.progressDialog = ProgressDialog.show(PreviewInfoActivity.this, "�Ȼ��...", "����������...", true);
				break;
			case FINISH:
				PreviewInfoActivity.this.progressDialog.dismiss();
				if (result != null && "0".equals(result)) {
					Toast.makeText(PreviewInfoActivity.this, "δ�ܳɹ��ϴ�", Toast.LENGTH_SHORT).show();
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
		 * ע��Ҫ�ڲ˵������ļ��н��˰�ť����һ��actionProviderClass����
		 * ֵΪcom.actionbarsherlock.widget.ShareActionProvider
		 * ����˴��޷����actionProvider
		 * ���Ұ�actionProviderֻ��onCreateOptionsMenu()�����н��з���Ч
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
			Toast.makeText(PreviewInfoActivity.this, "���๦�ܣ������ڴ�", Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
}
