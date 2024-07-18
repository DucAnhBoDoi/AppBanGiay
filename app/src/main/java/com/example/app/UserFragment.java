package com.example.app;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class UserFragment extends Fragment {

    private ImageView avatarImageView;
    private TextView usernameTextView;
    private TextView donMuaTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        avatarImageView = view.findViewById(R.id.avatarImageView);
        usernameTextView = view.findViewById(R.id.usernameTextView);
        donMuaTextView = view.findViewById(R.id.donMuaTextView);

        // Đọc thông tin đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginPrefs", getContext().MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "Guest");

        // Hiển thị thông tin tài khoản
        usernameTextView.setText(username);
        avatarImageView.setImageResource(R.drawable.avatar); // thay avatar bằng resource của bạn

        return view;
    }
}