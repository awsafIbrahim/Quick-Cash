package com.example.quickcash.ui_elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

import java.util.Date;
import java.util.List;

public class EmployeeVisualizeJobHistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int VIEW_TYPE_RATING = 1;
    private static int VIEW_TYPE_PAYMENT_SUMMARY = 2;
    private static int VIEW_TYPE_PAYMENT_ITEM = 3;
    double rating;
    List<Double> paymentAmounts;
    List<Date> paymentDates;

    public EmployeeVisualizeJobHistoryAdapter(double rating, List<Double> paymentAmounts, List<Date> paymentDates) {
        this.rating = rating;
        this.paymentAmounts = paymentAmounts;
        this.paymentDates = paymentDates;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_RATING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item_layout, parent, false);
            return new UserReportRatingViewHolder(view);
        } else if (viewType == VIEW_TYPE_PAYMENT_SUMMARY) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_summary_layout, parent, false);
            return new UserReportPaymentSummaryViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item_layout, parent, false);
            return new UserReportPaymentItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_RATING) {
            ((UserReportRatingViewHolder) holder).setUserRating(rating);
        } else if (getItemViewType(position) == VIEW_TYPE_PAYMENT_SUMMARY) {
            ((UserReportPaymentSummaryViewHolder) holder).setIncome(paymentAmounts, paymentDates);
        } else {
            int adjustedPosition = position - 2;
            ((UserReportPaymentItemViewHolder) holder).setPaymentDetails(paymentAmounts.get(adjustedPosition), paymentDates.get(adjustedPosition));
        }
    }

    @Override
    public int getItemCount() {
        return paymentAmounts.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_RATING;
        } else if (position == 1) {
            return VIEW_TYPE_PAYMENT_SUMMARY;
        } else {
            return VIEW_TYPE_PAYMENT_ITEM;
        }
    }

}
