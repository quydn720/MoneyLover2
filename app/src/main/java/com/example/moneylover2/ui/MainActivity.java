package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.example.moneylover2.R;
import com.example.moneylover2.database.TransactionDatabase;
import com.example.moneylover2.model.Transaction;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.moneylover2.ui.NewTransactionActivity.EXTRA_REPLY;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_TRANSACTION_ACTIVITY_REQUEST_CODE = 1;
    private TextView textView;
    private TransactionDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);
        FloatingActionButton fab = findViewById(R.id.fab);

        db = Room.databaseBuilder(getApplicationContext(),
                TransactionDatabase.class, "transaction_db").build();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TRANSACTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            Transaction transaction1 = (Transaction) data.getSerializableExtra(EXTRA_REPLY);
            String transaction_string = transaction1.toString();
            textView.setText(transaction_string);

        }
    }
}
