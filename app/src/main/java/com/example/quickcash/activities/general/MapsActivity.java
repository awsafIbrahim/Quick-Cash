package com.example.quickcash.activities.general;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcash.R;
import com.example.quickcash.activities.employer.EmployerLandingActivity;
import com.example.quickcash.databinding.ActivityMapsBinding;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.utilities.UserCoordinatesHandler;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private static final float DEFAULT_ZOOM = 15f;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    private static final int ERROR_DIALOG_REQUEST = 9001;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (isServicesOK()) {
            setupOkButton();
            getLocationPermission();
        }
    }

    private void setupOkButton() {
        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(view -> navigateToActivity(EmployerLandingActivity.class));
    }

    private void getLocationPermission() {
        String[] permissions = {FINE_LOCATION, COURSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    @Deprecated
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMap();
            } else {
                navigateToActivity(ManualSearchActivity.class);
            }
        }
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getDeviceLocation();
    }

    public void getDeviceLocation(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY).setNumUpdates(1);
            mFusedLocationProviderClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }


    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location currentLocation = locationResult.getLastLocation();
            updateUserLocation(currentLocation.getLatitude(), currentLocation.getLongitude());
            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM, "Current Location");
        }
    };

    private void updateUserLocation(double latitude, double longitude) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Coordinates currentCoordinates = new Coordinates(latitude, longitude);
        UserCoordinatesHandler userCoordinatesHandler = new UserCoordinatesHandler();
        userCoordinatesHandler.updateUserLocation(userID, currentCoordinates);
    }

    public void moveCamera(LatLng latlng, float zoom, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, zoom));
        MarkerOptions options = new MarkerOptions().position(latlng).title(title);
        mMap.addMarker(options);
    }

    public boolean isServicesOK() {
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapsActivity.this);
        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, available, ERROR_DIALOG_REQUEST).show();
        } else {
            Toast.makeText(this, "You cannot make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(MapsActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "MapsActivity");
        startActivity(intent);
        finish();
    }

}
