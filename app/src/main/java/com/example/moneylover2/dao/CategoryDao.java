package com.example.moneylover2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneylover2.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT name FROM category_table")
    List<String> getAllCategoryName();
}
