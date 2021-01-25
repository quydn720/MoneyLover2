//package com.example.moneylover2;
//
//import android.content.Context;
//
//import androidx.room.Room;
//import androidx.test.core.app.ApplicationProvider;
//import androidx.test.runner.AndroidJUnit4;
//
//import com.example.moneylover2.dao.TransactionDao;
//import com.example.moneylover2.database.TransactionDatabase;
//import com.example.moneylover2.model.Transaction;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.hamcrest.Matchers.equalTo;
//import static org.junit.Assert.assertThat;
//
//@RunWith(AndroidJUnit4.class)
//public class DatabaseTest {
//    private TransactionDao transactionDao;
//    private TransactionDatabase db;
//
//    Transaction transaction1 = new Transaction("Coffee", "outcome", 1000, "15012021");
//    Transaction transaction2 = new Transaction("Family", "outcome", 2000, "16012021");
//    Transaction transaction3 = new Transaction("Salary", "income", 5000, "15012021");
//    Transaction transaction4 = new Transaction("Coffee", "outcome", 2000, "15012021");
//
//    @Before
//    public void createDb() {
//        Context context = ApplicationProvider.getApplicationContext();
//        db = Room.inMemoryDatabaseBuilder(context, TransactionDatabase.class).build();
//        transactionDao = db.transactionDao();
//    }
//
//    @After
//    public void closeDb() throws IOException {
//        db.close();
//    }
//
//    @Test
//    public void insertTransactionSuccessfully() throws Exception {
//
//        transactionDao.insert(transaction1);
//        transactionDao.insert(transaction2);
//        transactionDao.insert(transaction3);
//
//        List<Transaction> trans1 = transactionDao.getAllTransactions_Test();
//        assertThat(trans1.get(0).DateCreated, equalTo(transaction1.DateCreated));
//    }
//
//    @Test
//    public void queryTransactionsByDate() throws Exception {
//        List<Transaction> trans12 = new ArrayList<>();
//
//        trans12.add(transaction1);
//        trans12.add(transaction3);
//        trans12.add(transaction4);
//
//
//        transactionDao.insert(transaction1);
//        transactionDao.insert(transaction4);
//        transactionDao.insert(transaction3);
//
//
//        List<Transaction> trans_by_date = transactionDao.getAllTransactionsByDateCreated_Test("15012021");
//        assertThat(trans_by_date.size(), equalTo(trans12.size()));
//    }
//
//    @Test
//    public void queryTotalByCategory() throws Exception {
//        List<String> categories = new ArrayList<>();
//
//        categories.add("Coffee");
//        categories.add("Family");
//
//        transactionDao.insert(transaction1);
//        transactionDao.insert(transaction2);
//        transactionDao.insert(transaction3);
//        transactionDao.insert(transaction4);
//
//        List<TransactionDao.AmountCategory> total = transactionDao.getTotalByCategory();
//        assertThat(total.size(), equalTo(3));
//    }
//
//    @Test
//    public void queryTotalByCategory() throws Exception {
//        List<String> categories = new ArrayList<>();
//
//        categories.add("Coffee");
//        categories.add("Family");
//        categories.add("Coffee");
//
//        transactionDao.insert(transaction1);
//        transactionDao.insert(transaction2);
//        transactionDao.insert(transaction3);
//        transactionDao.insert(transaction4);
//
//        List<TransactionDao.AmountCategory> total = transactionDao.getTotalByCategory();
//        assertThat(total.size(), equalTo(3));
//    }
//
//
//}
