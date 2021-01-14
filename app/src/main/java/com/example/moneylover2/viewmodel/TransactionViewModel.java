package com.example.moneylover2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.repository.TransactionRepository;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private TransactionRepository Repository;
    private LiveData<List<Transaction>> allTransactions;


    public TransactionViewModel(Application application) {
        super(application);
        Repository = new TransactionRepository(application);
    }

    public LiveData<List<Transaction>> getAllTransactions() {
        allTransactions = Repository.getAllTransactions();
        return allTransactions;
    }

    public void insert(Transaction word) {
        Repository.insert(word);
    }

    public int getTotalByType(String type){
        return Repository.getTotalByType(type);
    }


    public void deleteAll() {
        Repository.deleteAll();
    }

    public void deleteTransaction(Transaction transaction) {
        Repository.delete(transaction);
    }

    public LiveData<List<Transaction>> getAllTransactionsByDateCreated(String date) {
        allTransactions = Repository.getAllTransactionsByDateCreated(date);
        return allTransactions;
    }

//    public void update(Transaction word) {
//        mRepository.update(word);
//    }
}
