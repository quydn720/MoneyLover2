package com.example.moneylover2.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.adapter.CategoryListAdapter;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.viewmodel.CategoryViewModel;

public class NewCategoryActivity extends AppCompatActivity {

    private EditText editText_new_category;

    private CategoryViewModel categoryViewModel;

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

        //region Event - Swipe left to delete category
        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Category category = adapter.getCategoryAtPosition(position);
                        Toast.makeText(NewCategoryActivity.this, "Deleting transaction...", Toast.LENGTH_SHORT).show();

                        categoryViewModel.delete(category);
                        // TODO: Bug: If user delete all the category, app must be restart to populate the default category
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
        //endregion

        // Update the cached copy of the words in the adapter.
        categoryViewModel.getAllCategories().observe(this, adapter::setAllCategories);
    }

    //region Event - Add new Category
    public void addNewCategory(View view) {

        String name = editText_new_category.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "You must enter the category title", Toast.LENGTH_SHORT).show();
        }
        else {
            Category category = new Category(name);
            categoryViewModel.insert(category);
        }

    }
    //endregion

}