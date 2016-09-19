package com.standardproject.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.standardproject.R;
import com.ugiant.AbActivity;

/**
 * Created by chijiaduo on 2016/7/21.
 */
public class ToolbarActivity extends AbActivity {
    private Toolbar toolbar1 , toolbar2 ,toolbar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {
        toolbar1 = (Toolbar) findViewById(R.id.toolbar1);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
//        toolbar3 = (Toolbar) findViewById(R.id.toolbar3);

    }

    @Override
    public void initData() {
     initToolbar1();
        initToolbar2();
 //       initToolbar3();

    }

    private void initToolbar3() {
        toolbar3.inflateMenu(R.menu.product_detail_menu);
    }

    private void initToolbar2() {

        toolbar2.setNavigationIcon(R.drawable.wechat);
        toolbar2.setTitle("大标题是红色");
        toolbar2.setTitleTextColor(getResources().getColor(R.color.text_color_red));
        toolbar2.setSubtitle("子标题是黑色");
        toolbar2.setSubtitleTextColor(getResources().getColor(R.color.text_color_black));
        toolbar2.setBackgroundColor(getResources().getColor(R.color.background_home_orange));



    }

    private void initToolbar1() {
    toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Toast.makeText(ToolbarActivity.this , "返回按钮或拉出菜单" , Toast.LENGTH_SHORT).show();
    }
});
        toolbar1.inflateMenu(R.menu.product_detail_menu);
       toolbar1.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
           @Override
           public boolean onMenuItemClick(MenuItem item) {
               switch (item.getItemId()){
                   case R.id.tv_back_main_dialog:
                       Toast.makeText(ToolbarActivity.this , "主页" , Toast.LENGTH_SHORT).show();
                       break;
                   case R.id.tv_share_dialog:
                       Toast.makeText(ToolbarActivity.this , "分享" , Toast.LENGTH_SHORT).show();
                       break;
               }
               return true;
           }
       });



    }

    @Override
    public void setListeners() {

    }



}

// class AbToolBar{
//     private Context mContext;
//     private Toolbar toolbar;
//     private View mRootView;
//     private int mToolBarID;
//     private LayoutInflater layoutInflater;
//
//     public AbToolBar(  Context context ,int toolbarID , int rootView){
//         mContext = context;
//        layoutInflater = LayoutInflater.from(mContext);
//         mRootView = layoutInflater.inflate(rootView , null);
//         mToolBarID = toolbarID;
//         toolbar = (Toolbar) mRootView.findViewById(mToolBarID);
//
//     }
//
//     public void setBackGroundColor(int color){
//         toolbar.setBackgroundColor(color);
//     }
//
//     public void setTitleText(CharSequence title){
//         toolbar.setTitle(title);
//     }
//
//     public void setTitleColor(int color){
//         toolbar.setTitleTextColor(color);
//     }
//
//        }

