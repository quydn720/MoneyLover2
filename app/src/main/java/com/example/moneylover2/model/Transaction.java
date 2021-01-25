package com.example.moneylover2.model;

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
    @ColumnInfo(name = "type")
    public String Type;

    @ColumnInfo(name = "date_created")
    public String DateCreated;

    public Transaction(){

    }

    public Transaction(String category, String type, int amount, String dateCreated) {
        Category = category;
        Type = type;
        Amount = amount;
        DateCreated = dateCreated;
    }
}
