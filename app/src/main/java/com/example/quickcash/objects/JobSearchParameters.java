package com.example.quickcash.objects;

import java.io.Serializable;

public class JobSearchParameters implements Serializable {
    private String jobTitle;
    private double distanceRange;

    public JobSearchParameters(String jobTitle, double distanceRange){
        this.jobTitle=jobTitle;
        this.distanceRange=distanceRange;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setDistanceRange(double distanceRange) {
        this.distanceRange = distanceRange;
    }

    public double getDistanceRange() {
        return distanceRange;
    }
}
