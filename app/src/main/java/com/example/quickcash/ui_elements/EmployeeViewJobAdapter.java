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
import com.example.quickcash.objects.Job;
import com.example.quickcash.utilities.JobApplicationManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of available jobs for an employee.
 */
public class EmployeeViewJobAdapter extends RecyclerView.Adapter<EmployeeViewJobViewHolder> {
    private List<Job> jobs;
    private Geocoder geocoder;

    /**
     * Constructor for the adapter.
     * @param jobs  The list of jobs to be displayed.
     * @param context   The context of the calling code.
     */
    public EmployeeViewJobAdapter(List<Job> jobs, Context context) {
        this.jobs = jobs;
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

        String details = job.toFormattedString(this.geocoder);
        holder.detailsTextView.setText(details);

        if (job.isOpenToApplications()) {
            holder.applyButton.setVisibility(View.VISIBLE);
            holder.applyButton.setOnClickListener(v -> {
                String jobID = job.getJobID();
                submitApplication(jobID);
                Toast.makeText(v.getContext(), "Application successfully submitted", Toast.LENGTH_SHORT).show();
                holder.applyButton.setVisibility(View.GONE);
            });
        }
    }

    private void submitApplication(String jobID) {
        String employeeID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        JobApplicationManager jobApplicationManager = new JobApplicationManager();
        jobApplicationManager.submitJobApplication(employeeID, jobID);
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
