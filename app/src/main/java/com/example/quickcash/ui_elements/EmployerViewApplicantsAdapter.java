package com.example.quickcash.ui_elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.utilities.JobManager;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of job applicants for an employer.
 */
public class EmployerViewApplicantsAdapter extends RecyclerView.Adapter<EmployerViewApplicantsViewHolder> {
    private List<Employee> employees;
    private String jobID;
    private boolean isJobOpen;

    /**
     * Constructor for the adapter.
     * @param employees The list of employees to display.
     * @param jobID     The job ID of the associated job.
     * @param isJobOpen A boolean representing if the job is open to applicants.
     */
    public EmployerViewApplicantsAdapter(List<Employee> employees, String jobID, boolean isJobOpen) {
        this.employees = employees;
        this.jobID = jobID;
        this.isJobOpen = isJobOpen;
    }

    /**
     * Called when RecyclerView needs a new EmployerViewApplicantsViewHolder.
     * @param parent    The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return          A new EmployerViewApplicantsViewHolder.
     */
    @NonNull
    @Override
    public EmployerViewApplicantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new EmployerViewApplicantsViewHolder(userView);
    }

    /**
     * Called by RecyclerView to display data at specified position.
     * @param holder   The EmployerViewApplicantsViewHolder that will be updated.
     * @param position The position of the item within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployerViewApplicantsViewHolder holder, int position) {
        Employee employee = employees.get(position);
        holder.usernameTextView.setText(employee.getName());

        if (isJobOpen) {
            holder.acceptButton.setVisibility(View.VISIBLE);
            holder.acceptButton.setOnClickListener(v -> {
                JobManager jobManager = new JobManager();
                jobManager.acceptEmployee(jobID, employee.getID());
                jobManager.closeJob(jobID);
                Toast.makeText(v.getContext(), "Applicant Accepted", Toast.LENGTH_SHORT).show();
                holder.acceptButton.setVisibility(View.GONE);
            });
        }
    }

    /**
     * Returns the total number of items held by the adapter.
     * @return  The total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return employees.size();
    }

}
