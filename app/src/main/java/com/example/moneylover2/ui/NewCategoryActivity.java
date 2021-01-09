package com.example.moneylover2.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.adapter.CategoryListAdapter;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.viewmodel.CategoryViewModel;

public class NewCategoryActivity extends AppCompatActivity {


    private EditText editText_new_category;
    CategoryListAdapter adapter;

    private CategoryViewModel categoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        editText_new_category = findViewById(R.id.editText_new_category);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_category);
        adapter = new CategoryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategories().observe(this, categories -> {
            // Update the cached copy of the words in the adapter.
            adapter.setAllCategories(categories);
        });
    }

    public void addNewCategory(View view) {

        String name = editText_new_category.getText().toString();
        Category category = new Category(name);

        categoryViewModel.insert(category);
    }
}