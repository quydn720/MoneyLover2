package com.example.moneylover2.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "category_table")
public class Category implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "categoryId")
    public int categoryId;

    @ColumnInfo(name = "name")
    public String Name;

    public Category() {

    }

    public Category(@NonNull String name) {
        this.Name = name;
    }

    public String getName() {
        return this.Name;
    }
}
