package com.example.moneylover2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moneylover2.model.Wallet;
import com.example.moneylover2.repository.WalletRepository;

import java.util.List;

public class WalletViewModel extends AndroidViewModel {
    private WalletRepository Repository;
    private LiveData<List<Wallet>> allWallets;


    public WalletViewModel(Application application) {
        super(application);
        Repository = new WalletRepository(application);
    }

    public void insert(Wallet wallet) {
        Repository.insert(wallet);
    }

    public void update(Wallet wallet) {
        Repository.update(wallet);
    }

    public LiveData<List<Wallet>> getAllWallets() {
        return Repository.getAllWallets();
    }

}
