package com.example.app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

public class PaymentFragment extends Fragment {

    private RadioGroup radioGroupPaymentMethods;
    private Button btnConfirmPayment;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        radioGroupPaymentMethods = view.findViewById(R.id.radioGroupPaymentMethods);
        btnConfirmPayment = view.findViewById(R.id.btnConfirmPayment);

        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePaymentConfirmation();
            }
        });

        return view;
    }

    private void handlePaymentConfirmation() {
        int selectedPaymentMethodId = radioGroupPaymentMethods.getCheckedRadioButtonId();

        // Xóa hết các sản phẩm trong giỏ hàng
        CartManager.getInstance().clearCart();

        // Hiển thị thông báo thanh toán thành công
        Toast.makeText(getContext(), "Đã thanh toán thành công", Toast.LENGTH_SHORT).show();

        // Chuyển về màn hình CartFragment
        getParentFragmentManager().popBackStack(); // Đóng fragment hiện tại (PaymentFragment)
        CartFragment cartFragment = new CartFragment();
        getParentFragmentManager().beginTransaction()
                .replace(R.id.container, cartFragment)
                .commit();

        // Cập nhật lại badge trên MainActivity
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.updateCartBadge();
        }
    }
}