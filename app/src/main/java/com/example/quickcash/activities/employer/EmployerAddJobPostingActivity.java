package com.example.quickcash.activities.employer;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcash.R;
import com.example.quickcash.enums.Urgency;
import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.objects.Job;
import com.example.quickcash.objects.JobBuilder;
import com.example.quickcash.utilities.EmployerManager;
import com.example.quickcash.utilities.JobManager;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Activity for allowing an employer to create a job to post.
 */
public class EmployerAddJobPostingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener, EmployerLoadedCallback {

    private Date selectedJobDate = new Date();
    private Coordinates userCoordinates = new Coordinates(0.0, 0.0);

    /**
     * Called when the activity is created.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_add_job_posting);

        fetchUserLocation();
        setupBackButton();
        setupDatePickerButton();
        setupJobDurationSpinner();
        setupJobUrgencySpinner();
        setupSubmitJobButton();
    }

    private void fetchUserLocation() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        EmployerManager employerManager = new EmployerManager();
        employerManager.getEmployerByID(userID, this);
    }

    private void setupBackButton() {
        Button backButton = findViewById(R.id.employerAddJobBackButton);
        backButton.setOnClickListener(v -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void setupDatePickerButton() {
        Button datePickerButton = (Button) findViewById(R.id.datepickerbtn);
        datePickerButton.setOnClickListener(v -> showDatePicker());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Callback method called when a date is selected by the user.
     * @param view the picker associated with the dialog
     * @param year the selected year
     * @param month the selected month (0-11 for compatibility with
     *              {@link Calendar#MONTH})
     * @param dayOfMonth the selected day of the month (1-31, depending on
     *                   month)
     */
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        this.selectedJobDate = calendar.getTime();

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(selectedJobDate);

        TextView textView = (TextView) findViewById(R.id.datePicker);
        textView.setText(currentDateString);
    }

    private void setupJobDurationSpinner() {
        Spinner jobDurationSpinner = findViewById(R.id.durationSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.duration_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobDurationSpinner.setAdapter(adapter);
        jobDurationSpinner.setPrompt("Enter Duration");
        jobDurationSpinner.setOnItemSelectedListener(this);
    }

    private void setupJobUrgencySpinner() {
        Spinner jobUrgencySpinner = findViewById(R.id.urgencySpinner);
        ArrayAdapter<CharSequence> urgencyAdapter = ArrayAdapter.createFromResource(this, R.array.Urgency, android.R.layout.simple_spinner_item);
        urgencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobUrgencySpinner.setAdapter(urgencyAdapter);
        jobUrgencySpinner.setPrompt("Urgency");
        jobUrgencySpinner.setOnItemSelectedListener(this);
    }

    /**
     * Callback method when item is selected by user.
     * @param parent The AdapterView where the selection happened
     * @param view The view within the AdapterView that was clicked
     * @param position The position of the view in the adapter
     * @param id The row id of the item that is selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("EmployerAddJobPostingActivity: calendar", "item selected");
    }

    /**
     * Callback method when nothing is selected by user.
     * @param parent The AdapterView that now contains no selected item.
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("EmployerAddJobPostingActivity: calendar", "no item selected");
    }

    private void setupSubmitJobButton() {
        Button jobSubmitButton = findViewById(R.id.submitJobBtn);
        jobSubmitButton.setOnClickListener(v -> submitJob());
    }

    private void submitJob() {
        boolean formIsValid = validateFields();
        if (!formIsValid) {
            return;
        }

        String jobTitle = ((EditText) findViewById(R.id.writeJob)).getText().toString().trim();
        Date jobStartDate = this.selectedJobDate;
        Date jobEndDate = calculateEndDate();
        Urgency jobUrgency = getJobUrgency();
        double jobSalary = Double.parseDouble(((EditText) findViewById(R.id.salaryNumber)).getText().toString().trim());
        Coordinates jobCoordinates = getLocationInput();
        String employerID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        JobBuilder jobBuilder = new JobBuilder()
                .setTitle(jobTitle)
                .setStartDate(jobStartDate)
                .setEndDate(jobEndDate)
                .setUrgency(jobUrgency)
                .setSalary(jobSalary)
                .setCoordinates(jobCoordinates)
                .setEmployerID(employerID);

        Job job = jobBuilder.build();
        JobManager jobManager = new JobManager();
        String jobID = jobManager.postJob(job);

        EmployerManager employerManager = new EmployerManager();
        employerManager.addPostedJobID(employerID, jobID);

        navigateToActivity(EmployerLandingActivity.class);
    }

    private boolean validateFields() {
        boolean invalidTitle = ((EditText) findViewById(R.id.writeJob)).getText().toString().trim().isEmpty();
        if (invalidTitle) {
            Toast.makeText(this, "Enter job title", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean isNullDate = (this.selectedJobDate == null);
        if (isNullDate) {
            Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
            return false;
        }

        Date currentDate = Calendar.getInstance().getTime();
        boolean invalidDate = this.selectedJobDate.before(currentDate);
        if (invalidDate) {
            Toast.makeText(this, "Invalid date", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean invalidDuration = !validateInputIsNonNegativeInt(findViewById(R.id.durationNumber));
        if (invalidDuration) {
            Toast.makeText(this, "Invalid duration", Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean invalidSalary = !validateInputIsNonNegativeDouble(findViewById(R.id.salaryNumber));
        if (invalidSalary) {
            Toast.makeText(this, "Invalid salary", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validateInputIsNonNegativeDouble(EditText editText) {
        boolean validNonNegativeDouble = true;
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            return false;
        }

        try {
            double value = Double.parseDouble(input);
            if (value < 0) {
                validNonNegativeDouble = false;
            }
        } catch (NumberFormatException e) {
            validNonNegativeDouble = false;
        }
        return validNonNegativeDouble;
    }

    private boolean validateInputIsNonNegativeInt(EditText editText) {
        boolean validNonNegativeInt = true;
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            return false;
        }

        try {
            int value = Integer.parseInt(input);
            if (value < 0) {
                validNonNegativeInt = false;
            }
        } catch (NumberFormatException e) {
            validNonNegativeInt = false;
        }
        return validNonNegativeInt;
    }

    private Date calculateEndDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.selectedJobDate);

        String input = ((EditText) findViewById(R.id.durationNumber)).getText().toString().trim();
        int value = Integer.parseInt(input);

        String selectedUnit = ((Spinner) findViewById(R.id.durationSpinner)).getSelectedItem().toString();
        if (selectedUnit.equals("Hours")) {
            calendar.add(Calendar.HOUR_OF_DAY, value);
        } else if (selectedUnit.equals("Days")) {
            calendar.add(Calendar.DAY_OF_MONTH, value);
        } else if (selectedUnit.equals("Weeks")) {
            calendar.add(Calendar.WEEK_OF_YEAR, value);
        } else {
            return null;
        }

        return calendar.getTime();
    }

    private Urgency getJobUrgency() {
        String selectedUrgency = ((Spinner) findViewById(R.id.urgencySpinner)).getSelectedItem().toString();

        if (selectedUrgency.equals("Low")) {
            return Urgency.LOW;
        } else if (selectedUrgency.equals("Medium")) {
            return Urgency.MEDIUM;
        } else {
            return Urgency.HIGH;
        }
    }

    private Coordinates getLocationInput() {
        Coordinates coordinates = this.userCoordinates;
        String userInput = ((EditText) findViewById(R.id.jobLocationInput)).getText().toString().trim();
        boolean hasinput = !userInput.isEmpty();

        if (hasinput) {
            Address address = getCoordinatesFromLocation(userInput);
            if (address != null) {
                coordinates = new Coordinates(address.getLatitude(), address.getLongitude());
            }
        }
        return coordinates;
    }

    private Address getCoordinatesFromLocation(String locationName) {
        Geocoder geocoder = new Geocoder(EmployerAddJobPostingActivity.this);
        Address address = null;
        try {
            address = geocoder.getFromLocationName(locationName, 1).get(0);
        } catch (IOException e) {
            Log.e("EmployerAddJobPosting", "Exception when retrievingAddress: " + e.getMessage(), e);
        }
        return address;
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(EmployerAddJobPostingActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "employerAddJob");
        startActivity(intent);
        finish();
    }

    /**
     * Callback method called when employer is loaded.
     * @param employer  The employer that has been loaded.
     */
    @Override
    public void onEmployerLoaded(Employer employer) {
        this.userCoordinates = employer.getCoordinates();
    }

    /**
     * Callback method called if employer is missing from the database.
     */
    @Override
    public void onEmployerNotFound() {
        Log.e("EmployerAddJobPostingActivity", "Database error: Employer not found");
    }

    /**
     * Callback method called if database returns an error.
     * @param errorMessage  The error message describing the issue.
     */
    @Override
    public void onDataLoadError(String errorMessage) {
        Log.e("EmployerAddJobPostingActivity", errorMessage);
    }

}