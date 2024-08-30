package com.example.quickcash.ui_elements;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Job;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of applied jobs for an employee.
 */
public class EmployeeViewAppliedJobsAdapter extends RecyclerView.Adapter<EmployeeViewAppliedJobsViewHolder> {
    private List<Job> appliedJobs;
    private Geocoder geocoder;

    /**
     * Constructor for the adapter.
     * @param appliedJobs   The list of applied jobs to be displayed.
     * @param context   The context of the calling code.
     */
    public EmployeeViewAppliedJobsAdapter(List<Job> appliedJobs, Context context) {
        this.appliedJobs = appliedJobs;
        this.geocoder = new Geocoder(context);
    }

    /**
     * Called when RecyclerView needs a new EmployeeViewAppliedJobsViewHolder.
     * @param parent    The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return          A new EmployeeViewAppliedJobsViewHolder.
     */
    @NonNull
    @Override
    public EmployeeViewAppliedJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View jobView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        return new EmployeeViewAppliedJobsViewHolder(jobView);
    }

    /**
     * Called by RecyclerView to display data at specified position.
     * @param holder   The EmployeeViewAppliedJobsViewHolder that will be updated.
     * @param position The position of the item within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployeeViewAppliedJobsViewHolder holder, int position) {
        Job appliedJob = appliedJobs.get(position);
        holder.titleTextView.setText(appliedJob.getTitle());

        String details = appliedJob.toFormattedString(this.geocoder);
        holder.detailsTextView.setText(details);
    }

    /**
     * Returns the total number of items held by the adapter.
     * @return  The total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return this.appliedJobs.size();
    }

}
