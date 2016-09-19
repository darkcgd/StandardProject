package com.standardproject.common;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSnackBarUtil;

public class RippleActivity extends AbActivity {
    private FrameLayout fl;
    private LinearLayout ll_item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ripple);
        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {
        fl= (FrameLayout) findViewById(R.id.fl);
        ll_item= (LinearLayout) findViewById(R.id.ll_item);
        fl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AbSnackBarUtil.ShortSnackbar(ll_item,"天啊，我竟然出错了！",AbSnackBarUtil.white,AbSnackBarUtil.black).setActionTextColor(AbSnackBarUtil.green).show();
            }
        });
       /* ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {

    }
}
