package com.standardproject.common;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.RootUpload;
import com.standardproject.util.Config;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbToastUtil;
import com.ugiant.util.api.UploadUtil;
import com.ugiant.widget.CircleImageView;
/**
 * 上传头像,修改个人信息,(第三方登录需要绑手机)
 * @author cgd
 * 2016-2-25
 */
public class UpdateInfoActivity extends AbActivity implements OnClickListener{
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private RelativeLayout rl_head;
	private CircleImageView iv_headImage;
	private RelativeLayout rl_username;
	private TextView tv_username;
	private RelativeLayout rl_nickname;
	private TextView tv_name;
	private RelativeLayout rl_gender;
	private TextView tv_gender;
	private RelativeLayout rl_address;
	private TextView tv_address;
	private RelativeLayout rl_update_pwd;
	private TextView tv_update_pwd;
	private RelativeLayout rl_forget_pwd;
	private TextView tv_forget_pwd;
	private RelativeLayout rl_bind_phone;
	private TextView tv_bind_phone;
	private TextView tv_exit;//推出登陆
	private UploadUtil uploadUtil;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_info);
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

		tv_forget_pwd=(TextView) findViewById(R.id.tv_forget_pwd);
		tv_update_pwd=(TextView) findViewById(R.id.tv_update_pwd);
		rl_head=(RelativeLayout) findViewById(R.id.rl_head);
		iv_headImage=(CircleImageView) findViewById(R.id.iv_headImage);
		rl_username=(RelativeLayout) findViewById(R.id.rl_username);
		tv_username=(TextView) findViewById(R.id.tv_username);

		rl_nickname=(RelativeLayout) findViewById(R.id.rl_nickname);
		tv_name=(TextView) findViewById(R.id.tv_name);
		rl_gender=(RelativeLayout) findViewById(R.id.rl_gender);
		tv_gender=(TextView) findViewById(R.id.tv_gender);
		rl_address=(RelativeLayout) findViewById(R.id.rl_address);
		tv_address=(TextView) findViewById(R.id.tv_address);
		rl_update_pwd=(RelativeLayout) findViewById(R.id.rl_update_pwd);
		tv_update_pwd=(TextView) findViewById(R.id.tv_update_pwd);
		rl_forget_pwd=(RelativeLayout) findViewById(R.id.rl_forget_pwd);
		tv_forget_pwd=(TextView) findViewById(R.id.tv_forget_pwd);
		rl_bind_phone=(RelativeLayout) findViewById(R.id.rl_bind_phone);
		tv_bind_phone=(TextView) findViewById(R.id.tv_bind_phone);
		tv_exit=(TextView) findViewById(R.id.tv_exit);
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		rl_head.setOnClickListener(this);
		rl_username.setOnClickListener(this);
		rl_nickname.setOnClickListener(this);
		rl_gender.setOnClickListener(this);
		rl_address.setOnClickListener(this);
		rl_update_pwd.setOnClickListener(this);
		rl_forget_pwd.setOnClickListener(this);
		rl_bind_phone.setOnClickListener(this);
		tv_exit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.rl_head:
				uploadUtil = new UploadUtil();
				uploadUtil.upload(UpdateInfoActivity.this);
				break;
			case R.id.rl_username:
				break;
			case R.id.rl_nickname:
				break;
			case R.id.rl_gender:
				break;
			case R.id.rl_address:
				break;
			case R.id.rl_update_pwd:
				break;
			case R.id.rl_forget_pwd:
				break;
			case R.id.rl_bind_phone:
				break;
			case R.id.tv_exit:
				break;

			default:
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) { /// 扫描二维码结果
			File imageResult = uploadUtil.imageResult(UpdateInfoActivity.this,requestCode, resultCode, data);
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
		String url = Config.getApi("/common/photo/upload");
		//String url = "http://119.29.118.160:30001/service/common/photo/upload";
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
				//{"picUrl":"http://58.210.52.234:16101/2016/2/25/PIC20160225104808246.png","no_domain_picUrl":"/2016/2/25/PIC20160225104808246.png","msg":"上传成功","success":true}
				//Gson解析数据
				Gson gson=new Gson();
				RootUpload entity = gson.fromJson(content, RootUpload.class);
				boolean success = entity.isSuccess();
				if (!success) {
					AbToastUtil.showToast(UpdateInfoActivity.this, "上传失败");
				}else {
					AbToastUtil.showToast(UpdateInfoActivity.this, "上传成功");
					String picUrl = entity.getPicUrl();
					TheApp.mAbImageLoader.display(iv_headImage, picUrl);
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
				AbToastUtil.showToast(UpdateInfoActivity.this,error.getMessage());
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
}
