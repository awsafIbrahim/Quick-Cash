package com.example.quickcash.utilities;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcash.interfaces.EmployerLoadedCallback;
import com.example.quickcash.objects.Employer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages operations related to employer data in the Firebase realtime database.
 */
public class EmployerManager {
    private static final String EMPLOYERS_REFERENCE = "employers";
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    /**
     * Constructor for EmployerManager.
     * Initializes the Firebase realtime database.
     */
    public EmployerManager() {
        this.database = FirebaseDatabase.getInstance();
        this.databaseReference = database.getReference();
    }

    /**
     * Adds an employer to the database.
     * @param employer  The Employer object to add.
     * @return          The ID of the employer object.
     */
    public String addEmployerToDatabase(Employer employer) {
        String employerID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference.child(EMPLOYERS_REFERENCE).child(employerID).setValue(employer);

        return employerID;
    }


    /**
     * Updates an employer in the database.
     * @param employer  The Employer object to update.
     * @return          The ID of the employer object.
     */
    public String updateEmployerInDatabase(Employer employer) {
        return addEmployerToDatabase(employer);
    }

    /**
     * Adds a job ID to an employer's representation in the database.
     * @param employerID    The ID of the employer.
     * @param jobID         The ID of the job to be added.
     */
    public void addPostedJobID(String employerID, String jobID) {
        DatabaseReference employerReference = getEmployerReference(employerID);

        employerReference.child("postedJobIDs").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> currentPostedJobs = new ArrayList<>();
                if (snapshot.exists()) {
                    for (DataSnapshot snapShotChild : snapshot.getChildren()) {
                        currentPostedJobs.add(snapShotChild.getValue(String.class));
                    }
                }
                currentPostedJobs.add(jobID);
                employerReference.child("postedJobIDs").setValue(currentPostedJobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("EmployerManager", error.getMessage());
            }
        });
    }

    /**
     * Retrieves the employer object from the database given their ID.
     * @param employerID    The ID of the employer to retrieve.
     * @param callback      The callback to handle the asynchronously returned result.
     */
    public void getEmployerByID(String employerID, final EmployerLoadedCallback callback) {
        DatabaseReference employerReference = getEmployerReference(employerID);

        employerReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Employer employer = snapshot.getValue(Employer.class);
                    callback.onEmployerLoaded(employer);
                } else {
                    callback.onEmployerNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDataLoadError(error.getMessage());
            }
        });
    }

    private DatabaseReference getEmployerReference(String employerID) {
        return this.databaseReference.child(EMPLOYERS_REFERENCE).child(employerID);
    }

}
