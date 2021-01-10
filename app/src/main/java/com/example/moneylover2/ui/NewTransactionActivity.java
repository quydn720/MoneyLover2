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

import com.example.moneylover2.R;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.moneylover2.ui.MainActivity.ALL_CATEGORY_NAME;

public class NewTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final String NEW_TRANSACTION = "com.example.android.moneylover.NEW_TRANSACTION";

    private EditText editText_amount;
    private Spinner spinner_category;
    private List<Category> allCategoryName;
    private String category;
    private int amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        //region Initialize, Populate the allCategoryName for Spinner
        List<String> categoryNameList = new ArrayList<>();

        // Populate with the default - resource
        String[] defaultCategoryName = getResources().getStringArray(R.array.default_categories_list);
        categoryNameList.addAll(Arrays.asList(defaultCategoryName));

        // Get all the category name from the intent
        Intent allCategoriesIntent = getIntent();
        allCategoryName = (List<Category>) allCategoriesIntent.getSerializableExtra(ALL_CATEGORY_NAME);
        for (Category category : allCategoryName) {
            categoryNameList.add(category.Name);
        }
        //endregion

        editText_amount = findViewById(R.id.editText_amount);

        //region Initialize the Spinner, populate it with (allCategoryName) variable

        // Create the spinner.
        spinner_category = findViewById(R.id.spinner_category);
        if (spinner_category != null) {
            spinner_category.setOnItemSelectedListener(this);
        }

        // Create an ArrayAdapter using the string array and default spinner layout.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, categoryNameList);

        // Apply the adapter to the spinner.
        spinner_category.setAdapter(arrayAdapter);
        //endregion

    }

    //region Event - Add a new Transaction
    public void returnTransaction(View view) {

        // TODO: Viết Hàm format string để hiển thị tiền tệ khi nhập....vd: 1000 => 1,000
        try {
            amount = Integer.parseInt(editText_amount.getText().toString());

            Transaction transaction = new Transaction(category, amount);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(NEW_TRANSACTION, transaction);
            setResult(RESULT_OK, replyIntent);
            finish();
        }

        // Người dùng bỏ trống ô Amount
        catch (NumberFormatException e) {
            Toast.makeText(this, "You must enter the amount", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    //region Event - Select the item on the Spinner
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        category = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    //endregion
}