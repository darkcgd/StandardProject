package com.standardproject.common;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.gson.Gson;
import com.standardproject.R;
import com.standardproject.TheApp;
import com.standardproject.bean.RootNoticeList;
import com.standardproject.bean.RootNoticeList.PageEntity.ListEntity;
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

/**
 * 公告列表
 *
 * @author Administrator
 *
 */
public class NoticeListActivity extends AbActivity implements OnClickListener, OnHeaderRefreshListener, OnFooterLoadListener {

	private TextView tv_title_left;
	private TextView tv_title_right;
	private TextView tv_title_content;
	private LinearListView notice_list;

	private AbAdapter<ListEntity> adapter;
	List<ListEntity> list;

	//下拉上拉刷新所需变量
	List<ListEntity> newList=new ArrayList<ListEntity>();
	private AbPullToRefreshView mAbPullToRefreshView = null;
	private int pageNumber = 1;
	private int totalPage = 5;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_list);


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
		tv_title_content.setText("公告列表");

		notice_list = (LinearListView) findViewById(R.id.notice_list);
	}


	@Override
	public void setListeners() {
		tv_title_left.setOnClickListener(this);
		tv_title_right.setOnClickListener(this);
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

	@Override
	public void initData() {

		list = new ArrayList<ListEntity>();

		mAbPullToRefreshView = (AbPullToRefreshView)findViewById(R.id.mPullRefreshView);
		//设置进度条的样式
		mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));
		// 设置监听器
		mAbPullToRefreshView.setOnHeaderRefreshListener(this);
		mAbPullToRefreshView.setOnFooterLoadListener(this);

		adapter = new AbAdapter<ListEntity>(this, list, R.layout.adapter_notice_item) {

			@Override
			public void convert(AbViewHolder viewHolder, ListEntity item,
								int position) {

				TextView context = viewHolder.getView(R.id.tv_context); //
				TextView time = viewHolder.getView(R.id.tv_time);   //
				TextView state = viewHolder.getView(R.id.tv_state);

				context.setText(item.getContext());
				time.setText(item.getCreated());
				state.setText(item.getStatus()+"");


			}
		};

		notice_list.setAdapter(adapter);

		// 初始化网络数据
		pageNumber=1;
		initFreshData(pageNumber);

	}



	public void onHeaderRefresh(AbPullToRefreshView view) {
		pageNumber=1;
		initFreshData(pageNumber);
		//防止禁止上拉后,刷新不能再上拉
		mAbPullToRefreshView.setLoadMoreEnable(true);

	}

	public void onFooterLoad(AbPullToRefreshView view) {
		pageNumber++;
		if(pageNumber<=totalPage){
			mAbPullToRefreshView.setLoadMoreEnable(true);
			loadMoreTask(pageNumber);
		}else{
			mAbPullToRefreshView.onFooterLoadFinish();
			mAbPullToRefreshView.setLoadMoreEnable(false);
		}

	}

	private void initFreshData(int pager) {
		String url = Config.getApi("/common/getSysNoticeList");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {

				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}

				RootNoticeList entity = gson.fromJson(content, RootNoticeList.class);

				//获取请求到的当前数据
				newList = entity.getPage().getList();
				//获取当前页码.总页码
				pageNumber=entity.getPage().getPageNumber();
				totalPage=entity.getPage().getTotalPage();

				AbTask mAbTask = AbTask.newInstance();
				final AbTaskItem item = new AbTaskItem();
				item.setListener(new AbTaskListListener() {
					@Override
					public List<?> getList() {
						return newList;
					}
					@Override
					public void update(List<?> paramList) {
						//通知Dialog
						List<ListEntity> newList = (List<ListEntity>) paramList;
						list.clear();
						if (newList != null && newList.size() >= 0) {
							list.addAll(newList);
							adapter.updateView(list);
							newList.clear();
						}
						mAbPullToRefreshView.onHeaderRefreshFinish();
					}
				});
				mAbTask.execute(item);
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


	private void loadMoreTask(int pager) {

		String url = Config.getApi("/common/getSysNoticeList");//请求地址
		// 绑定参数
		AbRequestParams params = new AbRequestParams();

		TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

			// 获取数据成功会调用这里
			@Override
			public void onSuccess(int statusCode, String content) {

				//Gson解析数据
				Gson gson=new Gson();//{"code":"1512281757293453","order_id":125,"success":true}

				RootNoticeList entity = gson.fromJson(content, RootNoticeList.class);
				boolean success = entity.isSuccess();

				if (!success) {
					AbToastUtil.showToast(NoticeListActivity.this, "获取失败!");
				}else {
					//获取请求到的当前数据
					newList = entity.getPage().getList();
					//获取当前页码.总页码
					pageNumber=entity.getPage().getPageNumber();
					totalPage=entity.getPage().getTotalPage();

					AbTask mAbTask = AbTask.newInstance();
					final AbTaskItem item = new AbTaskItem();
					item.setListener(new AbTaskListListener() {
						@Override
						public List<?> getList() {
							return newList;
						}
						@Override
						public void update(List<?> paramList) {
							//通知Dialog
							List<ListEntity> newList = (List<ListEntity>) paramList;
							list.clear();
							if (newList != null && newList.size() >= 0) {
								list.addAll(newList);
								adapter.updateView(list);
								//					newList.clear();
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



}
