package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UserReportPaymentSummaryViewHolder extends RecyclerView.ViewHolder {
    LineChart lineChart;
    TextView dailyIncomeTextView;
    TextView totalIncomeTextView;

    public UserReportPaymentSummaryViewHolder(@NonNull View itemView) {
        super(itemView);
        lineChart = itemView.findViewById(R.id.paymentSummaryLineChart);
        dailyIncomeTextView = itemView.findViewById(R.id.paymentSummaryLeftText);
        totalIncomeTextView = itemView.findViewById(R.id.paymentSummaryRightText);
    }

    public void setIncome(List<Double> paymentAmounts, List<Date> paymentDates) {
        setLineChart(paymentAmounts, paymentDates);
        setDailyIncomeTextView(paymentAmounts, paymentDates);
        setTotalIncomeTextView(paymentAmounts);
    }

    private void setLineChart(List<Double> paymentAmounts, List<Date> paymentDates) {
        List<Entry> entries = new ArrayList<>();
        float cumulativeSum = 0.0f;

        for (int i = 0; i < paymentAmounts.size(); i++) {
            long xValue = paymentDates.get(i).getTime();
            cumulativeSum += paymentAmounts.get(i).floatValue();
            entries.add(new Entry(xValue, cumulativeSum));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Cumulative Payment");
        LineData lineData = new LineData(dataSet);

        lineChart.setData(lineData);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd", Locale.getDefault());
        xAxis.setValueFormatter(new XAxisValueFormatter(dateFormat));
        lineChart.getDescription().setEnabled(false);
        lineChart.getAxisRight().setEnabled(false);

        lineChart.invalidate();
    }

    private static class XAxisValueFormatter extends ValueFormatter {
        private final SimpleDateFormat dateFormat;

        XAxisValueFormatter(SimpleDateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override
        public String getAxisLabel(float value, AxisBase axis) {
            return dateFormat.format(new Date((long) value));
        }
    }

    private void setDailyIncomeTextView(List<Double> paymentAmounts, List<Date> paymentDates) {
        Calendar startOfDay = Calendar.getInstance();
        startOfDay.set(Calendar.HOUR_OF_DAY, 0);
        startOfDay.set(Calendar.MINUTE, 0);
        startOfDay.set(Calendar.SECOND, 0);
        startOfDay.set(Calendar.MILLISECOND, 0);

        Date startOfDayDate = startOfDay.getTime();
        double cumulativeDailyIncome = 0.0;

        for (int i = paymentAmounts.size() - 1; i >= 0; i--) {
            if (paymentDates.get(i).before(startOfDayDate)) {
                break;
            }
            cumulativeDailyIncome += paymentAmounts.get(i);
        }

        dailyIncomeTextView.setText(String.format(Locale.getDefault(), "Today's Income%n$%.2f", cumulativeDailyIncome));
    }

    private void setTotalIncomeTextView(List<Double> paymentAmounts) {
        double cumulativeTotalIncome = 0.0;

        for (Double payment : paymentAmounts) {
            cumulativeTotalIncome += payment;
        }

        totalIncomeTextView.setText(String.format(Locale.getDefault(), "Total Income%n$%.2f", cumulativeTotalIncome));
    }

}
