package com.example.moneylover2.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.moneylover2.dao.CategoryDao;
import com.example.moneylover2.dao.TransactionDao;
import com.example.moneylover2.dao.WalletDao;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.model.Wallet;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Transaction.class, Category.class, Wallet.class},
        version = 3, exportSchema = false)
public abstract class TransactionDatabase extends RoomDatabase {

    public abstract TransactionDao transactionDao();

    public abstract CategoryDao categoryDao();

    public abstract WalletDao walletDao();

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
                            .allowMainThreadQueries()
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final CategoryDao categoryDao;
        private final WalletDao walletDao;
        String[] defaultCategoryName = {"Family", "Food & Beverages", "Health & Fitness", "Entertainment", "Others", "Education", "Shopping"};

        PopulateDbAsync(TransactionDatabase db) {
            categoryDao = db.categoryDao();
            walletDao = db.walletDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            //dao.deleteAll();
            if (walletDao.getAnyWallet().length < 1) {
                walletDao.insert(new Wallet("Main", 0));
            }

            if (categoryDao.getAnyCategory().length < 1) {
                for (String s : defaultCategoryName) {
                    Category category = new Category(s);
                    categoryDao.insert(category);
                }
            }
            return null;
        }
    }


}
