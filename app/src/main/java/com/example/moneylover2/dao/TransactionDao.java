package com.example.moneylover2.dao;

import androidx.lifecycle.LiveData;
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

    @Query("SELECT * from transaction_table ORDER BY transactionId DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("Select * from transaction_table where date_created = :date order by transactionId desc")
    LiveData<List<Transaction>> getAllTransactionsByDateCreated(String date);


//    @Query("Select * from transaction_table where date_created >= :date1 and date_created <= :date2")
//    LiveData<List<Transaction>> getAllTransactionsBetweenTwoDate(String date1, String date2);


    //region For testing
    @Query("SELECT * from transaction_table order by date_created")
    List<Transaction> getAllTransactions_Test();
    @Query("Select * from transaction_table where date_created = :date")
    List<Transaction> getAllTransactionsByDateCreated_Test(String date);

    //endregion
}
