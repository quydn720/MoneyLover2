package com.example.moneylover2;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.example.moneylover2.dao.TransactionDao;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Transaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private TransactionDao transactionDao;
    private TransactionDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase.class).build();
        transactionDao = db.transactionDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void insertSuccessfully() throws Exception {
        String a = "Quy"; int b = 9000;
        Transaction transaction = new Transaction(a, b);
        transaction.id = 1;
        transactionDao.insert(transaction);
        List<Transaction> trans1 = transactionDao.getAllTransactions();
        assertThat(trans1.get(0).Category, equalTo(transaction.Category));
    }
}
