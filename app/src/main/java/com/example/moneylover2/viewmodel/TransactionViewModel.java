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



//    public void deleteAll() {
//        Repository.deleteAll();
//    }

//    public void deleteWord(Transaction word) {
//        mRepository.deleteWord(word);
//    }
//
//    public void update(Transaction word) {
//        mRepository.update(word);
//    }
}
