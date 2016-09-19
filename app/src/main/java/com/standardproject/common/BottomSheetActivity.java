package com.standardproject.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.standardproject.R;
import com.ugiant.AbActivity;

/**
 * Created by chijiaduo on 2016/7/19.
 * 这里提供三种BottomSheet的实现方式，前两种为github上的开源，第三种为系统自带
 * 1.地址：
 * 2.地址：https://github.com/Flipboard/bottomsheet
 */
public class BottomSheetActivity extends AbActivity implements View.OnClickListener {
    private Button bt_bottom1, bt_bottom2, bt_bottom3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        initViews();
        initData();
        setListeners();


    }

    @Override
    public void initViews() {
        bt_bottom1 = (Button) findViewById(R.id.bt_bottom1);
        bt_bottom2 = (Button) findViewById(R.id.bt_bottom2);
        bt_bottom3 = (Button) findViewById(R.id.bt_bottom3);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        bt_bottom1.setOnClickListener(this);
        bt_bottom2.setOnClickListener(this);
        bt_bottom3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_bottom1:
                startActivity(new Intent(BottomSheetActivity.this, BotttomSheet1Activity.class));

                break;
            case R.id.bt_bottom2:
                startActivity(new Intent(BottomSheetActivity.this, BottomSheet2Activity.class));
                break;
            case R.id.bt_bottom3:
                startActivity(new Intent(BottomSheetActivity.this, BottomSheet3Activity.class));
                break;
        }

    }
}
