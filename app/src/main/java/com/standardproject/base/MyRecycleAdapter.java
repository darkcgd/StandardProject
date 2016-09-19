package com.standardproject.base;

import android.content.Context;

import com.standardproject.R;

import java.util.List;

/**
 * @ explain:
 * @ author：xujun on 2016/5/21 23:07
 * @ email：gdutxiaoxu@163.com
 */
public class MyRecycleAdapter extends BaseRecycleAdapter<String>{

    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    public MyRecycleAdapter(Context context, int itemLayoutId) {
        super(context, itemLayoutId);
    }

    public MyRecycleAdapter(Context context, int itemLayoutId, List<String> datas) {
        super(context,itemLayoutId,datas);
    }

    @Override
    public void convert(BaseRecycleHolder helper, String item) {
        helper.setText(R.id.textView,item);
    }
}
