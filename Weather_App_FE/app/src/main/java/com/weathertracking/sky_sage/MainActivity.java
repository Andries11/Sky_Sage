package com.weathertracking.sky_sage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.weathertracking.sky_sage.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 123;
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    com.weathertracking.sky_sage.databinding.ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }

    private boolean checkLocationPermission() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermission() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private void getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // location object to get latitude and longitude
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();

                        Locations locationsFragment = Locations.newInstance(latitude, longitude);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, locationsFragment, "locations_fragment_tag")
                                .commit();
                    }
                    else {
                        Log.e("LocationError", "Last known location is null");
                    }
                }).addOnFailureListener(this, e -> {
                    Log.e("LocationError", "Error getting last known location", e);
                });;
        }
    private boolean isPermissionRequested = false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && !isPermissionRequested) {
        isPermissionRequested = true;
            if (grantResults.length > 0) {
                boolean fineLocationGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean coarseLocationGranted = grantResults.length > 1 && grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (fineLocationGranted && coarseLocationGranted) {
                    // Permission granted, proceed to get location
                    Log.d("LocationPermission", "Permission granted");
                    getLastKnownLocation();
                } else {
                    // Permission denied
                    Log.d("LocationPermission", "Permission denied");
                }
            } else {
                // Permission request canceled
                Log.d("LocationPermission", "Permission request canceled");
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void openAppSettings(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_setting) {
            // Open the app's settings screen to allow the user to grant the location permission
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void exitApp(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_exit) {
            finish();
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        Fragment locationsFrag = fragmentManager.findFragmentByTag("locations_fragment_tag");

        if (locationsFrag == null) {
            // If the fragment doesn't exist, create and add it
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Locations(), "locations_fragment_tag")
                    .commit();
        }
        //check if location permissions have been granted
        if (!checkLocationPermission()) {
            requestLocationPermission();
        } else {
            getLastKnownLocation();
        }
    }
}