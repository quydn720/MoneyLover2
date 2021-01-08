package com.example.moneylover2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.moneylover2.dao.TransactionDao;
import com.example.moneylover2.model.Transaction;

@Database(entities = {Transaction.class}, version = 1, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {
    public abstract TransactionDao transactionDao();
}
