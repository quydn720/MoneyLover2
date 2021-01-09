package com.example.moneylover2.repository;

import android.app.Application;

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


}


