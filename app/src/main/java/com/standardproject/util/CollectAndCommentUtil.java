package com.standardproject.util;

import com.standardproject.TheApp;
import com.ugiant.http.AbRequestParams;
import com.ugiant.http.AbStringHttpResponseListener;
import com.ugiant.util.AbToastUtil;

/**
 * Created by yangpeixian on 2016/7/21.
 * 收藏、点赞、评论工具类
 */
public class CollectAndCommentUtil {

    // 收藏、点赞、评论
    public static void  doCollectOrComment(String url,AbRequestParams params, final CollectAndCommentCallBack callBack){
//        AbRequestParams params = new AbRequestParams();
//        if(map!=null&&map.size()!=0){
//            try {
//                Set<Map.Entry<String, String>> entrySet = map.entrySet();
//                Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();
//                while (iterator.hasNext()) {
//                    Map.Entry<String, String>  mapEntry = (Map.Entry<String, String>) iterator.next();
//                    params.put(mapEntry.getKey(), mapEntry.getValue());
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        if (url==null){
            AbToastUtil.showToast(TheApp.instance,"url为空");
            return;
        }else if(params==null){
            AbToastUtil.showToast(TheApp.instance,"params为空");
            return;
        }else if(callBack==null){
            AbToastUtil.showToast(TheApp.instance,"callBack为空");
            return;
        }
        TheApp.mAbHttpUtil.post(url,params, new AbStringHttpResponseListener() {

            // 获取数据成功会调用这里
            @Override
            public void onSuccess(int statusCode, String content) {
                if (callBack!=null){
                    callBack.success(statusCode,content);
                }
            };
            // 开始执行前
            @Override
            public void onStart() {
                //显示进度框
            }

            // 失败，调用
            @Override
            public void onFailure(int statusCode, String content,Throwable error) {
                if (callBack!=null){
                    callBack.failure(statusCode,content,error);
                }
            }
            // 完成后调用，失败，成功
            @Override
            public void onFinish() {

            };
        });
    }
    //收藏、点赞、评论回调接口
    public interface CollectAndCommentCallBack{
        void success(int statusCode, String content);
        void failure(int statusCode, String content,Throwable error);
    }

}
