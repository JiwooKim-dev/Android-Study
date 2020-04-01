package com.example.lab5b_fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class LeftFragment extends Fragment implements View.OnClickListener {

    private OnColorButtonListener onColorButtonListener;

    public LeftFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {    // Context는 Activity보다 상위 class
        super.onAttach(context);
        onColorButtonListener = (OnColorButtonListener) context;    // Activity를 Listener로 가져옴
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left, container, false);
        view.findViewById(R.id.v_red).setOnClickListener(this);
        view.findViewById(R.id.v_green).setOnClickListener(this);
        view.findViewById(R.id.v_blue).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        /* 인터페이스 만들어서 구현 */

        int color = -1;
        switch(v.getId()){

            case (R.id.v_red):
                color = 0;
                break;

            case(R.id.v_green):
                color = 1;
                break;

            case (R.id.v_blue):
                color = 2;
                break;
        }

        onColorButtonListener.onColorClick(color);
    }
}
