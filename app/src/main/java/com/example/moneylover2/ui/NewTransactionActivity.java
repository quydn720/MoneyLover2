package com.example.moneylover2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.moneylover2.R;
import com.example.moneylover2.model.Transaction;
import com.example.moneylover2.viewmodel.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class NewTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String EXTRA_TRANSACTION = "com.example.android.moneylover.NEW_TRANSACTION";

    private EditText editText_amount;
    private Spinner spinner_category;

    private String category;
    private int amount;

    private CategoryViewModel categoryViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);

        editText_amount = findViewById(R.id.editText_amount);

        // Create the spinner.
        spinner_category = findViewById(R.id.spinner_category);
        if (spinner_category != null) {
            spinner_category.setOnItemSelectedListener(this);
        }
        // Test. TODO: lấy categoryList từ Database.
        List<String> categoryList = new ArrayList<>();
        //categoryViewModel.getAllCategoryName();

        categoryList.add("Family");
        categoryList.add("Coffee");
        categoryList.add("Health");
        categoryList.add("Shopping");

        // Create an ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categoryList);
        // Apply the adapter to the spinner.
        spinner_category.setAdapter(arrayAdapter);

    }

    public void returnTransaction(View view) {

        // TODO: Viết Hàm format string để hiển thị tiền tệ khi nhập....vd: 1000 => 1,000
        try {
            amount = Integer.parseInt(editText_amount.getText().toString());

            Transaction transaction = new Transaction(category, amount);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_TRANSACTION, transaction);
            setResult(RESULT_OK, replyIntent);
            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "You must enter the amount", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}