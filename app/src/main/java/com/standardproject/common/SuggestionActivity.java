package com.standardproject.common;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.RootSuggestion;
import com.standardproject.util.Config;
import com.standardproject.util.MD5String;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;

/***
 *
 *  提交建议
 * @author cj
 *
 */
public class SuggestionActivity extends AbActivity implements OnClickListener {


	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private Button bt_submit;// 提交建议
	private EditText et_title;  // 标题
	private EditText et_context;  // 内容

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggestion);

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
		tv_title_content.setText("提交建议");

		et_title = (EditText) findViewById(R.id.et_title);
		et_context = (EditText) findViewById(R.id.et_context);
		bt_submit = (Button) findViewById(R.id.bt_submit);



	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
		bt_submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			case R.id.bt_submit:   //  提交建议
				Submit();
				break;

			default:
				break;
		}
	}

	//  提交建议
	private void Submit() {


		String title = et_title.getText().toString().trim();
		String context = et_context.getText().toString().trim();

		if (TextUtils.isEmpty(title)) {
			AbToastUtil.showToast(this, "请填写标题!");
			return;
		}else if(TextUtils.isEmpty(context)) {
			AbToastUtil.showToast(this, "请填写内容!");
			return;
		}
		String url = Config.getApi("/api/member/logined/submitSuggestion");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();
		params.put("user_id", AbSharedUtil.getInt(TheApp.instance, "user_id"));
		// MD5加密后的签名
		String sign = MD5String.SignUser(AbSharedUtil.getInt(TheApp.instance, "user_id"), AbSharedUtil.getString(TheApp.instance, "token"));
		params.put("sign", sign);

		params.put("title", title);
		params.put("context", context);

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {
			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
				RootSuggestion entity = gson.fromJson(content, RootSuggestion.class);
				boolean success = entity.isSuccess();
				if (success) {
					AbToastUtil.showToast(SuggestionActivity.this, "提交成功!");
					finish();
				}else {
					AbToastUtil.showToast(SuggestionActivity.this, entity.getMsg());
				}

			};
			// 开始执行前
			@Override
			public void onStart() {
				//显示进度框
			}
			// 失败，调用
			@Override
			public void onFailure(int statusCode, String content,Throwable error) {
				AbToastUtil.showToast(TheApp.instance, error.getMessage());
			}
			// 完成后调用，失败，成功
			@Override
			public void onFinish() {
			};

		});



	}


}
