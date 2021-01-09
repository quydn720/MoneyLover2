package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.adapter.TransactionListAdapter;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.viewmodel.TransactionViewModel;

import java.io.Serializable;
import java.util.List;

import static com.example.moneylover2.ui.NewCategoryActivity.ALL_CATEGORIES;
import static com.example.moneylover2.ui.NewTransactionActivity.EXTRA_TRANSACTION;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TRANSACTION_ACTIVITY_REQUEST_CODE = 1;
    private static final int ALL_CATEGORIES_ACTIVITY_REQUEST_CODE = 2;
    public static final String ALL_CATEGORIES_TITLE =  "com.example.android.moneylover.extra.ALL_CATEGORIES";

    private TextView textView;
    private TransactionViewModel transactionViewModel;
    private List<Category> categoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);

        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TransactionListAdapter adapter = new TransactionListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Update the cached copy of the words in the adapter.
        transactionViewModel.getAllTransactions().observe(this, adapter::setTransactions);
    }

    public void addNewTransaction(View view) {
        Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
        intent.putExtra(ALL_CATEGORIES_TITLE, (Serializable) categoryList);
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_category:
                Intent intent = new Intent(MainActivity.this, NewCategoryActivity.class);
                startActivityForResult(intent, ALL_CATEGORIES_ACTIVITY_REQUEST_CODE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TRANSACTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Transaction transaction = (Transaction) data.getSerializableExtra(EXTRA_TRANSACTION);
            transactionViewModel.insert((transaction));
        }
        else if (requestCode == ALL_CATEGORIES_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

             categoryList = (List<Category>) data.getSerializableExtra(ALL_CATEGORIES);
        }
    }
}
