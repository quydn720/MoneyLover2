package com.example.moneylover2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moneylover2.R;
import com.example.moneylover2.model.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {

    private final LayoutInflater mInflater;
    private List<Category> allCategories; // Cached copy of words

    public CategoryListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView categoryItemView;

        private CategoryViewHolder(View itemView) {
            super(itemView);
            categoryItemView = itemView.findViewById(R.id.textView_category_title);
        }

    }

    public void setAllCategories(List<Category> categories) {
        allCategories = categories;
        notifyDataSetChanged();
    }

    public Category getCategoryAtPosition(int position){
        return allCategories.get(position);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_category, parent, false);
        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        if (allCategories != null) {
            Category current = allCategories.get(position);
            holder.categoryItemView.setText(current.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (allCategories != null)
            return allCategories.size();
        else return 0;
    }
}
