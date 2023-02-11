package com.example.recyclerviewproject;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContactDetailsFragment extends Fragment {

    ImageView imgView;
    TextView nameTextView;
    TextView numberTextView;

    private static final String ARG_PARAM1 = "image";
    private static final String ARG_PARAM2 = "name";
    private static final String ARG_PARAM3 = "number";

    private int img_arg;
    private String name_arg;
    private String number_arg;

    public ContactDetailsFragment() {
        // Required empty public constructor
    }

    public static ContactDetailsFragment newInstance(int img,String name, String number) {
        ContactDetailsFragment fragment = new ContactDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, img);
        args.putString(ARG_PARAM2, name);
        args.putString(ARG_PARAM3, number);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            img_arg = getArguments().getInt(ARG_PARAM1);
            name_arg = getArguments().getString(ARG_PARAM2);
            number_arg = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact_details, container, false);
        imgView=view.findViewById(R.id.contact_img_imageView);
        imgView.setImageResource(img_arg);
        nameTextView=view.findViewById(R.id.contact_name_textView);
        nameTextView.setText(name_arg);
        numberTextView=view.findViewById(R.id.contact_number_textView);
        numberTextView.setText(number_arg);
        return view;
    }

}