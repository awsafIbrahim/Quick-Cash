package com.example.quickcash.ui_elements;

import android.content.Context;
import android.location.Geocoder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.interfaces.OnViewApplicantsClickListener;
import com.example.quickcash.objects.Job;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of posted jobs for an employer.
 */
public class EmployerViewJobAdapter extends RecyclerView.Adapter<EmployerViewJobViewHolder> {
    private List<Job> jobs;
    private OnViewApplicantsClickListener callback;
    private Geocoder geocoder;

    /**
     * Constructor for the adapter.
     * @param jobs      The list of jobs to display.
     * @param callback  A callback used to handle clicking the view applicants button.
     * @param context   The context of the calling code.
     */
    public EmployerViewJobAdapter(List<Job> jobs, OnViewApplicantsClickListener callback, Context context) {
        this.jobs = jobs;
        this.callback = callback;
        this.geocoder = new Geocoder(context);
    }

    /**
     * Called when RecyclerView needs a new EmployerViewJobViewHolder.
     * @param parent    The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return          A new EmployerViewJobViewHolder.
     */
    @NonNull
    @Override
    public EmployerViewJobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View jobView = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_layout, parent, false);
        return new EmployerViewJobViewHolder(jobView);
    }

    /**
     * Called by RecyclerView to display data at specified position.
     * @param holder   The EmployerViewJobViewHolder that will be updated.
     * @param position The position of the item within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployerViewJobViewHolder holder, int position) {
        Job job = jobs.get(position);
        holder.titleTextView.setText(job.getTitle());

        String details = job.toFormattedString(this.geocoder);
        holder.detailsTextView.setText(details);

        holder.viewApplicantsButton.setOnClickListener(v -> {
            String jobID = job.getJobID();
            boolean isJobOpen = job.isOpenToApplications();
            callback.onViewApplicantsClicked(jobID, isJobOpen);
        });
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
