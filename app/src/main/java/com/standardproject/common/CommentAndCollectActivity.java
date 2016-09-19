package com.standardproject.common;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.standardproject.R;
import com.standardproject.TheApp;
import com.ugiant.AbActivity;
import com.ugiant.util.AbToastUtil;

/**
 * Created by yangpeixian on 2016/7/21.
 * 收藏、点赞工具类
 */
public class CommentAndCollectActivity extends AbActivity implements View.OnClickListener {
    private TextView tv_title_left;
    private TextView tv_title_right;
    private TextView tv_title_content;
    private TextView tv_comment;
    ImageView img_like;
    ImageView img_collect;
    EditText et_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_and_collect);
        initViews();
        initData();
        setListeners();

    }

    @Override
    public void initViews() {
        tv_title_left = (TextView) findViewById(R.id.tv_title_left);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        tv_title_right.setVisibility(View.INVISIBLE);
        tv_title_content = (TextView) findViewById(R.id.tv_title_content);
        tv_title_content.setText("评论点赞");
        img_collect=(ImageView) findViewById(R.id.img_collect);
        img_like=(ImageView) findViewById(R.id.img_like);
        tv_comment=(TextView) findViewById(R.id.tv_comment);
        et_content=(EditText) findViewById(R.id.et_content);
    }

    @Override
    public void initData() {
        tv_title_left.setOnClickListener(this);
        img_collect.setOnClickListener(this);
        img_like.setOnClickListener(this);
        tv_comment.setOnClickListener(this);


    }

    @Override
    public void setListeners() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_left:
                finish();
                break;
            case R.id.tv_comment:
                String content=et_content.getText().toString();
                if (content==null||"".equals(content)){
                    AbToastUtil.showToast(TheApp.instance,"请输入评论内容");
                    break;
                }
                AbToastUtil.showToast(TheApp.instance,"评论成功");
                //调用CollectAndCommentUtil doCollectOrComment方法
                // CollectAndCommentUtil.doCollectOrComment(null,null,null);
                break;
            case R.id.img_collect:
                AbToastUtil.showToast(TheApp.instance,"收藏成功");
                //调用CollectAndCommentUtil doCollectOrComment方法
               // CollectAndCommentUtil.doCollectOrComment(null,null,null);
                break;
            case R.id.img_like:
                AbToastUtil.showToast(TheApp.instance,"点赞成功");
                //调用CollectAndCommentUtil doCollectOrComment方法
                //CollectAndCommentUtil.doCollectOrComment(null,null,null);
                break;
            default:
                break;
        }
    }
}
