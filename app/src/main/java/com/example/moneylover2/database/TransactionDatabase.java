package com.example.moneylover2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.moneylover2.dao.CategoryDao;
import com.example.moneylover2.dao.TransactionDao;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class, Category.class},
        version = 2, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    //  Singleton
    private static TransactionDatabase INSTANCE;

    // UI-crash avoid
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TransactionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TransactionDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TransactionDatabase.class, "moneylover_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
//                            .fallbackToDestructiveMigration()
//                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
