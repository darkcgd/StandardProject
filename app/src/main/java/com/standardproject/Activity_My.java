package com.standardproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.standardproject.common.MyDataActivity;
import com.standardproject.widget.PullScrollView;
import com.ugiant.AbActivity;


public class Activity_My extends AbActivity implements OnClickListener, PullScrollView.OnTurnListener {


    private ImageView ib_photo;  // 头像
    private PullScrollView mScrollView;
    private ImageView mHeadImg;

    private RelativeLayout rl_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity__my);

        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {

        mScrollView = (PullScrollView) findViewById(R.id.mScrollView);
        mHeadImg = (ImageView) findViewById(R.id.mHeadImg);
        ib_photo = (ImageView) findViewById(R.id.user_avatar);
        rl_detail = (RelativeLayout) findViewById(R.id.rl_detail);


        mScrollView.setHeader(mHeadImg);
        mScrollView.setOnTurnListener(this);
    }

    public void initData() {

        getDetail();
    }

    @Override
    public void setListeners() {
        ib_photo.setOnClickListener(this);
        rl_detail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_avatar:
                startActivity(new Intent(this, MyDataActivity.class));
                break;
            case R.id.rl_detail:
               startActivity(new Intent(this, MyDataActivity.class));

                break;

            default:
                break;
        }
    }


    private void getDetail() {

    }

    @Override
    public void onTurn() {
    }



}
