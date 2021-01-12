package com.example.moneylover2.ui;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.moneylover2.R;
import com.example.moneylover2.model.Category;
import com.example.moneylover2.model.Transaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.moneylover2.ui.MainActivity.ALL_CATEGORY_NAME;

public class NewTransactionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener{

    public static final String NEW_TRANSACTION = "com.example.android.moneylover.NEW_TRANSACTION";

    private EditText editText_amount;
    private TextView textView_dateCreated;
    private Spinner spinner_category;
    private List<Category> allCategoryName;

    private String category;
    private int amount;
    private String dateToDisplay;
    private String dateToDbSave;
    DateFormat df = new SimpleDateFormat("ddMMyyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_transaction);

        //region Initialize, Populate the allCategoryName for Spinner
        List<String> categoryNameList = new ArrayList<>();

        // Get all the category name from the intent
        Intent allCategoriesIntent = getIntent();
        allCategoryName = (List<Category>) allCategoriesIntent.getSerializableExtra(ALL_CATEGORY_NAME);

        for (Category category : allCategoryName) {
            categoryNameList.add(category.Name);
        }

        //endregion

        editText_amount = findViewById(R.id.editText_amount);
        textView_dateCreated = findViewById(R.id.textView_dateCreated);

        // Nếu user không chọn ngày khác
        Date d = Calendar.getInstance().getTime();
        dateToDisplay = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        dateToDbSave = dateToDisplay;
        dateToDbSave = df.format(d);
        textView_dateCreated.setText(dateToDisplay);

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
            Transaction transaction = new Transaction(category, amount, dateToDbSave);
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

    //region Event - Show the DatePickerDiaglog
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
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


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date d = c.getTime();

        dateToDisplay = DateFormat.getDateInstance(DateFormat.FULL).format(d);
        textView_dateCreated.setText(dateToDisplay);

        dateToDbSave = df.format(d);
    }

    public static class DatePickerFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), (DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        }
    }
}