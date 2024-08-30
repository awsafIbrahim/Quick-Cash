package com.example.quickcash.ui_elements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcash.R;
import com.example.quickcash.objects.Employee;

import java.util.List;

/**
 * RecyclerView Adapter for displaying a list of preferred employees for an employer.
 */
public class EmployerViewPreferredEmployeesAdapter extends RecyclerView.Adapter<EmployerViewPreferredEmployeesViewHolder> {
    private List<Employee> preferredEmployees;

    /**
     * Constructor for the adapter.
     * @param preferredEmployees    Th list of preferred employees to display.
     */
    public EmployerViewPreferredEmployeesAdapter(List<Employee> preferredEmployees) {
        this.preferredEmployees = preferredEmployees;
    }

    /**
     * Called when RecyclerView needs a new EmployerViewPreferredEmployeesViewHolder.
     * @param parent    The ViewGroup into which the new View will be added after it is bound to
     *                  an adapter position.
     * @param viewType  The view type of the new View.
     * @return          A new EmployerViewPreferredEmployeesViewHolder.
     */
    @NonNull
    @Override
    public EmployerViewPreferredEmployeesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View userView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_layout, parent, false);
        return new EmployerViewPreferredEmployeesViewHolder(userView);
    }

    /**
     * Called by RecyclerView to display data at specified position.
     * @param holder   The EmployerViewPreferredEmployeesViewHolder that will be updated.
     * @param position The position of the item within the adapter.
     */
    @Override
    public void onBindViewHolder(@NonNull EmployerViewPreferredEmployeesViewHolder holder, int position) {
        Employee preferredEmployee = preferredEmployees.get(position);
        holder.usernameTextView.setText(preferredEmployee.getName());
        holder.setupRatingsSubmit(preferredEmployee);
    }

    /**
     * Returns the total number of items held by the adapter.
     * @return  The total number of items held by the adapter.
     */
    @Override
    public int getItemCount() {
        return preferredEmployees.size();
    }

}
