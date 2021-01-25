package com.example.moneylover2.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.moneylover2.model.Wallet;

import java.util.List;

@Dao
public interface WalletDao {
    @Insert
    void insert(Wallet wallet);

    @Update
    void update(Wallet wallet);

    @Query("Select * from wallet_table limit 1")
    Wallet[] getAnyWallet();

    @Query("Select * from wallet_table")
    LiveData<List<Wallet>> getAllWallets();

}
