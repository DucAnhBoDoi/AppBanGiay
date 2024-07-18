package com.example.app;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private List<Product> cartProductList;
    private int cartItemCount = 0;

    private CartManager() {
        cartProductList = new ArrayList<>();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    public void addProductToCart(Product product) {
        cartProductList.add(product);
        cartItemCount++;
    }

    public void removeProductFromCart(Product product) {
        cartProductList.remove(product);
        cartItemCount--;
    }

    public int getCartItemCount() {
        return cartItemCount;
    }

    public List<Product> getCartProductList() {
        return cartProductList;
    }

    public void clearCart() {
        cartProductList.clear();
        cartItemCount = 0;
    }
}
