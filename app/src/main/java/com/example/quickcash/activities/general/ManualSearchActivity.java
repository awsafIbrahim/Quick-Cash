package com.example.quickcash.activities.general;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.quickcash.R;
import com.example.quickcash.activities.employer.EmployerLandingActivity;
import com.example.quickcash.objects.Coordinates;
import com.example.quickcash.utilities.UserCoordinatesHandler;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

/**
 * Activity for manual location search search, allowing users an alternative to location permissions.
 */
public class ManualSearchActivity extends AppCompatActivity {
    private SearchView searchBar;
    private GoogleMap mMap;
    public boolean userLocationUpdated;

    /**
     * Called when the activity is first created.
     * Prompts the user, initializes the UI elements, and sets up the map.
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_search);

        this.userLocationUpdated = false;
        Toast.makeText(this, "Enter a location", Toast.LENGTH_LONG).show();
        setupMap();
        setupOkButton();
        setupLocationSearchBar();
    }

    private void setupMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.clear();
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            mMap.getUiSettings().setZoomControlsEnabled(true);
        });
    }

    private void setupOkButton() {
        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(view -> {
            if (userLocationUpdated) {
                navigateToActivity(EmployerLandingActivity.class);
            } else {
                Toast.makeText(this, "Enter location", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupLocationSearchBar() {
        this.searchBar = findViewById(R.id.idSearchBar);
        this.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String text)
            {
                return true;
            }
        });
    }

    private void performSearch() {
        Address userAddress = retrieveAddressFromSearchBar();
        if (userAddress != null) {
            updateUserLocation(userAddress.getLatitude(), userAddress.getLongitude());
            userLocationUpdated = true;
            placeMarkerOnMap(userAddress);
            Toast.makeText(this, "Location updated!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bad Location", Toast.LENGTH_SHORT).show();
            Log.d("ManualSearch", "User entered bad manual search location");
        }
    }

    private Address retrieveAddressFromSearchBar() {
        Address userAddress = null;
        Geocoder geocoder = new Geocoder(ManualSearchActivity.this);
        try {
            String userLocationInputString = this.searchBar.getQuery().toString();
            userAddress = geocoder.getFromLocationName(userLocationInputString, 1).get(0);
        } catch (IOException e) {
            Log.e("ManualSearch", "Exception when retrievingAddressFromSearchBar: " + e.getMessage(), e);
        }
        return userAddress;
    }

    private void updateUserLocation(double latitude, double longitude) {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Coordinates currentCoordinates = new Coordinates(latitude, longitude);
        UserCoordinatesHandler userCoordinatesHandler = new UserCoordinatesHandler();
        userCoordinatesHandler.updateUserLocation(userID, currentCoordinates);
    }

    private void placeMarkerOnMap(Address address) {
        if (mMap != null) {
            LatLng location = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(location).title("Your Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f));
        }
    }

    private void navigateToActivity(Class<?> destinationClass) {
        Intent intent = new Intent(ManualSearchActivity.this, destinationClass);
        intent.putExtra("switchedFrom", "ManualSearch");
        startActivity(intent);
        finish();
    }

}
