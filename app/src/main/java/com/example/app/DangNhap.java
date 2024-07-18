package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DangNhap extends AppCompatActivity {

    TextView tvDangKy;
    Button btnDangNhap;
    EditText edtTK, edtMK;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        databaseHelper = new DatabaseHelper(this);

        tvDangKy = findViewById(R.id.tvDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        edtTK = findViewById(R.id.edtTK);
        edtMK = findViewById(R.id.edtMK);

        // khi nhấn vào nút Đăng nhập
        // khi nhấn vào nút Đăng nhập
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtTK.getText().toString().trim();
                String password = edtMK.getText().toString().trim();

                // Kiểm tra đăng nhập
                if (checkLogin(username, password)) {
                    // Đăng nhập thành công
                    saveLoginInfo(username);
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                    // Chuyển sang MainActivity hoặc màn hình chính
                    Intent intent = new Intent(DangNhap.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // Đăng nhập thất bại
                    Toast.makeText(DangNhap.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // khi nhấn vào TextView Đăng ký
        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangNhap.this, DangKy.class);
                startActivity(intent);
            }
        });

    }
    private boolean checkLogin(String username, String password) {

        return databaseHelper.checkUser(username, password);
    }

    private void saveLoginInfo(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
}