package com.example.moneylover2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "transaction_table")
public class Transaction implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "transactionId")
    public int transactionId;

    @ColumnInfo(name = "category")
    public String Category;

    @ColumnInfo(name = "amount")
    public int Amount;

    public Transaction(){

    }

    public Transaction(String category, int amount) {
        Category = category;
        Amount = amount;
    }

    @NonNull
    @Override
    public String toString() {
        return Category + "\t" + Amount;
    }
}
