package com.standardproject.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.standardproject.R;
import com.standardproject.util.LUtils;


/**
 * @author xujun
 * @explain
 * @time 2016/5/5 18:03.
 */
public class PulltoRefreshRecyclerView extends LinearLayout {

    String Tag = "xujun";

    /**
     * 内容控件
     */
    private RecyclerView mRecyclerView;
    /**
     * 刷新布局控件
     */
    private SwipeRefreshLayout mSwipeRfl;
    private LinearLayoutManager mLayoutManager;
    /*
     * 刷新布局的监听
     */
    private OnRefreshListener mRefreshListener;
    /**
     * 内容控件滚动监听
     */
    private RecyclerView.OnScrollListener mScrollListener;

    /*
     * 刷新加载监听事件
     */
    private onRefreshListener mRefreshLoadMoreListner;

    /**
     * 是否正在刷新
     */
    private boolean isRefresh = false;
    /**
     * 是否正在加载更多
     */
    private boolean isLoadMore = false;
    private FrameLayout mFootView;
    //    是否允许加载更多
    private boolean enableLoadMore = true;
    private int mMeasuredHeight;

    public PulltoRefreshRecyclerView(Context context) {
        this(context, null);
    }

    @SuppressWarnings("deprecation")
    public PulltoRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 导入布局
        initView(context);


        /**
         * 监听上拉至底部滚动监听
         */
        initListener();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setOnScrollListener(mScrollListener);
    }

    private void setFootViewHeight(int height) {
        ViewGroup.LayoutParams layoutParams = mFootView.getLayoutParams();
        layoutParams.height = height;
        mFootView.setLayoutParams(layoutParams);
    }

    private void initListener() {
        mScrollListener = new RecyclerView.OnScrollListener() {

            private int mLastVisibleItem;
            private int mDy;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }

                //最后显示的项
                mLastVisibleItem = mLayoutManager.findLastCompletelyVisibleItemPosition();

                int totalItemCount = mLayoutManager.getItemCount();

                /**
                 * 只有在下拉，并且没有早加载更多，并且允许加载更多，并且在最后一个条目，才调用加载更多的接口
                 */
                if (mDy >= 0 && !isLoadMore && (mLastVisibleItem >= totalItemCount - 1) &&
                        enableLoadMore) {
                    loadMore();


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDy = dy;
            }

        };

        /**
         * 下拉至顶部刷新监听
         */
        mRefreshListener = new OnRefreshListener() {

            @Override
            public void onRefresh() {
            /*    Log.i("Tag", "onRefresh1");
                Log.i("Tag", isRefresh + "");
                Log.i("Tag", isRefresh + "");
                Log.i("Tag", isLoadMore + "");*/
                LUtils.i("isLoadMore=" + isLoadMore);
                if (!isRefresh && !isLoadMore) {//没有正在刷新或者正在加载更多
                    isRefresh = true;
                    refresh();
                }
            }
        };
        mSwipeRfl.setOnRefreshListener(mRefreshListener);
    }

    private void initView(Context context) {
        View.inflate(context, R.layout.pulltorefreshrecyclerview,this);
        mSwipeRfl = (SwipeRefreshLayout) findViewById(R.id.all_swipe);
        mSwipeRfl.setColorSchemeResources(R.color.blueStatus);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        initFootView();


    }

    private void initFootView() {
        mFootView = (FrameLayout) findViewById(R.id.ll_load_more);
        LUtils.i("mFootView=" +(mFootView==null));
        mFootView.measure(0, 0);
        mMeasuredHeight = mFootView.getMeasuredHeight();
        setFootViewHeight(0);

    }


    public void setFooterView(View view) {
        if (getFooterView() != null) {
            getFooterView().removeAllViews();
            mFootView.addView(view);
        }

    }

    public FrameLayout getFooterView() {
        return mFootView;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(layoutManager);
        }
    }

    /**
     * 设置是否允许下拉刷新
     *
     * @param enable
     */
    public void setPullRefreshEnable(boolean enable) {
        setRefreshEnabled(enable);
    }

    public boolean getPullRefreshEnable() {
        return mSwipeRfl.isEnabled();
    }

    public void setLoadMoreListener() {
        mRecyclerView.setOnScrollListener(mScrollListener);
    }

    public void loadMore() {
        if (mRefreshLoadMoreListner != null) {
            isLoadMore = true;
            //设置在加载更多的时候swipeLayout不允许加载更多，同时需要设置在加载更多完毕的时候允许swipeLayout加载更多
            setRefreshEnabled(false);
            mLayoutManager.scrollToPosition(mLayoutManager.findLastVisibleItemPosition());
            showFootView();
            mRefreshLoadMoreListner.onLoadMore(mRecyclerView);

        }
    }

    public void setRefreshEnabled(boolean enabled) {
        mSwipeRfl.setEnabled(enabled);
    }

    private void showFootView() {
        setFootViewHeight(mMeasuredHeight);

    }

    public void OnRefreshCompleted() {
        if (isRefresh) {//如果是正在刷新，就调用这个方法
            stopRefresh();
        } else if (isLoadMore) {//如果是正在加载更多，就调用这个方法
            setLoadMoreCompleted();
        }


    }

    /**
     * 加载更多完毕,为防止频繁网络请求,isLoadMore为false才可再次请求更多数据
     */
    private void setLoadMoreCompleted() {

//因为在加载更多的时候设置swipeLayout不允刷新，
// 所以加载更多完毕的时候需要设置允许swipeLayout允许刷新
        setRefreshEnabled(true);
        isLoadMore = false;
        hideFootView();
    }

    private void hideFootView() {
//        setFootViewHeight(0);
        rollbackFootView(mFootView);
    }

    private void stopRefresh() {
        isRefresh = false;
        mSwipeRfl.setRefreshing(false);

    }

    public void setOnRefreshListener(onRefreshListener listener) {
        mRefreshLoadMoreListner = listener;
    }

    public void refresh() {
        if (mRefreshLoadMoreListner != null) {
            mRefreshLoadMoreListner.onRefresh(mRecyclerView);
        }
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        if (adapter != null)
            mRecyclerView.setAdapter(adapter);
    }

    public interface onRefreshListener {

        public void onRefresh(RecyclerView recyclerView);

        public void onLoadMore(RecyclerView recyclerView);
    }

    /**
     * 回滚下拉刷新头部控件
     */
    private void rollbackFootView(View view) {

        final ValueAnimator rbAnimator = ValueAnimator.ofInt(0, mMeasuredHeight);
        rbAnimator.setDuration(800);
        rbAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        rbAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedFraction = animation.getAnimatedFraction();

                /**
                 * 当刷新完毕后，进行headerView的隐藏
                 */
                setFootViewHeight((int) (mMeasuredHeight - animatedFraction * mMeasuredHeight));
            }


        });
        rbAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                rbAnimator.removeAllListeners();
            }
        });
        rbAnimator.start();

    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public boolean isEnableLoadMore() {
        return enableLoadMore;
    }
}
