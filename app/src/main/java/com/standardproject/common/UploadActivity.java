package com.standardproject.common;

import java.io.File;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.Root;
import com.standardproject.bean.RootUpload;
import com.standardproject.util.Config;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbToastUtil;
import com.ugiant.util.api.UploadUtil;

/**
 *
 *   上传图片以及其他操作
 * @author Administrator
 *
 */
public class UploadActivity extends AbActivity implements OnClickListener {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private TextView tv_upload;

	private ImageView iv_logo;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload);

		initViews();
		setListeners();
		initData();
	}

	@Override
	public void initViews() {
		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);
		tv_upload = (TextView) findViewById(R.id.tv_upload);
		iv_logo = (ImageView) findViewById(R.id.iv_logo);

	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_upload.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.tv_upload:
				//showDialog();

				uploadUtil=new UploadUtil();
				uploadUtil.upload(UploadActivity.this);

				break;


			default:
				break;
		}
	}

	private UploadUtil uploadUtil;

	Dialog dialog;
	private int flag;//0代表图库,1代表照相机
	private File cameraFile;
	private File path2;
	private Bitmap bitmap;

	private static final String IMGURL = Environment.getExternalStorageDirectory() + "/tlbs/changehead/";
	/* 照相机缓存头像名称 */
	private static final String IMAGE_FILE_NAME_TEMP = "tempfaceimage.jpg";
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceimage.jpg";
	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 2;
	private static final int CAMERA_REQUEST_CODE = 3;
	private static final int RESULT_REQUEST_CODE = 4;
	private static final int SELECT_PIC_KITKAT = 5;


	private void showDialog() {


		View view = View.inflate(this, R.layout.dialog_photo_choose_yn, null);

		Button openCamera = (Button) view.findViewById(R.id.openCamera);
		Button openPhones = (Button) view.findViewById(R.id.openPhones);
		Button cancel = (Button) view.findViewById(R.id.cancel);

		openCamera.setOnClickListener(new on_click());
		openPhones.setOnClickListener(new on_click());
		cancel.setOnClickListener(new on_click());

		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = TheApp.screenHeight;
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();



	}

	private class on_click implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId())
			{
				case R.id.openCamera:
					flag=1;
					openCamera();
					dialog.cancel();
					break;
				case R.id.openPhones:
					flag=0;
					openPhones();
					dialog.cancel();
					break;
				case R.id.cancel:
					dialog.cancel();
					break;
				default:
					break;
			}

		}

	}

	String result=null;
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { /// 扫描二维码结果
			File imageResult = uploadUtil.imageResult(UploadActivity.this,requestCode, resultCode, data);
			if(imageResult!=null){
				uploadFile(imageResult);
			}
		}
	}


	private void uploadFile(File file) {//storage/emulated/0/Pictures/cgd/small_img_20150519_174356.jpg
		//已经在后台上传                                                 ///storage/emulated/0/Pictures/cgd/small_img_20150519_174349.jpg
		/*if(mAlertDialog!=null){
			AbDialogUtil.showDialog(ActivityMine.this,mAlertDialog);
			return;
		}*/
		//String url = Config.getApi("/common/photo/upload");//
		String url = "http://ugiant.f3322.net:57305/common/photo/upload";
		AbRequestParams params = new AbRequestParams();

		try {
			//多文件上传添加多个即可
			File pathRoot = Environment.getExternalStorageDirectory();
			String path = pathRoot.getAbsolutePath();
			//参数随便加，在sd卡根目录放图片
			//文件名称可能是中文
			params.put("imagefile",file);
			//注意：框架默认将所有参数：URLDecoder.encode(fileName,HTTP.UTF_8)
			//所以服务端要解析中文要用到 URLDecoder.decode(fileName,HTTP.UTF_8)

			//只支持最多2个文件域，因为会产生流中断的异常，所以你需要传递更多，请分次数上传
			//File file3 = new File(path+"/3.log");
			//File file4 = new File(path+"/1.jpg");
			//params.put(file3.getName(),file3);
			//params.put(file4.getName(),file4);
		} catch (Exception e) {
			e.printStackTrace();
		}

		TheApp.mAbHttpUtil.post(url, params, new AbStringHttpResponseListener() {
			@Override
			public void onSuccess(int statusCode, String content) {
				//{"picUrl":"\/2016\/1\/9\/PIC20160109180910855.png","msg":"上传成功","success":true}
				//Gson解析数据
				Gson gson=new Gson();
				RootUpload rootData = gson.fromJson(content, RootUpload.class);
				boolean success = rootData.isSuccess();
				if (!success) {
					String msg = gson.fromJson(content, Root.class).getMsg();
					if (msg != null) {
						AbToastUtil.showToast(UploadActivity.this, msg);
					} else {
						AbToastUtil.showToast(UploadActivity.this, "上传失败,请稍后再试!");
					}
				} else {
					AbToastUtil.showToast(UploadActivity.this, rootData.getMsg());
					TheApp.mAbImageLoader.display(iv_logo,rootData.getPicUrl());
				}
			}

			// 开始执行前
			@Override
			public void onStart() {
				//打开进度框
				/*View v = LayoutInflater.from(ActivityMine.this).inflate(R.layout.progress_bar_horizontal, null, false);
            	mAbProgressBar = (AbHorizontalProgressBar) v.findViewById(R.id.horizontalProgressBar);
            	numberText = (TextView) v.findViewById(R.id.numberText);
        		maxText = (TextView) v.findViewById(R.id.maxText);

        		maxText.setText(progress+"/"+String.valueOf(max));
        		mAbProgressBar.setMax(max);
        		mAbProgressBar.setProgress(progress);

        		//mAlertDialog = AbDialogUtil.showAlertDialog("正在上传",v);
        		Dialog dialog=new Dialog(ActivityMine.this);
        		dialog.setContentView(v);
        		dialog.show();*/
			}

			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(UploadActivity.this,error.getMessage());
			}

			// 进度
			@Override
			public void onProgress(long bytesWritten, long totalSize) {
				/*maxText.setText(bytesWritten/(totalSize/max)+"/"+max);
				mAbProgressBar.setProgress((int)(bytesWritten/(totalSize/max)));*/
			}

			// 完成后调用，失败，成功，都要调用
			public void onFinish() {
			};

		});

	}



	// 打开照相机
	private void openCamera()
	{
		// 打开相机
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 判断存储卡是否可以用,存储缓存图片
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
		{
			//intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(IMGURL + IMAGE_FILE_NAME_TEMP)));
			File path1 = new File(IMGURL);
			path2 = new File(IMGURL);
			if(!path2.exists()){
				path2.mkdirs();
			}

			cameraFile = new File(path2, new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA))  + ".jpg");

			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cameraFile));
		}
		startActivityForResult(intentFromCapture, CAMERA_REQUEST_CODE);
	}


	// 打开相册
	private void openPhones()
	{
		/*Intent intentFromGallery = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intentFromGallery.setType("image/*"); // 设置文件类型
					intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
					startActivityForResult(intentFromGallery, IMAGE_REQUEST_CODE);*/

		Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
			startActivityForResult(intent,SELECT_PIC_KITKAT);
		} else {
			startActivityForResult(intent,IMAGE_REQUEST_CODE);
		}

	}



	private void getData() {

		String url = Config.getApi("/common/sms_code/send");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();
		params.put("phone_no", "13610295454");

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {
			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
				AbToastUtil.showToast(UploadActivity.this, "success");

			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}

			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(UploadActivity.this,statusCode+ content);

			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});

	}


}
