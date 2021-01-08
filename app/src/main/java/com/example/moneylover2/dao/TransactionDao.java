package com.example.moneylover2.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.moneylover2.model.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {
    @Insert
    void insert(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM transaction_table")
    void deleteAll();

    @Query("SELECT * from transaction_table")
    List<Transaction> getAllTransactions();
}
