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

import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.TransactionViewHolder> {

    private final LayoutInflater mInflater;
    private List<Transaction> transactionList; // Cached copy of words

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
            holder.textView_category.setText(current.Category);
            holder.textView_amount.setText(current.Amount + "");
        } else {
            // Covers the case of data not being ready yet.
            holder.textView_amount.setText("No amount");
            holder.textView_category.setText("No category");
        }
    }

    public void setTransactions(List<Transaction> transactions) {
        transactionList = transactions;
        notifyDataSetChanged();
    }

    public Transaction getTransactionAtPosition(int position){
        return transactionList.get(position);
    }

    @Override
    public int getItemCount() {
        if (transactionList != null)
            return transactionList.size();
        else return 0;
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

        private final TextView textView_amount, textView_category;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_amount = itemView.findViewById(R.id.textView_amount);
            textView_category = itemView.findViewById(R.id.textView_category);
        }
    }

}
