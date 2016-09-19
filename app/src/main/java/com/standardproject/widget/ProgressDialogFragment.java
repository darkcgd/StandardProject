package com.standardproject.widget;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.standardproject.R;

import java.io.Serializable;

/**
 * Created by zbz on 2016/8/3.
 */
public class ProgressDialogFragment extends DialogFragment {
    private ProgressBar pbar;
    private TextView tv_login;
    private Dialog dialog;
    private CallBack callBack;

    //传递参数给Bundle,存放在ProgressDialogFragment的Arguments中
    public static ProgressDialogFragment newInstance(int resId,CallBack callBack){

        Bundle bundle = new Bundle();
        bundle.putInt("resId",resId);
        bundle.putSerializable("callback",callBack);

        ProgressDialogFragment pdf = new ProgressDialogFragment();
        pdf.setArguments(bundle);
        return pdf;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int resId =  bundle.getInt("resId");//取出布局参数
        callBack = (CallBack) bundle.getSerializable("callback");

        dialog = new Dialog(getActivity(), R.style.progress_dialog_fragment);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(resId);    //设置dialog布局
        dialog.setCanceledOnTouchOutside(true);     //允许外部点击取消

        //设置宽度为屏幕宽度，靠近屏幕底部
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;    //紧贴底部
        lp.width= WindowManager.LayoutParams.MATCH_PARENT;//宽度持平
        lp.windowAnimations=R.style.dialogAnim;     //设置动画效果
        window.setAttributes(lp);

       // initView();
        callBack.initView(dialog);
        return dialog;
    }


    private void initView() {
        pbar = (ProgressBar) dialog.findViewById(R.id.pbar);
        tv_login = (TextView) dialog.findViewById(R.id.tv_login);
    }


    public interface  CallBack extends Serializable{
        void initView(Dialog dialog);
    }


}
