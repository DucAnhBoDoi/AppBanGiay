package com.example.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<Product> productList;
    private ProductAdapter adapter;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        String jsonFileString = loadJSONFromAsset("products.json");
        if (jsonFileString != null) {
            try {
                JSONObject jsonObject = new JSONObject(jsonFileString);
                JSONArray productsArray = jsonObject.getJSONArray("products");
                productList = new ArrayList<>();

                for (int i = 0; i < productsArray.length(); i++) {
                    JSONObject productObject = productsArray.getJSONObject(i);

                    String image = productObject.getString("image");
                    String name = productObject.getString("name");
                    String price = productObject.getString("price");
                    String description = productObject.getString("description");
                    JSONArray sizeArray = productObject.getJSONArray("size");
                    List<Integer> sizeList = new ArrayList<>();
                    for (int j = 0; j < sizeArray.length(); j++) {
                        sizeList.add(sizeArray.getInt(j));
                    }

                    int drawableId = getResources().getIdentifier(image.split("\\.")[0], "drawable", requireContext().getPackageName());

                    Product product = new Product(name, price, sizeList, drawableId, description);
                    productList.add(product);
                }

                adapter = new ProductAdapter(getContext(), productList);
                adapter.setOnItemClickListener(product -> {
                    Bundle bundle = new Bundle();
                    bundle.putString("name", product.getName());
                    bundle.putString("price", product.getPrice());
                    bundle.putInt("drawableId", product.getDrawableId());
                    bundle.putString("description", product.getDescription());
                    bundle.putIntegerArrayList("size", (ArrayList<Integer>) product.getSize());

                    DetailFragment detailFragment = new DetailFragment();
                    detailFragment.setArguments(bundle);

                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container, detailFragment)
                            .addToBackStack(null)
                            .commit();
                });

                recyclerView.setAdapter(adapter);

                // Thiết lập sự kiện tìm kiếm
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        filter(newText);
                        return true;
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("HomeFragment", "Error loading JSON from assets");
        }

        return view;
    }

    private void filter(String text) {
        List<Product> filteredList = new ArrayList<>();

        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(product);
            }
        }

        adapter.filterList(filteredList);
    }

    private String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = requireContext().getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }
}
