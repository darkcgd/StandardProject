package com.standardproject.presenter;

import com.standardproject.view.IRecycleListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangpeixian on 2016/7/19.
 */
public class RecycleListPresenter implements BasePresenter {

    private IRecycleListView resultView;
    private List<String> mList=new ArrayList<>();;
    String mes = "这是数据";
    private int start=0;
    private int end=0;
    public RecycleListPresenter(IRecycleListView resultView) {
        this.resultView = resultView;
    }

    @Override
    public void handlerSucceed(int type, String content) {

    }

    @Override
    public void showNoNetWork(int type, String msg) {

    }

    //下拉刷新与上拉加载
    public void getListData(final boolean flag) {
        if (flag) {//下拉刷新
            mList .clear();
            end = 20;
            for (start = 0; start < end; start++) {
                mList.add(mes + start);
            }
            resultView.updateAdapter(mList);
        }else{//上拉加载
            final ArrayList<String> list = new ArrayList<>();
            for (end = end + 10; start < end + 10; start++) {
                list.add(mes + start);
            }
            mList.addAll(list);
            resultView.updateAdapter(mList);
        }
    }
}
