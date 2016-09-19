package com.standardproject.base;

/**
 * @ explain:
 * @ author：xujun on 2016/5/13 15:45
 * @ email：gdutxiaoxu@163.com
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的通用Adapter，需重写convert(Helper helper, T item)方法
 * <p/>
 * 注意：convert(BaseCommonAdapter.Helper helper, T item);子类这个方法的参数需要一致，即BaseCommonAdapter.Helper
 * helper
 * <p/>
 * Created by Domen、on 2016/5/5.
 */
public abstract class BaseRecycleAdapter
        <T> extends RecyclerView.Adapter<BaseRecycleHolder> {

    protected final Context mContext;
    protected final int mItemLayoutId;
    protected List<T> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private OnLongItemClickListener mOnLongItemClickListener;

    protected int mScreenWidth;

    /**
     * @param context      context
     * @param itemLayoutId 布局的layout的Id
     */
    public BaseRecycleAdapter(Context context, int itemLayoutId) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = new ArrayList<>();

    }

    public BaseRecycleAdapter(Context context, int itemLayoutId,List<T> datas) {
        mContext = context;
        mItemLayoutId = itemLayoutId;
        mDatas = datas;

    }

    @Override
    public void onBindViewHolder(final BaseRecycleHolder helper, int position) {
        if( mOnItemClickListener!= null) {
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onClick(v,helper.getAdapterPosition());
                }
            });

        }
        if(mOnLongItemClickListener!=null){
            helper.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnLongItemClickListener.onLongClick(v,helper.getAdapterPosition());
                    return false;
                }
            });
        }
        convert(helper, mDatas.get(position));
    }

    /**
     * @param helper 自定义的ViewHolder对象，可以getView获取控件
     * @param item   对应的数据
     */
    public abstract void convert(BaseRecycleHolder helper, T item);

    @Override
    public BaseRecycleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseRecycleHolder(LayoutInflater.from(mContext).inflate(mItemLayoutId, parent, false));
    }

    @Override
    public int getItemCount() {
        return isEmpty() ? 0 : mDatas.size();
    }

    public boolean isEmpty() {
        return mDatas == null|| mDatas.size()==0;
    }

    /**
     * 设置列表中的数据
     */
    public void setDatas(List<T> datas) {
        if(datas ==null){
            return;
        }
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public List<T> getDatas(){
        return mDatas;
    }


    public void notifyItemChange(int postion){
        this.notifyItemChanged(postion);
    }

    /**
     * 将单个数据添加到列表中
     */
    public void addSingleDate(T t) {
        if(t==null){
            return;
        }
        this.mDatas.add(t);
        notifyItemInserted(mDatas.size() - 1);
    }

    public void addSingleDate(T t, int position) {
        mDatas.add(position,t );
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData( int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size());
    }


    public void removeData(T t){
        int index = mDatas.indexOf(t);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, mDatas.size());
    }

    /**
     * 将一个List添加到列表中
     */
    public void addDates(List<T> dates) {
        if(dates==null || dates.size()==0){
            return;
        }
        this.mDatas.addAll(dates);
        notifyDataSetChanged();
    }

    public void clearDates() {
        if (!isEmpty()) {
            this.mDatas.clear();
        }

    }

    public interface OnItemClickListener{
        void onClick(View v, int position);

    }

    public interface OnLongItemClickListener {
        void onLongClick(View v, int position);
    }

    /**
     * 设置点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this. mOnItemClickListener=onItemClickListener;
    }

    /**
     * 设置长按点击事件
     * @param onLongItemClickListener
     */
    public void setonLongItemClickListener(OnLongItemClickListener onLongItemClickListener){
        this. mOnLongItemClickListener=onLongItemClickListener;
    }
}

