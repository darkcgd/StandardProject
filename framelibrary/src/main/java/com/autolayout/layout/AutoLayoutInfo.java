package com.autolayout.layout;

import java.util.ArrayList;
import java.util.List;

import android.view.View;

import com.autolayout.attr.AutoAttr;

public class AutoLayoutInfo
{
    private List<AutoAttr> autoAttrs = new ArrayList<AutoAttr>();
    public void addAttr(AutoAttr autoAttr)
    {
        autoAttrs.add(autoAttr);
    }


    public void fillAttrs(View view)
    {
        for (AutoAttr autoAttr : autoAttrs)
        {
            autoAttr.apply(view);
        }
    }

    @Override
    public String toString()
    {
        return "AutoLayoutInfo{" +
                "autoAttrs=" + autoAttrs +
                '}';
    }
}