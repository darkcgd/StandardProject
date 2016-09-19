package com.standardproject.common;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.RootAboutUs;
import com.standardproject.util.Config;
import com.ugiant.AbActivity;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.task.AbTask;
import com.ugiant.task.AbTaskItem;
import com.ugiant.task.AbTaskListListener;
import com.ugiant.util.AbAdapter;
import com.ugiant.util.AbToastUtil;
import com.ugiant.util.AbViewHolder;
import com.ugiant.view.pullview.AbPullToRefreshView;
import com.ugiant.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ugiant.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ugiant.widget.LinearListView;

import java.util.ArrayList;
import java.util.List;

/***
 *
 *  刷新界面
 * @author Administrator
 *
 */
public class RefreshActivity extends AbActivity implements OnClickListener ,OnHeaderRefreshListener, OnFooterLoadListener{

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;

	private List<Integer> list;
	private AbAdapter<Integer> adapter;
	private LinearListView lv;
	//下拉上拉刷新所需变量
	List<Integer> newList=new ArrayList<Integer>();
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private Dialog progressDialog;//加载等待框
	private int pageNumber = 1;
	private int totalPage = 5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refresh);

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
		tv_title_content.setText("刷新界面");

		mAbPullToRefreshView = (AbPullToRefreshView) this.findViewById(R.id.mPullRefreshView);
		//设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		lv=(LinearListView) findViewById(R.id.lv);

	}

	@Override
	public void initData() {
		list=new ArrayList<Integer>();
		/*for (int i = 0; i < 10; i++) {
			list.add(i);
		}*/

		adapter=new AbAdapter<Integer>(RefreshActivity.this, list, R.layout.item_category) {

			@Override
			public void convert(AbViewHolder viewHolder,Integer item, int position) {
				TextView tv=viewHolder.getView(R.id.tv_category);
				tv.setText("11");
			}
		};
		lv.setAdapter(adapter);
		// 初始化网络数据
		pageNumber=1;
		initFreshData(pageNumber);
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView arg0) {
		pageNumber = 1;
		initFreshData(pageNumber);
		// 防止禁止上拉后,刷新不能再上拉
		mAbPullToRefreshView.setLoadMoreEnable(true);
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView arg0) {
		pageNumber++;
		if (pageNumber <= totalPage) {
			mAbPullToRefreshView.setLoadMoreEnable(true);
			loadMoreTask(pageNumber);
		} else {
			mAbPullToRefreshView.onFooterLoadFinish();
			mAbPullToRefreshView.setLoadMoreEnable(false);
		}
	}

	private void initFreshData(int pageNumber) {
		String url = Config.getApi("/common/getHelpCategoryList");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {
			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
				RootAboutUs entity = gson.fromJson(content, RootAboutUs.class);
				boolean success = entity.isSuccess();
				if (!success) {
					AbToastUtil.showToast(RefreshActivity.this, "刷新失败!");
				}else {
					for (int i = 0; i < 10; i++) {
						newList.add(i);
					}
					AbTask mAbTask = AbTask.newInstance();
					final AbTaskItem item = new AbTaskItem();
					item.setListener(new AbTaskListListener() {
						@Override
						public List<?> getList() {
							return newList;
						}

						@Override
						public void update(List<?> paramList) {
							List<Integer> newList = (List<Integer>) paramList;
							list.clear();
							if (newList != null && newList.size() >= 0) {
								list.addAll(newList);
								int size = list.size();
								adapter.updateView(list);
								newList.clear();
							}
							mAbPullToRefreshView.onHeaderRefreshFinish();
						}
					});
					mAbTask.execute(item);

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
	private void loadMoreTask(int pageNumber) {
		String url = Config.getApi("/common/getHelpCategoryList");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {
			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {
				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}
				RootAboutUs entity = gson.fromJson(content, RootAboutUs.class);
				boolean success = entity.isSuccess();
				if (!success) {
					AbToastUtil.showToast(RefreshActivity.this, "刷新失败!");
				}else {
					AbTask mAbTask = AbTask.newInstance();
					final AbTaskItem item = new AbTaskItem();
					item.setListener(new AbTaskListListener() {
						@Override
						public List<?> getList() {
							for (int i = 0; i < 10; i++) {
								newList.add(i);
							}
							return newList;
						}

						@Override
						public void update(List<?> paramList) {
							List<Integer> newList = (List<Integer>) paramList;
							if (newList != null && newList.size() >= 0) {
								list.addAll(newList);
								adapter.updateView(list);
								newList.clear();
							}
							mAbPullToRefreshView.onHeaderRefreshFinish();
						}
					});
					mAbTask.execute(item);

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

	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);
	}
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_left:
				finish();
				break;

			default:
				break;
		}
	}

}
