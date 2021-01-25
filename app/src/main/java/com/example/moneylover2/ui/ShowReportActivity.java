package com.example.moneylover2.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.moneylover2.R;
import com.example.moneylover2.viewmodel.TransactionViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowReportActivity extends AppCompatActivity {

    private PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_report);

        setPieChartInOut();
        setPieChartCategory();
    }

    public void setPieChartInOut() {

        TransactionViewModel transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        int income = transactionViewModel.getTotalByType("income");
        int outcome = transactionViewModel.getTotalByType("outcome");
        int[] chartNumber = {income, outcome};

        this.pieChart = findViewById(R.id.pieChartByInOut);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        yValues.add(new PieEntry(chartNumber[0],"Income"));
        yValues.add(new PieEntry(chartNumber[1],"Outcome"));

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }

    public void setPieChartCategory() {

        TransactionViewModel transactionViewModel = ViewModelProviders.of(this).get(TransactionViewModel.class);

        this.pieChart = findViewById(R.id.pieChartByCategory);
        pieChart.setDragDecelerationFrictionCoef(0.9f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);
        ArrayList<PieEntry> yValues = new ArrayList<>();


        List<String> categoriesName = transactionViewModel.getAllCurrentActiveCategory_Test();
        for(String categoryName : categoriesName){
            yValues.add(new PieEntry(transactionViewModel.getTotalByCategory(categoryName), categoryName));
        }

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData pieData = new PieData((dataSet));
        pieData.setValueTextSize(15f);
        pieData.setValueTextColor(Color.YELLOW);
        pieChart.setData(pieData);
        //PieChart Ends Here
    }
}