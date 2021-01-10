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

    @Query("SELECT * FROM category_table ORDER BY categoryId DESC")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategoryName();

}
