package com.example.quickcash.activities.employer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.BuildConfig;
import com.example.quickcash.R;

import com.example.quickcash.objects.Job;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigDecimal;

public class EmployerPaymentActivity extends AppCompatActivity {
    private static final String TAG = EmployerPaymentActivity.class.getName();
    private ActivityResultLauncher<Intent> activityResultLauncher;
   private Button makePayment;
    private TextView paymentStatus;

    private TextView details;
    String jobSalary = "0";

    private PayPalConfiguration payPalConfig;


    protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_employee_payment);
        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("jobSalary")) {
            jobSalary = String.valueOf(intent.getStringExtra("jobSalary"));
        }
            init();
            configPayPal();
            initActivityLauncher();
            setListeners();



    }



    private void init() {

        makePayment = findViewById(R.id.makePayment);
        paymentStatus = findViewById(R.id.paymentStatus);
        details=findViewById(R.id.details);
        details.setText(jobSalary);

    }

    private void configPayPal() {
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
                .clientId(BuildConfig.PAYPAL_CLIENT_ID);
    }

    private void initActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        final PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);
                                JSONObject payObj = new JSONObject(paymentDetails);
                                String payID = payObj.getJSONObject("response").getString("id");
                                String state = payObj.getJSONObject("response").getString("state");
                              paymentStatus.setText(String.format("Payment %s%n with payment id is %s", state, payID));
                            } catch (JSONException e) {
                                Log.e(TAG, "An extremely unlikely failure occurred", e);
                            }
                        }
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Log.d(TAG, "Launcher Result Invalid");
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Log.d(TAG, "Launcher Result Cancelled");
                    }
                });
    }

    private void setListeners() {
        makePayment.setOnClickListener(v -> processPayment());
    }

    private void processPayment() {
        final String paymentAmount = jobSalary;
        final PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(
                paymentAmount), "CAD", "Purchase Goods", PayPalPayment.PAYMENT_INTENT_SALE);

        final Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        activityResultLauncher.launch(intent);


    }
}
