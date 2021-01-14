package com.example.moneylover2.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.moneylover2.dao.CategoryDao;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Category;

import java.util.List;

public class CategoryRepository {

    private CategoryDao categoryDao;
    private LiveData<List<Category>> allCategories;
    private List<String> allCategoryName;

    public CategoryRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getDatabase(application);

        categoryDao = db.categoryDao();
    }

    public void insert(Category category) {
        TransactionDatabase.databaseWriteExecutor.execute(() -> categoryDao.insert(category));
    }

    public LiveData<List<Category>> getAllCategories() {
        allCategories = categoryDao.getAllCategories();
        return allCategories;
    }

    public List<String> getAllCategoryName() {
        allCategoryName = categoryDao.getAllCategoryName();
        return allCategoryName;
    }

    public void deleteAll() {
        new CategoryRepository.deleteAllCategoriesAsyncTask(categoryDao).execute();
    }

    public void delete(Category category) {
        new CategoryRepository.deleteCategoryAsyncTask(categoryDao).execute(category);
    }

    // to do the delete asynchronous
    private static class deleteAllCategoriesAsyncTask extends AsyncTask<Void, Void, Void> {

        private CategoryDao categoryDao;

        deleteAllCategoriesAsyncTask(CategoryDao dao) {
            this.categoryDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            categoryDao.deleteAll();
            return null;
        }
    }

    private static class deleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {

        private CategoryDao categoryDao;

        deleteCategoryAsyncTask(CategoryDao dao) {
            this.categoryDao = dao;
        }

        @Override
        protected Void doInBackground(final Category... params) {
            categoryDao.delete(params[0]);
            return null;
        }
    }

}
