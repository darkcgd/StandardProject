package com.standardproject.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;

import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.standardproject.R;
import com.ugiant.AbActivity;

import java.util.zip.Inflater;

/**
 * Created by chijiaduo on 2016/7/20.
 */
public class BottomSheet2Activity extends AbActivity implements View.OnClickListener {
    private Button bt_load_fragment , bt_load_fragment1 ,bt_list ,bt_grid;
    protected BottomSheetLayout bottomSheetLayout;

    private View bottomSheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomsheet2);

        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {
        bt_load_fragment = (Button) findViewById(R.id.bt_load_fragment);
        bt_list = (Button) findViewById(R.id.bt_list);
        bt_grid = (Button) findViewById(R.id.bt_grid);
        bottomSheetLayout = (BottomSheetLayout) findViewById(R.id.bottomsheet_layout_id);
        bt_load_fragment1 = (Button) findViewById(R.id.bt_load_fragment1);

        //bottomSheetLayout.setContentView(LayoutInflater.from(this).inflate(R.layout.activity_bottom_sheet_content_view , null));
    }

    @Override
    public void initData() {
        bottomSheetLayout.setPeekOnDismiss(true);
    }

    @Override
    public void setListeners() {
        bt_load_fragment.setOnClickListener(this);
        bt_list.setOnClickListener(this);
        bt_grid.setOnClickListener(this);
        bt_load_fragment1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.bt_load_fragment:
                new FlipboardFragment().show(getSupportFragmentManager() , R.id.bottomsheet_layout_id);
                break;
            case R.id.bt_load_fragment1:
                new FlipboardMenuFragment().show(getSupportFragmentManager() , R.id.bottomsheet_layout_id);
                break;
            case R.id.bt_list:
                showListMenu();
                break;
            case R.id.bt_grid:
               // showGridMenu();

                if(bottomSheetView==null){
                    bottomSheetView = createBottomSheetView();
                }
                if(bottomSheetLayout.isSheetShowing()){
                    bottomSheetLayout.dismissSheet();
                }else {
                        bottomSheetLayout.showWithSheetView(bottomSheetView);
                }
                break;
        }
    }




    private void showListMenu() {
        MenuSheetView listMenuView = new MenuSheetView(BottomSheet2Activity.this, MenuSheetView.MenuType.LIST, "", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tv_back_main_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "返回首页" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_share_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "分享商品" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_collect_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "我的收藏" , Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        listMenuView.inflateMenu(R.menu.product_detail_menu);
        bottomSheetLayout.showWithSheetView(listMenuView);
    }

    private void showGridMenu() {
        MenuSheetView gridMenuView = new MenuSheetView(BottomSheet2Activity.this, MenuSheetView.MenuType.GRID, "更多", new MenuSheetView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.tv_back_main_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "返回首页" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_share_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "分享商品" , Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.tv_collect_dialog:
                        Toast.makeText(BottomSheet2Activity.this , "我的收藏" , Toast.LENGTH_SHORT).show();
                        break;
                }
                if (bottomSheetLayout.isSheetShowing()) {
                    bottomSheetLayout.dismissSheet();
                }
                return true;
            }
        });
        gridMenuView.inflateMenu(R.menu.product_detail_menu);
        bottomSheetLayout.showWithSheetView(gridMenuView);
    }

    private View createBottomSheetView(){
        View view = LayoutInflater.from(this).inflate(R.layout.activity_comment,(ViewGroup) getWindow().getDecorView(),false);

        return view;
    }


}
