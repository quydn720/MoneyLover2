package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.model.Wallet;
import com.example.moneylover2.viewmodel.TransactionViewModel;
import com.example.moneylover2.viewmodel.WalletViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.moneylover2.adapter.TransactionListAdapter.CurrencyFormat;
import static com.example.moneylover2.ui.NewTransactionActivity.NEW_TRANSACTION;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TRANSACTION_ACTIVITY_REQUEST_CODE = 1;
    public static final DateFormat df = new SimpleDateFormat("ddMMyyyy");

    private TextView textView_total;
    private TextView textView_current_date;
    private TransactionViewModel transactionViewModel;

    private Calendar c = Calendar.getInstance();
    private Date d = c.getTime();
    private String dateToDbQuery = df.format(d);
    private TransactionListAdapter adapter;
    private Locale l;
    private List<Wallet> allWallets = new ArrayList<>();
    private Wallet wallet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView_total = findViewById(R.id.textView_total);

        WalletViewModel walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
        walletViewModel.getAllWallets().observe(this, wallets -> {
            allWallets = wallets;
            wallet = allWallets.get(0);
            textView_total.setText(CurrencyFormat(wallet.getBalance()));
        });

        l = new Locale("vi", "VN");


        transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        textView_current_date = findViewById(R.id.textView_current_date);

        // TODO : create a helper/util class
        textView_current_date.setText(DateFormat.getDateInstance(DateFormat.FULL, l).format(d));
        // Event - change the date
        textView_current_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                transactionViewModel.getAllTransactionsByDateCreated(dateToDbQuery).observe(MainActivity.this, adapter::setTransactions);
            }
        });


        // Initialize, Populate RecyclerView of Transactions
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapter = new TransactionListAdapter(this);
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

                        int sign = transaction.Type.equals("income") ? -1 : 1;
                        wallet.balance += transaction.Amount * sign;
                        walletViewModel.update(wallet);

                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);
        //endregion

        // Update the cached copy of the words in the adapter.
        transactionViewModel.getAllTransactionsByDateCreated(dateToDbQuery).observe(this, adapter::setTransactions);
    }

    public void addNewTransaction(View view) {

        Intent intent = new Intent(MainActivity.this, NewTransactionActivity.class);
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

        if (id == R.id.action_report){
            Intent intent = new Intent(MainActivity.this, ShowReportActivity.class);
            startActivity(intent);
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

            WalletViewModel walletViewModel = ViewModelProviders.of(this).get(WalletViewModel.class);
            int sign = transaction.Type.equals("income") ? 1 : -1;
            wallet.balance += transaction.Amount * sign;
            walletViewModel.update(wallet);
        }
    }

    //region Event - change the date
    public void showPreviousDay(View view) {
        c.add(Calendar.DATE, -1);
        dateToDbQuery = df.format(c.getTime());
        textView_current_date.setText(DateFormat.getDateInstance(DateFormat.FULL, l).format(c.getTime()));
    }
    public void showNextDay(View view) {
        c.add(Calendar.DATE, 1);
        dateToDbQuery = df.format(c.getTime());
        textView_current_date.setText(DateFormat.getDateInstance(DateFormat.FULL, l).format(c.getTime()));
    }
    //endregion

}
