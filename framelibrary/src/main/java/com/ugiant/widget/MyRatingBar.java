package com.ugiant.widget;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.RatingBar;

/**
 *  评分星星控件
 * @author cgd
 */

public class MyRatingBar extends RatingBar
{

	public MyRatingBar(Context context)
	{
		super(context);
	}

	public MyRatingBar(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MyRatingBar(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		try
		{
			Class<?> clazz = Class.forName("android.widget.ProgressBar");
			Field field = clazz.getDeclaredField("mSampleTile");
			field.setAccessible(true);
			Object bitmapObject = field.get(this); 
			Bitmap bitmap = (Bitmap) bitmapObject;
			int height = bitmap.getHeight();
			if (height > 0)
			{
//				setMeasuredDimension(getMeasuredWidth(), resolveSizeAndState(height, heightMeasureSpec, 0));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
