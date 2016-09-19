package com.standardproject.view;

import java.util.List;

/**
 * Created by yangpeixian on 2016/7/19.
 */
public interface IRecycleListView extends IBaseView{

    /**
     * 更新列表
     * @param list 数据源
     */
    void updateAdapter(List<String> list);
}
