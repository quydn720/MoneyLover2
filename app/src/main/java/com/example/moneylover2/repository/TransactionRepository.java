package com.example.moneylover2.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.moneylover2.dao.TransactionDao;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Transaction;

import java.util.List;

public class TransactionRepository {

    private TransactionDao transactionDao;
    private LiveData<List<Transaction>> allTransactions;

    public TransactionRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getDatabase(application);

        transactionDao = db.transactionDao();
    }

    // TODO: Insert Async
    public void insert(Transaction transaction) {
        TransactionDatabase.databaseWriteExecutor.execute(() -> transactionDao.insert(transaction));
    }

    public int getTotalByCategory(String category){
        return transactionDao.getTotalByCategory(category);
    }

    public List<String> getAllCurrentActiveCategory(){
        return transactionDao.getAllCurrentActiveCategory();
    }


    public LiveData<List<Transaction>> getAllTransactions() {
        allTransactions = transactionDao.getAllTransactions();
        return allTransactions;
    }

    public void deleteAll() {
        new deleteAllTransactionsAsyncTask(transactionDao).execute();
    }

    public void delete(Transaction transaction) {
        new deleteTransactionAsyncTask(transactionDao).execute(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactionsByDateCreated(String date) {
        allTransactions = transactionDao.getAllTransactionsByDateCreated(date);
        return allTransactions;
    }

    public int getTotalByType(String type){
        int total = transactionDao.getTotalByType(type);
        return total;
    }

    // to do the delete asynchronous
    private static class deleteAllTransactionsAsyncTask extends AsyncTask<Void, Void, Void> {

        private TransactionDao transactionDao;

        deleteAllTransactionsAsyncTask(TransactionDao dao) {
            this.transactionDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDao.deleteAll();
            return null;
        }
    }

    private static class deleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {

        private TransactionDao transactionDao;

        deleteTransactionAsyncTask(TransactionDao dao) {
            this.transactionDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            transactionDao.delete(params[0]);
            return null;
        }
    }

}


