package com.standardproject.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.standardproject.R;

/**
 * !!!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!
 * !!!!!!!!!!!!!!!
 * 继承BottomSheetFragment
 */
public class FlipboardFragment extends BottomSheetFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flipboard , container , false);
        return view;
    }
}
