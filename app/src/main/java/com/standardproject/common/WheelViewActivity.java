package com.standardproject.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.widget.wheelview.OptionsPickerView;
import com.ugiant.widget.wheelview.TimePickerView;

public class WheelViewActivity extends AbActivity  implements OnClickListener{
	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private ArrayList<ProvinceBean> options1Items = new ArrayList<ProvinceBean>();
	private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
	private TextView tvTime, tvOptions;
	private TimePickerView pvTime;
	private OptionsPickerView pvOptions;
	private View vMasker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wheel_view);
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

		vMasker=findViewById(R.id.vMasker);
		tvTime=(TextView) findViewById(R.id.tvTime);
		tvOptions=(TextView) findViewById(R.id.tvOptions);
		//时间选择器(第二个参数为选择模式，年月日时分，年月日，时分，月日时分)
		pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
		//控制时间范围
		Calendar calendar = Calendar.getInstance();
		pvTime.setRange(calendar.get(Calendar.YEAR) - 3, calendar.get(Calendar.YEAR));
		pvTime.setTime(new Date());
		pvTime.setCyclic(false);
		pvTime.setCancelable(true);
		//时间选择后回调
		pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

			@Override
			public void onTimeSelect(Date date) {
				tvTime.setText(getTime(date));
			}
		});
		//弹出时间选择器
		tvTime.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pvTime.show();
			}
		});

		//选项选择器
		pvOptions = new OptionsPickerView(this);

		//选项1
		options1Items.add(new ProvinceBean(0,"广东","广东省，以岭南东道、广南东路得名","其他数据"));
		options1Items.add(new ProvinceBean(1,"湖南","湖南省地处中国中部、长江中游，因大部分区域处于洞庭湖以南而得名湖南","芒果TV"));
		options1Items.add(new ProvinceBean(3,"广西","嗯～～",""));

		//选项2
		ArrayList<String> options2Items_01=new ArrayList<String>();
		options2Items_01.add("广州");
		options2Items_01.add("佛山");
		options2Items_01.add("东莞");
		options2Items_01.add("阳江");
		options2Items_01.add("珠海");
		ArrayList<String> options2Items_02=new ArrayList<String>();
		options2Items_02.add("长沙");
		options2Items_02.add("岳阳");
		ArrayList<String> options2Items_03=new ArrayList<String>();
		options2Items_03.add("桂林");
		options2Items.add(options2Items_01);
		options2Items.add(options2Items_02);
		options2Items.add(options2Items_03);

		//选项3
		ArrayList<ArrayList<String>> options3Items_01 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> options3Items_02 = new ArrayList<ArrayList<String>>();
		ArrayList<ArrayList<String>> options3Items_03 = new ArrayList<ArrayList<String>>();
		ArrayList<String> options3Items_01_01=new ArrayList<String>();
		options3Items_01_01.add("白云");
		options3Items_01_01.add("天河");
		options3Items_01_01.add("海珠");
		options3Items_01_01.add("越秀");
		options3Items_01.add(options3Items_01_01);
		ArrayList<String> options3Items_01_02=new ArrayList<String>();
		options3Items_01_02.add("南海");
		options3Items_01_02.add("高明");
		options3Items_01_02.add("顺德");
		options3Items_01_02.add("禅城");
		options3Items_01.add(options3Items_01_02);
		ArrayList<String> options3Items_01_03=new ArrayList<String>();
		options3Items_01_03.add("其他");
		options3Items_01_03.add("常平");
		options3Items_01_03.add("虎门");
		options3Items_01.add(options3Items_01_03);
		ArrayList<String> options3Items_01_04=new ArrayList<String>();
		options3Items_01_04.add("其他1");
		options3Items_01_04.add("其他2");
		options3Items_01_04.add("其他3");
		options3Items_01.add(options3Items_01_04);
		ArrayList<String> options3Items_01_05=new ArrayList<String>();
		options3Items_01_05.add("其他1");
		options3Items_01_05.add("其他2");
		options3Items_01_05.add("其他3");
		options3Items_01.add(options3Items_01_05);

		ArrayList<String> options3Items_02_01=new ArrayList<String>();
		options3Items_02_01.add("长沙长沙长沙长沙长沙长沙长沙长沙长沙1111111111");
		options3Items_02_01.add("长沙2");
		options3Items_02_01.add("长沙3");
		options3Items_02_01.add("长沙4");
		options3Items_02_01.add("长沙5");
		options3Items_02_01.add("长沙6");
		options3Items_02_01.add("长沙7");
		options3Items_02_01.add("长沙8");
		options3Items_02.add(options3Items_02_01);
		ArrayList<String> options3Items_02_02=new ArrayList<String>();
		options3Items_02_02.add("岳1");
		options3Items_02_02.add("岳2");
		options3Items_02_02.add("岳3");
		options3Items_02_02.add("岳4");
		options3Items_02_02.add("岳5");
		options3Items_02_02.add("岳6");
		options3Items_02_02.add("岳7");
		options3Items_02_02.add("岳8");
		options3Items_02_02.add("岳9");
		options3Items_02.add(options3Items_02_02);
		ArrayList<String> options3Items_03_01=new ArrayList<String>();
		options3Items_03_01.add("好山水");
		options3Items_03.add(options3Items_03_01);

		options3Items.add(options3Items_01);
		options3Items.add(options3Items_02);
		options3Items.add(options3Items_03);

		//三级联动效果(有三个重载方法,分别表示1,2,3级联动)
		pvOptions.setPicker(options1Items, options2Items, options3Items, true);
		//设置选择的三级单位
		//pwOptions.setLabels("省", "市", "区");
		pvOptions.setTitle("选择城市");
		pvOptions.setCyclic(false, false, false);
		//设置默认选中的三级项目
		//监听确定选择按钮
		pvOptions.setSelectOptions(1, 1, 1);
		pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

			@Override
			public void onOptionsSelect(int options1, int option2, int options3) {
				//返回的分别是三个级别的选中位置
				String tx = options1Items.get(options1).getPickerViewText()
						+ options2Items.get(options1).get(option2)
						+ options3Items.get(options1).get(option2).get(options3);
				tvOptions.setText(tx);
				vMasker.setVisibility(View.GONE);
			}
		});
		//点击弹出选项选择器
		tvOptions.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				pvOptions.show();
			}
		});
	}

	@Override
	public void initData() {

	}

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;
			default:
				break;
		}
	}

	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(date);
	}





	class ProvinceBean {
		private long id;
		private String name;
		private String description;
		private String others;

		public ProvinceBean(long id,String name,String description,String others){
			this.id = id;
			this.name = name;
			this.description = description;
			this.others = others;
		}

		public long getId() {
			return id;
		}

		public void setId(long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getOthers() {
			return others;
		}

		public void setOthers(String others) {
			this.others = others;
		}

		//这个用来显示在PickerView上面的字符串,PickerView会通过反射获取getPickerViewText方法显示出来。
		public String getPickerViewText() {
			//这里还可以判断文字超长截断再提供显示
			return name;
		}
	}


}
