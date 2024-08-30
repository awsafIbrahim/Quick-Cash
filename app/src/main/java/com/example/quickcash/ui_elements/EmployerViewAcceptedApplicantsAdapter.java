package com.example.quickcash.ui_elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;
import com.example.quickcash.objects.Employer;
import com.example.quickcash.objects.Job;
import com.example.quickcash.utilities.EmployerManager;

import java.util.List;
import java.util.stream.Collectors;
import android.content.Context;

/**
 * RecyclerView Adapter for displaying a list of accepted job applicants for an employer.
 */
public class EmployerViewAcceptedApplicantsAdapter extends RecyclerView.Adapter<EmployerViewApplicantsViewHolder> {
    private List<Employee> acceptedApplicants;
    private Employer employer;
    private List<Job> jobsWithAcceptedApplicants;

    /**
     * Constructor for the adapter.
     * @param acceptedApplicants    The list of employees to display.
     * @param employer              The employer associated with the job.
     */
    public EmployerViewAcceptedApplicantsAdapter(List<Employee> acceptedApplicants, Employer employer, List<Job> jobsWithAcceptedApplicants) {
        this.acceptedApplicants = acceptedApplicants;
        this.employer = employer;
        this.jobsWithAcceptedApplicants=jobsWithAcceptedApplicants;
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
        Employee acceptedApplicant = acceptedApplicants.get(position);
        Job acceptedEmployeeJob = jobsWithAcceptedApplicants.stream()
               .filter(j -> j.getAcceptedEmployeeID().equals(acceptedApplicant.getID()))
               .collect(Collectors.toList()).get(0);
        holder.usernameTextView.setText(acceptedApplicant.getName());
        Context context = holder.itemView.getContext();
        holder.payEmployee(acceptedEmployeeJob, context);
        holder.setupRatingsSubmit(acceptedApplicant);

        boolean isEmployeePreferred = employer.getPreferredEmployeeIDs().contains(acceptedApplicant.getID());
        if(!isEmployeePreferred) {
            holder.preferEmployeeButton.setVisibility(View.VISIBLE);
            holder.preferEmployeeButton.setOnClickListener(v -> {
                employer.addPreferredEmployeeID(acceptedApplicant.getID());
                EmployerManager employerManager = new EmployerManager();
                employerManager.updateEmployerInDatabase(employer);
                Toast.makeText(v.getContext(), "Employee Favourited", Toast.LENGTH_SHORT).show();
                holder.preferEmployeeButton.setVisibility(View.GONE);
            });
        }
    }

    /**
     * Returns the total number of items held by the adapter.
     * @return  The total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return acceptedApplicants.size();
    }

}
