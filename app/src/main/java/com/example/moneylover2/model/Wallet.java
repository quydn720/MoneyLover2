package com.example.moneylover2.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wallet_table")
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "walletId")
    public int walletId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "balance")
    public int balance;


    public Wallet(String name, int balance) {
        this.name = name;
        this.balance = balance;
    }


    public int getWalletId() {
        return walletId;
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }
}

