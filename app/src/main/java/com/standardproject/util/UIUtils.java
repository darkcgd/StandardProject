package com.standardproject.util;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.widget.Toast;


public class UIUtils {

    private UIUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static Toast mShortToast;//Toast是单例的，注意其显示方式
    private static Toast mLongToast;//Toast是单例的，注意其显示方式


    public static void showShortText(Context context, String text) {

        if (mShortToast == null) {
            mShortToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        mShortToast.setText(text);
        mShortToast.show();
    }




    public static void showShortText(final Activity mActivity, final String text) {
        if (isMainThread()) {
            if (mShortToast == null) {
                mShortToast = Toast.makeText(mActivity, "", Toast.LENGTH_SHORT);
            }
            mShortToast.setText(text);
            mShortToast.show();
        } else {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mShortToast == null) {
                        mShortToast = Toast.makeText(mActivity, "",
                                Toast.LENGTH_SHORT);
                    }
                    mShortToast.setText(text);
                    mShortToast.show();

                }
            });
        }

    }

    public static void showLongText(final Activity mActivity, final String text) {
        if (isMainThread()) {
            if (mLongToast == null) {
                mLongToast = Toast.makeText(mActivity, "", Toast.LENGTH_LONG);
            }
            mLongToast.setText(text);
            mLongToast.show();
        } else {
            mActivity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mLongToast == null) {
                        mLongToast = Toast.makeText(mActivity, "",
                                Toast.LENGTH_SHORT);
                    }
                    mLongToast.setText(text);
                    mLongToast.show();

                }
            });
        }

    }


    /**
     * 判断是否在主线程
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
