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
import java.util.ArrayList;
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
    public void insertTransactionSuccessfully() throws Exception {
        String a = "Quy"; int b = 9000; String c = "12012021";
        Transaction transaction = new Transaction(a, b, c);

        String a1 = "Quy"; int b1 = 9000; String c1 = "12012021";
        Transaction transaction1 = new Transaction(a1, b1, c1);
        String a2 = "Quy"; int b2 = 9000; String c2 = "13012021"; // 13-01-2021
        Transaction transaction2 = new Transaction(a2, b2, c2);

        transactionDao.insert(transaction);
        transactionDao.insert(transaction1);
        transactionDao.insert(transaction2);

        List<Transaction> trans1 = transactionDao.getAllTransactions_Test();
        assertThat(trans1.get(0).DateCreated, equalTo(transaction.DateCreated));
    }

    @Test
    public void queryTransactionsByDate() throws Exception {
        List<Transaction> trans12 = new ArrayList<>();
        String a = "Quy"; int b = 9000; String c = "12012021";
        Transaction transaction = new Transaction(a, b, c);

        String a1 = "Quy"; int b1 = 9000; String c1 = "12012021";
        Transaction transaction1 = new Transaction(a1, b1, c1);

        trans12.add(transaction);
        trans12.add(transaction1);

        String a2 = "Quy"; int b2 = 9000; String c2 = "13012021"; // 13-01-2021
        Transaction transaction2 = new Transaction(a2, b2, c2);

        transactionDao.insert(transaction);
        transactionDao.insert(transaction1);
        transactionDao.insert(transaction2);


        List<Transaction> trans_by_date = transactionDao.getAllTransactionsByDateCreated_Test("12012021");
        assertThat(trans_by_date.size(), equalTo(trans12.size()));
    }
}
