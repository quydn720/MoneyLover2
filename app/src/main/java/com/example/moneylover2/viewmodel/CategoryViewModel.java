package com.example.moneylover2.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneylover2.model.Category;
import com.example.moneylover2.repository.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends AndroidViewModel {

    private CategoryRepository Repository;
    private LiveData<List<Category>> allCategories;
    private List<Category> allCategoryName;

    public CategoryViewModel(@NonNull Application application) {
        super(application);
        Repository = new CategoryRepository(application);
    }

    public void insert(Category category) {
        Repository.insert(category);
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = Repository.getAllCategories();
        return allCategories;
    }

    public List<Category> getAllCategoryName() {
        allCategoryName = Repository.getAllCategoryName();
        return allCategoryName;
    }

    public void deleteAll() {
        Repository.deleteAll();
    }

    public void delete(Category category) {
        Repository.delete(category);
    }

}
