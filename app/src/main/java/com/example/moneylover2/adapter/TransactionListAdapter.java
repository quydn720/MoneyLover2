package com.example.moneylover2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.model.Transaction;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {

    private final LayoutInflater mInflater;
    private List<Transaction> transactionList; // Cached copy of words
    private static final Locale l = new Locale("vi", "VN");

    public TransactionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_transaction, parent, false);
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        if (transactionList != null) {
            Transaction current = transactionList.get(position);
            boolean isIncome = current.Type.equals("income");

            holder.textView_category.setText(current.Category);
            holder.textView_amount.setText(CurrencyFormat(current.Amount, isIncome));
            holder.textView_date.setText(StringToDateFormat(current.DateCreated));

        }
    }

    public void setTransactions(List<Transaction> transactions) {
        transactionList = transactions;
        notifyDataSetChanged();
    }

    public Transaction getTransactionAtPosition(int position) {
        return transactionList.get(position);
    }

    @Override
    public int getItemCount() {
        if (transactionList != null)
            return transactionList.size();
        else return 0;
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_amount, textView_category, textView_date;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_amount = itemView.findViewById(R.id.textView_amount);
            textView_category = itemView.findViewById(R.id.textView_category);
            textView_date = itemView.findViewById(R.id.textView_date);
        }
    }


    // Helper method
    public static String StringToDateFormat(String s) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_YEAR, Integer.parseInt(s.substring(0, 2)));
        c.set(Calendar.MONTH, Integer.parseInt(s.substring(2, 4)));
        c.set(Calendar.YEAR, Integer.parseInt(s.substring(4, 8)));

        return DateFormat.getDateInstance(DateFormat.FULL, l).format(c.getTime());
    }

    // Tham số Locale cho phép người dùng có thể thay đổi đơn vị tiền tệ
    public static String CurrencyFormat(int i, boolean isIncome) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(l);
        String currencyFormatted = numberFormat.format(i);

        if (isIncome) currencyFormatted = "+" + currencyFormatted;
        else currencyFormatted = "-" + currencyFormatted;

        return currencyFormatted;
    }

    public static String CurrencyFormat(int i) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(l);

        return numberFormat.format(i);
    }


}
