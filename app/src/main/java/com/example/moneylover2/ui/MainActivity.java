package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.adapter.TransactionListAdapter;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.viewmodel.CategoryViewModel;
import com.example.moneylover2.viewmodel.TransactionViewModel;

import java.io.Serializable;
import java.util.List;

import static com.example.moneylover2.ui.NewTransactionActivity.NEW_TRANSACTION;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TRANSACTION_ACTIVITY_REQUEST_CODE = 1;
    public static final String ALL_CATEGORY_NAME = "com.example.android.moneylover.extra.ALL_CATEGORIES_NAME";

    private TextView textView;
    private TransactionViewModel transactionViewModel;
    private CategoryViewModel categoryViewModel;

    private List<Category> categoryNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);


        // Initialize, Populate RecyclerView of Transactions
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TransactionListAdapter adapter = new TransactionListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //region Event - Swipe left to delete
        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Transaction transaction = adapter.getTransactionAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting transaction...", Toast.LENGTH_SHORT).show();

                        transactionViewModel.deleteTransaction(transaction);
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
        //endregion

        // Update the cached copy of the words in the adapter.
        transactionViewModel.getAllTransactions().observe(this, adapter::setTransactions);

    }

    public void addNewTransaction(View view) {
        Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
        categoryNameList = categoryViewModel.getAllCategoryName();
        intent.putExtra(ALL_CATEGORY_NAME, (Serializable) categoryNameList);
        startActivityForResult(intent, NEW_TRANSACTION_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings)
            return true;

        if (id == R.id.action_category) {
            Intent intent = new Intent(MainActivity.this, NewCategoryActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_deleteAll_test) {
            Toast.makeText(this, "Delete all transactions", Toast.LENGTH_SHORT).show();
            transactionViewModel.deleteAll();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TRANSACTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Transaction transaction = (Transaction) data.getSerializableExtra(NEW_TRANSACTION);
            transactionViewModel.insert((transaction));
        }
    }

}
