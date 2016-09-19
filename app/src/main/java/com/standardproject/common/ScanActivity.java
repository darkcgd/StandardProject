package com.standardproject.common;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.util.qrcode.CaptureActivity;
import com.standardproject.util.qrcode.EncodingHandler;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;

public class ScanActivity extends AbActivity implements OnClickListener {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private EditText et_content;
	private TextView tv_scan;
	private TextView tv_build;
	private ImageView iv_qrcode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);

		initViews();
		initData();
		setListeners();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		et_content = (EditText) findViewById(R.id.et_content);
		tv_scan = (TextView) findViewById(R.id.tv_scan);
		tv_build = (TextView) findViewById(R.id.tv_build);
		iv_qrcode = (ImageView) findViewById(R.id.iv_qrcode);


	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		tv_scan.setOnClickListener(this);
		tv_build.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_scan:

				Intent openCameraIntent = new Intent(this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
				iv_qrcode.setBackgroundResource(0);

				break;
			case R.id.tv_build:
				String content  = et_content.getText().toString().trim();
				if (TextUtils.isEmpty(content)) {
					AbToastUtil.showToast(this, "请输入内容!");
					return;
				}else {
					createQrCode(content);
					AbToastUtil.showToast(this, "正在生成中!");
				}
				break;

			default:
				break;
		}
	}


	// 生成二维码
	private void createQrCode(String content) {

		try {
			if (!TextUtils.isEmpty(content)) {
				Bitmap qrCodeBitmap = null;
				qrCodeBitmap = EncodingHandler.createQRCode(
						"G" + content, TheApp.screenWidth * 3 / 5);
				iv_qrcode.setImageBitmap(qrCodeBitmap);


			} else {
				AbToastUtil.showToast(ScanActivity.this, "无法生成二维码");
			}

		} catch (WriterException e) {
			e.printStackTrace();
		}
	}



	String result=null;
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { /// 扫描二维码结果
			if(1==1){////o代表是上传头像,1代表扫描二维码
				Bundle bundle = data.getExtras();
				result = bundle.getString("result");
				showDialog(result);
				AbToastUtil.showToast(this, result);
			}
		}
	}

	/**
	 *
	 *  显示二维码内容
	 */
	private Dialog dialog;
	private void showDialog(String context) {
		View view = getLayoutInflater().inflate(R.layout.activity_show_scanqrcode,
				null);
		dialog = new Dialog(this,R.style.ShareDialog);
		dialog.setContentView(view, new LayoutParams((int) (TheApp.screenWidth * 0.6),// 设置为屏幕宽度的0.9
				(int) (TheApp.screenHeight * 0.5)));//设置为屏幕高度的0.8
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);

		// 设置显示宽高
		dialog.show();

		Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
		TextView tv_context = (TextView) view.findViewById(R.id.tv_context);
		tv_context.setText("二维码内容: "+context);
		//
		bt_confirm.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				dialog.dismiss();
			}
		});

	}



}
