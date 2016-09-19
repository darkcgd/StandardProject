package com.standardproject.common;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.standardproject.R;
import com.ugiant.AbActivity;
import com.ugiant.util.AbSnackBarUtil;
import com.ugiant.util.AbToastUtil;

public class NewLayoutActivity extends AbActivity implements View.OnClickListener {

    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private TextView tv04;
    Toolbar toolbar;
    private FloatingActionButton fab;
    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_layout);

        initViews();
        initData();
        setListeners();


    }

    @Override
    public void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

    //    toolbar.setNavigationIcon(R.drawable.bt_title_left);//设置导航栏图标
        toolbar.setLogo(R.drawable.ic_launcher);//设置app logo
        toolbar.setTitle("Title");//设置主标题
        toolbar.setSubtitle("ToolBar");//设置子标题

        toolbar.inflateMenu(R.menu.base_toolbar_menu);//设置右上角的填充菜单
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuItemId = item.getItemId();
                if (menuItemId == R.id.action_menu01) {
                   AbToastUtil.showToast(NewLayoutActivity.this,"菜单1");
                } else if (menuItemId == R.id.action_menu02) {
                    AbToastUtil.showToast(NewLayoutActivity.this,"菜单2");
                } else if (menuItemId == R.id.action_menu03) {
                    AbToastUtil.showToast(NewLayoutActivity.this,"菜单3");
                } else if (menuItemId == R.id.action_menu04) {
                    AbToastUtil.showToast(NewLayoutActivity.this,"菜单4");
                }
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });



        tv01 = (TextView) findViewById(R.id.tv01);
        tv02 = (TextView) findViewById(R.id.tv02);
        tv03 = (TextView) findViewById(R.id.tv03);
        tv04 = (TextView) findViewById(R.id.tv04);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        mCoordinatorLayout = (CoordinatorLayout)findViewById(R.id.mCoordinatorLayout);

    }

    @Override
    public void initData() {

    }

    @Override
    public void setListeners() {
        tv01.setOnClickListener(this);
        tv02.setOnClickListener(this);
        tv03.setOnClickListener(this);
        tv04.setOnClickListener(this);
        fab.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv01:
                AbSnackBarUtil.ShortSnackbar(tv01,"天啊，我竟然出错了！",AbSnackBarUtil.white,AbSnackBarUtil.black)
                        .setActionTextColor(AbSnackBarUtil.green).setAction("再来", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

                break;
            case R.id.tv02:
                if (fab.getVisibility()==View.VISIBLE){
                fab.setVisibility(View.GONE);
                }else {
                fab.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.tv04:
                startActivity(new Intent(this,RippleActivity.class));
                break;
            case R.id.fab:
                AbSnackBarUtil.ShortSnackbar(fab,"天啊，我竟然出错了！",AbSnackBarUtil.white,AbSnackBarUtil.black)
                        .setActionTextColor(AbSnackBarUtil.green).setAction("再来", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

                break;

            case R.id.tv03:
           //     startActivity(new Intent(this,SampleActivity.class));
                break;
            default:
                break;
        }
    }
}
