package com.standardproject.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.flipboard.bottomsheet.commons.BottomSheetFragment;
import com.standardproject.R;

/**
 * Created by chijiaduo on 2016/7/25.
 */
public class FlipboardMenuFragment extends BottomSheetFragment {

    private ImageButton img;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_flipboard_menu, container, false);
        img = (ImageButton) view.findViewById(R.id.img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "点击图标", Toast.LENGTH_SHORT).show();

            }
        });
        return view;
    }
}
