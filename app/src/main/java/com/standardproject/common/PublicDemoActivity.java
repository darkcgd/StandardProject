package com.standardproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSharedUtil;
import com.ugiant.util.AbToastUtil;

/***
 *
 *  公共通用demo
 * @author Administrator
 *
 */
public class PublicDemoActivity extends AbActivity implements OnClickListener {

	private RelativeLayout rl_01,rl_02,rl_03,rl_04,rl_05,rl_06,rl_07,rl_08;
	private RelativeLayout rl_09,rl_10,rl_11,rl_12,rl_13,rl_14,rl_15,rl_16;
	private RelativeLayout rl_17,rl_18,rl_19,rl_20,rl_21,rl_22,rl_23,rl_24;
	private RelativeLayout rl_25,rl_26,rl_27,rl_29,rl_30,rl_31,rl_32,rl_33;
	private RelativeLayout rl_34;




	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standard_demo);

		initViews();
		setListeners();
	}



	@Override
	public void initViews() {
		rl_01 = (RelativeLayout) findViewById(R.id.rl_01);
		rl_02 = (RelativeLayout) findViewById(R.id.rl_02);
		rl_03 = (RelativeLayout) findViewById(R.id.rl_03);
		rl_04 = (RelativeLayout) findViewById(R.id.rl_04);
		rl_05 = (RelativeLayout) findViewById(R.id.rl_05);
		rl_06 = (RelativeLayout) findViewById(R.id.rl_06);
		rl_07 = (RelativeLayout) findViewById(R.id.rl_07);
		rl_08 = (RelativeLayout) findViewById(R.id.rl_08);
		rl_09 = (RelativeLayout) findViewById(R.id.rl_09);
		rl_10 = (RelativeLayout) findViewById(R.id.rl_10);
		rl_11 = (RelativeLayout) findViewById(R.id.rl_11);
		rl_12 = (RelativeLayout) findViewById(R.id.rl_12);
		rl_13 = (RelativeLayout) findViewById(R.id.rl_13);
		rl_14 = (RelativeLayout) findViewById(R.id.rl_14);
		rl_15 = (RelativeLayout) findViewById(R.id.rl_15);
		rl_16 = (RelativeLayout) findViewById(R.id.rl_16);
		rl_17 = (RelativeLayout) findViewById(R.id.rl_17);
		rl_18 = (RelativeLayout) findViewById(R.id.rl_18);
		rl_19 = (RelativeLayout) findViewById(R.id.rl_19);
		rl_20 = (RelativeLayout) findViewById(R.id.rl_20);
		rl_21 = (RelativeLayout) findViewById(R.id.rl_21);
		rl_22 = (RelativeLayout) findViewById(R.id.rl_22);
		rl_23 = (RelativeLayout) findViewById(R.id.rl_23);
		rl_25 = (RelativeLayout) findViewById(R.id.rl_25);
		rl_26 = (RelativeLayout) findViewById(R.id.rl_26);
		rl_27 = (RelativeLayout) findViewById(R.id.rl_27);
		rl_29 = (RelativeLayout) findViewById(R.id.rl_29);
		rl_30 = (RelativeLayout) findViewById(R.id.rl_30);
		rl_31 = (RelativeLayout) findViewById(R.id.rl_31);
		rl_32 = (RelativeLayout) findViewById(R.id.rl_32);
		rl_33 = (RelativeLayout) findViewById(R.id.rl_33);
		rl_34 = (RelativeLayout) findViewById(R.id.rl_34);


		tv_title_left = (TextView) findViewById(R.id.tv_title_left);
		tv_title_right = (TextView) findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.INVISIBLE);
		tv_title_content = (TextView) findViewById(R.id.tv_title_content);

	}


	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}


	@Override
	public void setListeners() {

		rl_01.setOnClickListener(this);
		rl_02.setOnClickListener(this);
		rl_03.setOnClickListener(this);
		rl_04.setOnClickListener(this);
		rl_05.setOnClickListener(this);
		rl_06.setOnClickListener(this);
		rl_07.setOnClickListener(this);
		rl_08.setOnClickListener(this);
		rl_09.setOnClickListener(this);
		rl_10.setOnClickListener(this);
		rl_11.setOnClickListener(this);
		rl_12.setOnClickListener(this);
		rl_13.setOnClickListener(this);
		rl_14.setOnClickListener(this);
		rl_15.setOnClickListener(this);
		rl_16.setOnClickListener(this);
		rl_17.setOnClickListener(this);
		rl_18.setOnClickListener(this);
		rl_19.setOnClickListener(this);
		rl_20.setOnClickListener(this);
		rl_21.setOnClickListener(this);
		rl_22.setOnClickListener(this);
		rl_23.setOnClickListener(this);
		rl_25.setOnClickListener(this);
		rl_26.setOnClickListener(this);
		rl_27.setOnClickListener(this);
		rl_29.setOnClickListener(this);
		rl_30.setOnClickListener(this);
		rl_31.setOnClickListener(this);
		rl_32.setOnClickListener(this);
		rl_33.setOnClickListener(this);
		rl_34.setOnClickListener(this);


		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;

			case R.id.rl_01:
				startActivity(new Intent(this,PayActivity.class));      //  1.   支付
				break;
			case R.id.rl_02:
				startActivity(new Intent(this,ShareActivity.class));        //  2.  分享
				break;
			case R.id.rl_03:
				startActivity(new Intent(this,LoginRegActivity.class));      //  3.   登录注册
				break;
			case R.id.rl_04:
				startActivity(new Intent(this,ChangeCityActivity.class));      //  4.   城市定位,选择
				break;
			case R.id.rl_05:
				startActivity(new Intent(this,ScanActivity.class));      //  5.   扫码
				break;
			case R.id.rl_06:
				startActivity(new Intent(this,UploadActivity.class));      //  6.   上传图片以及相关操作
				break;
			case R.id.rl_07:
				startActivity(new Intent(this,AdListActivity.class));      //  7.   广告轮播图
				break;
			case R.id.rl_08:
				startActivity(new Intent(this,PromptActivity.class));      //  8.   提醒方式(提示语)
				break;
			case R.id.rl_09:
				startActivity(new Intent(this,RefreshActivity.class));      //  9.   刷新Activity
				break;
			case R.id.rl_10:
				startActivity(new Intent(this,NoDataActivity.class));      //  10. 没数据提醒样式
				break;
			case R.id.rl_11:
				int user_id = AbSharedUtil.getInt(this, "user_id");
				if (user_id==-999) {
					startActivity(new Intent(this,LoginActivity.class));
					AbToastUtil.showToast(this, "请先登录!");
					return;
				}
				startActivity(new Intent(this,SuggestionActivity.class));      //  11. 用户反馈,提交建议
				break;
			case R.id.rl_12:
				startActivity(new Intent(this,HelpListActivity.class));      //  12.帮助分类列表
				break;
			case R.id.rl_13:
				startActivity(new Intent(this,NoticeListActivity.class));      //  13.公告列表
				break;
			case R.id.rl_14:
				startActivity(new Intent(this,AboutActivity.class));      //  14.关于我们
				break;
			case R.id.rl_15:
				startActivity(new Intent(this,WheelViewActivity.class));      //  15.地区时间选择控件
				break;
			case R.id.rl_16:                                                   //  16 下拉筛选
				startActivity(new Intent(this,DropDownActivity.class));
				break;
			case R.id.rl_17:                                                   //  17.上传头像,修改个人信息,密码(第三方登录需要绑手机)
				startActivity(new Intent(this,UpdateInfoActivity.class));
				break;
			case R.id.rl_18:                                                   //  18.多图展示
				startActivity(new Intent(this,PhotoViewActivity.class));
				break;
			case R.id.rl_19:                                                   //  19."评价"
				startActivity(new Intent(this,CommentActivity.class));
				break;
			case R.id.rl_20:                                                   // 20.收藏
				startActivity(new Intent(this,CollectActivity.class));
				break;
			case R.id.rl_21:                                                   //  21.欢迎页
				startActivity(new Intent(this,WelcomeActivity.class));
				break;
			case R.id.rl_22:                                                   //  22. 引导页
				startActivity(new Intent(this,GuideActivity.class));
				break;
			case R.id.rl_23:                                                   //  23.
				startActivity(new Intent(this,NewLayoutActivity.class));
				break;
			case R.id.rl_25:                                                  //带阴影的布局
				startActivity(new Intent(this , ShadowLayoutActivity.class));
				break;
			case R.id.rl_26:                                                   //  26.recycleview使用
				startActivity(new Intent(this,RecycleViewActivity.class));
				break;
			case R.id.rl_27:                                                   //  27.百度定位
				startActivity(new Intent(this,BaiduLocationActivity.class));
				break;
			case R.id.rl_29:                                                   //  29.bottomsheet

			/*	String nu = null;
				nu.toString();*/

				startActivity(new Intent(this,BottomSheetActivity.class));
				break;
			case R.id.rl_30:                                                   //  30.评论、点赞工具类
				startActivity(new Intent(this,CommentAndCollectActivity.class));
				break;
			case R.id.rl_31:                                                  // 31.toolbar
				startActivity(new Intent(this,ToolbarActivity.class));
				break;
			case R.id.rl_32:                                                   //  32.原生态和h5交互工具库抽离
				startActivity(new Intent(this,WebViewActivity.class));
				break;
			case R.id.rl_33:                                                 //33.svg图片的使用
				startActivity(new Intent(this,SVGActivity.class));
				break;
			case R.id.rl_34:                                                 //33.svg图片的使用
				startActivity(new Intent(this,GuideMaskActivity.class));
				break;

			default:
				break;
		}
	}
}
