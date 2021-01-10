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

    public void insert(Transaction transaction) {
        TransactionDatabase.databaseWriteExecutor.execute(() -> transactionDao.insert(transaction));
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        allTransactions = transactionDao.getAllTransactions();
        return allTransactions;
    }

    public void deleteAll()  {
        new deleteAllTransactionsAsyncTask(transactionDao).execute();
    }

    public void delete(Transaction transaction){
        new deleteTransactionAsyncTask(transactionDao).execute(transaction);
    }


    // to do the delete asynchronous
    private static class deleteAllTransactionsAsyncTask extends AsyncTask<Void, Void, Void>{

        private TransactionDao transactionDao;

        deleteAllTransactionsAsyncTask(TransactionDao dao){
            this.transactionDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDao.deleteAll();
            return null;
        }
    }

    private static class deleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void>{

        private TransactionDao transactionDao;

        deleteTransactionAsyncTask(TransactionDao dao){
            this.transactionDao = dao;
        }

        @Override
        protected Void doInBackground(final Transaction... params) {
            transactionDao.delete(params[0]);
            return null;
        }
    }

}


