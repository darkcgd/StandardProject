package com.standardproject.common;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.standardproject.R;
import com.standardproject.base.MyRecycleAdapter;
import com.standardproject.presenter.RecycleListPresenter;
import com.standardproject.view.IRecycleListView;
import com.standardproject.widget.PulltoRefreshRecyclerView;
import com.ugiant.AbActivity;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AbActivity implements IRecycleListView {

    PulltoRefreshRecyclerView mRefreshRecyclerView;
    private MyRecycleAdapter mAdapter;
    private List<String> mList=new ArrayList<String>();
    private RecycleListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {
        presenter=new RecycleListPresenter(this);
        mRefreshRecyclerView= (PulltoRefreshRecyclerView) findViewById(R.id.ll_recycle_view);
    }
    @Override
    public void initData() {
        mAdapter = new MyRecycleAdapter(this, R.layout.item_recycleview, mList);
        mRefreshRecyclerView.setAdapter(mAdapter);
        presenter.getListData(true);
    }

    @Override
    public void setListeners() {
        mRefreshRecyclerView.setOnRefreshListener(new PulltoRefreshRecyclerView.onRefreshListener() {

            @Override
            public void onRefresh(RecyclerView recyclerView) {
                presenter.getListData(true);
            }

            @Override
            public void onLoadMore(RecyclerView recyclerView) {
               presenter.getListData(false);
            }
        });
    }

    //更新适配器
    @Override
    public void updateAdapter(List<String> list) {
        mRefreshRecyclerView.OnRefreshCompleted();
        mAdapter.setDatas(list);
    }

    //
    @Override
    public void showError(int type, String msg) {

    }

    @Override
    public void showLoding(int type) {

    }

    @Override
    public void showData(int type) {

    }

    @Override
    public void showEmpty(int type) {

    }

    @Override
    public void showNoNetWork(int type, String msg) {

    }
}
