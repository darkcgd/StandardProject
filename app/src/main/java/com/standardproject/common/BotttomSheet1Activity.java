package com.standardproject.common;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cocosw.bottomsheet.BottomSheet;
import com.cocosw.bottomsheet.BottomSheetHelper;
import com.standardproject.R;
import com.standardproject.widget.ProgressDialogFragment;
import com.ugiant.AbActivity;

/**
 * Created by chijiaduo on 2016/7/20.
 */
public class BotttomSheet1Activity extends AbActivity implements View.OnClickListener {

    private Button bt_base ,bt_grid , bt_own_layout  ,bt_share_expand , bt_share_all,bt_bottom_dialog;
    private BottomSheet bottomSheet;
    Intent shareIntent = new Intent();

    private View bottomSheetView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet1);
        initViews();
        initData();
        setListeners();
    }

    @Override
    public void initViews() {
        bt_base = (Button) findViewById(R.id.bt_base);
        bt_grid = (Button) findViewById(R.id.bt_grid);
        bt_own_layout = (Button) findViewById(R.id.bt_own_layout);
        bt_share_expand = (Button) findViewById(R.id.bt_share_expand);
        bt_share_all = (Button) findViewById(R.id.bt_share_all);
        bt_bottom_dialog = (Button) findViewById(R.id.bt_botton_dialog);
    }

    @Override
    public void initData() {

        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "hello");

    }

    @Override
    public void setListeners() {
        bt_base.setOnClickListener(this);
        bt_grid.setOnClickListener(this);
        bt_own_layout.setOnClickListener(this);
        bt_share_expand.setOnClickListener(this);
        bt_share_all.setOnClickListener(this);
        bt_bottom_dialog.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //基本使用，可添加黑色主题 .darkTheme()
            case R.id.bt_base:
                bottomSheet = new BottomSheet.Builder(BotttomSheet1Activity.this).icon(R.mipmap.ic_qq_white_48dp).title("更多").sheet(R.menu.product_detail_menu).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case R.id.tv_back_main_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "返回首页" , Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.tv_share_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "分享商品" , Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.tv_collect_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "我的收藏" , Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).show();
                break;
            //.grid()切换为grid布局
            case R.id.bt_grid:
                bottomSheet = new BottomSheet.Builder(BotttomSheet1Activity.this).icon(R.mipmap.ic_qq_white_48dp).title("更多").grid().sheet(R.menu.product_detail_menu).listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case R.id.tv_back_main_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "返回首页" , Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.tv_share_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "分享商品" , Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.tv_collect_dialog:
                                Toast.makeText(BotttomSheet1Activity.this , "我的收藏" , Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }).show();
                break;
            case R.id.bt_own_layout:


                break;
            //展开更多 limit里的值要在integer.xml里定义，当limit值为0时，一次显示全部；limit表示行数。
            case R.id.bt_share_expand:
                bottomSheet = BottomSheetHelper.shareAction(this , shareIntent ).limit(R.integer.limit).show();
                break;
            //全显示
            case R.id.bt_share_all:
                bottomSheet = BottomSheetHelper.shareAction(this , shareIntent ).limit(R.integer.no_limit).show();
                break;

            //底部弹窗效果，继承自DialogFragment
            case R.id.bt_botton_dialog:
                ProgressDialogFragment pdf = ProgressDialogFragment.newInstance(R.layout.dialog_progress, new ProgressDialogFragment.CallBack() {
                    @Override
                    public void initView(Dialog dialog) {
                        ProgressBar pbar = (ProgressBar) dialog.findViewById(R.id.pbar);
                        TextView tv_login = (TextView) dialog.findViewById(R.id.tv_login);
                        tv_login.setText("好罗");
                    }
                });
                pdf.show(getSupportFragmentManager(),"progress_bottom_dialog");
                break;
        }
    }






    }

