package com.example.moneylover2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.moneylover2.dao.CategoryDao;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private List<String> allCategoryName;

    public CategoryRepository(Application application){
        TransactionDatabase db = TransactionDatabase.getDatabase(application);

        categoryDao = db.categoryDao();
    }

    public void insert(Category category){
        TransactionDatabase.databaseWriteExecutor.execute(()-> categoryDao.insert(category));
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = categoryDao.getAllCategories();
        return allCategories;
    }

    public List<String> getAllCategoryName(){
        allCategoryName = categoryDao.getAllCategoryName();
        return allCategoryName;
    }
}
