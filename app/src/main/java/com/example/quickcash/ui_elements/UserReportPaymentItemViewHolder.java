package com.example.quickcash.ui_elements;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserReportPaymentItemViewHolder extends RecyclerView.ViewHolder {
    private TextView paymentItemTextView;

    public UserReportPaymentItemViewHolder(@NonNull View itemView) {
        super(itemView);
        paymentItemTextView = itemView.findViewById(R.id.paymentTextView);
    }

    public void setPaymentDetails(double paymentAmount, Date paymentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(paymentDate);
        String paymentString = String.format(Locale.getDefault(), "$%.2f was paid on %s", paymentAmount, formattedDate);
        paymentItemTextView.setText(paymentString);
    }

}
