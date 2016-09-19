package com.ugiant.util;

import android.util.SparseArray;
import android.view.View;

/**
 * 名称：AbViewHolder.java 
 * 描述：超简洁的ViewHolder.
 * 代码更简单，性能略低，可以忽略,一般不建议用这个,建议用AbViewHolder,和AbAdapter配合使用
 * @author cgd
 * @date 2016-01-11
 */
public class AbViewHolderEasy {
    
    /**
     * ImageView view = AbViewHolder.get(convertView, R.id.imageView);
     * @param view
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends View> T get(View view, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();
        
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }
}
