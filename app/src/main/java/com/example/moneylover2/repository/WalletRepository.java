package com.example.moneylover2.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.moneylover2.dao.WalletDao;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Wallet;

import java.util.List;

public class WalletRepository {
    private WalletDao walletDao;

    public WalletRepository(Application application) {
        TransactionDatabase db = TransactionDatabase.getDatabase(application);

        walletDao = db.walletDao();
    }

    public void insert(Wallet wallet) {
        walletDao.insert(wallet);
    }

    public void update(Wallet wallet) {
        walletDao.update(wallet);
    }

    public LiveData<List<Wallet>> getAllWallets() {
        return walletDao.getAllWallets();
    }



}
