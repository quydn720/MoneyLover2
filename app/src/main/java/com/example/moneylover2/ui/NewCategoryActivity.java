package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.adapter.CategoryListAdapter;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.viewmodel.CategoryViewModel;

import java.io.Serializable;
import java.util.List;

public class NewCategoryActivity extends AppCompatActivity {

    public static final String ALL_CATEGORIES = "com.example.android.moneylover.ALL_CATEGORIES";

    private EditText editText_new_category;

    private CategoryViewModel categoryViewModel;

    private List<Category> allCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);

        editText_new_category = findViewById(R.id.editText_new_category);

        RecyclerView recyclerView = findViewById(R.id.recyclerview_category);
        CategoryListAdapter adapter = new CategoryListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        // Update the cached copy of the words in the adapter.
        categoryViewModel.getAllCategories().observe(this, categories -> {
            adapter.setAllCategories(categories);
            allCategories = categories;
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent();
        intent.putExtra(ALL_CATEGORIES, (Serializable) allCategories);
        setResult(RESULT_OK, intent);
        finish();
    }

    //region Event - Add new Category
    public void addNewCategory(View view) {

        try {
            String name = editText_new_category.getText().toString();
            Category category = new Category(name);
            categoryViewModel.insert(category);
        } catch (Exception e) {
            Toast.makeText(this, "You must enter the category title", Toast.LENGTH_LONG).show();
        }
    }
    //endregion

}