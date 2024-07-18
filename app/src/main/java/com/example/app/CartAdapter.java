package com.example.app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private List<Product> cartProductList;
    private CartUpdateListener cartUpdateListener;

    public CartAdapter(Context context, List<Product> cartProductList, CartUpdateListener cartUpdateListener) {
        this.context = context;
        this.cartProductList = cartProductList;
        this.cartUpdateListener = cartUpdateListener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartProductList.get(position);
        holder.productImageView.setImageResource(product.getDrawableId());
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText(product.getPrice());
        holder.productSizeTextView.setText("Kích thước: " + product.getSelectedSize());
        holder.productQuantityTextView.setText("Số lượng: " + product.getQuantity());

        holder.deleteProductButton.setOnClickListener(v -> {
            cartProductList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, cartProductList.size());
            notifyDataSetChanged(); // Cập nhật lại RecyclerView

            CartManager.getInstance().removeProductFromCart(product);

            // Gọi callback để cập nhật giỏ hàng
            cartUpdateListener.onCartUpdated();
        });
    }

    @Override
    public int getItemCount() {
        return cartProductList.size();
    }

    static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productSizeTextView;
        TextView productQuantityTextView;
        Button deleteProductButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productSizeTextView = itemView.findViewById(R.id.productSizeTextView);
            productQuantityTextView = itemView.findViewById(R.id.productQuantityTextView);
            deleteProductButton = itemView.findViewById(R.id.deleteProductButton);
        }
    }

    // Interface để thông báo khi giỏ hàng được cập nhật
    public interface CartUpdateListener {
        void onCartUpdated();
    }
}
