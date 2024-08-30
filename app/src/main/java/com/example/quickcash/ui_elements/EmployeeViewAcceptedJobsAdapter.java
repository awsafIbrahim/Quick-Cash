package com.example.quickcash.ui_elements;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Job;
import com.example.quickcash.utilities.EmployeeManager;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of accepted jobs for an employee.
 */
public class EmployeeViewAcceptedJobsAdapter extends RecyclerView.Adapter<EmployeeViewJobViewHolder> {
    private List<Job> jobs;
    private Employee employee;
    private Geocoder geocoder;

    /**
     * Constructor for the adapter.
     * @param jobs      The list of accepted jobs to be displayed.
     * @param employee  The employee associated with the adapter.
     * @param context   The context of the calling code.
     */
    public EmployeeViewAcceptedJobsAdapter(List<Job> jobs, Employee employee, Context context) {
        this.jobs = jobs;
        this.employee = employee;
        this.geocoder = new Geocoder(context);
    }

    /**
     * Called when RecyclerView needs a new EmployeeViewJobViewHolder.
     * @param parent    The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return          A new EmployeeViewJobViewHolder.
     */
    @NonNull
    @Override
    public EmployeeViewJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View jobView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        return new EmployeeViewJobViewHolder(jobView);
    }

    /**
     * Called by RecyclerView to display data at specified position.
     * @param holder   The EmployeeViewJobViewHolder that will be updated.
     * @param position The position of the item within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewJobViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.titleTextView.setText(job.getTitle());

        holder.setupRatingSubmitForEmployer(job);

        String details = job.toFormattedString(this.geocoder);
        holder.detailsTextView.setText(details);

        boolean isEmployerPreferred = employee.getPreferredEmployerIDs().contains(job.getEmployerID());
        if (!isEmployerPreferred) {
            holder.preferEmployerButton.setVisibility(View.VISIBLE);
            holder.preferEmployerButton.setOnClickListener(v -> {
                employee.addPreferredEmployerID(job.getEmployerID());
                EmployeeManager employeeManager = new EmployeeManager();
                employeeManager.updateEmployeeInDatabase(employee);
                Toast.makeText(v.getContext(), "Employer Favourited", Toast.LENGTH_SHORT).show();
                holder.preferEmployerButton.setVisibility(View.GONE);
            });
        }

    }

    /**
     * Returns the total number of items held by the adapter.
     * @return  The total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return jobs.size();
    }

}
