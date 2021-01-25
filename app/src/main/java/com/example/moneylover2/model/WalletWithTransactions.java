package com.example.moneylover2.model;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class WalletWithTransactions {
    // TODO : add relationship between table (Room)

    @Embedded
    public Wallet wallet;
    @Relation(
            parentColumn = "walletId",
            entityColumn = "wallet_id"
    )
    public List<Transaction> transactionList;

}
