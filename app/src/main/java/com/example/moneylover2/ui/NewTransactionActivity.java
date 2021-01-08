package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneylover2.R;
import com.example.moneylover2.model.Transaction;

public class NewTransactionActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.moneylover.REPLY";

    private EditText editText_amount, editText_category;
    private Button button_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        button_save = findViewById(R.id.button_save);
        editText_amount = findViewById(R.id.editText_amount);
        editText_category = findViewById(R.id.editText_category);
    }

    public void returnTransaction(View view) {
        Transaction transaction = new Transaction(editText_category.getText().toString(), Integer.parseInt(editText_amount.getText().toString()));
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, transaction);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}