package com.standardproject.util;

import android.app.Activity;
import android.content.DialogInterface;

import com.cocosw.bottomsheet.BottomSheet;
import com.standardproject.R;

/**
 * Created by yangpeixian on 2016/7/26.
 */
public class BottomSheetUtil {

    public static BottomSheet bottomSheet;

    public static void show(final Activity activity) {
        bottomSheet = new BottomSheet.Builder(activity).icon(R.mipmap.ic_qq_white_48dp).title("更多").sheet(R.menu.bottom_sheet_util_menu).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                switch (i) {
//                    case R.id.tv_back_main_dialog:
//                        Toast.makeText(activity, "返回首页", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.tv_share_dialog:
//                        Toast.makeText(activity, "分享商品", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.tv_collect_dialog:
//                        Toast.makeText(activity, "我的收藏", Toast.LENGTH_SHORT).show();
//                        break;
//                }
            }
        }).show();

    }
}
