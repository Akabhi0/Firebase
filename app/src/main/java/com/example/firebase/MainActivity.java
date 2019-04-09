package com.example.firebase;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private Toolbar toolbar;
    private Button button;
    private double[] latlng = new double[3];
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private LocationManager locationManager;
    private RecyclerView recyclerView;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        button = findViewById(R.id.updateLocation);
        recyclerView = findViewById(R.id.recycler_view);

        /**
         * Checking for location is on or not in the deivce
         */
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,
                        0, MainActivity.this);

                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!gps_enabled && !network_enabled) {
                    Toast.makeText(MainActivity.this, "Please Open location", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * This is for showing the dialog for location enable
         */
        if (checkingPermission() == -1) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 200);
        }
    }

    public int checkingPermission() {
        String permission = android.Manifest.permission.ACCESS_FINE_LOCATION;
        int res = MainActivity.this.checkCallingOrSelfPermission(permission);
        return res;
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationCalculator(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void LocationCalculator(double latitude, double longitude) {
        ArrayList<DistaceModel> models = new ArrayList<>();
        String[] storeLat;
        String[] storeLong;
        String[] storeName;

        int[] drawable = new int[]{R.drawable.one,
                R.drawable.two,
                R.drawable.three,
                R.drawable.four,
                R.drawable.five,
                R.drawable.seven,
                R.drawable.six,
                R.drawable.backgroundimage};

        storeLat = getResources().getStringArray(R.array.latitude);
        storeLong = getResources().getStringArray(R.array.longitude);
        storeName = getResources().getStringArray(R.array.filename);

        for (int i = 0; i < storeLat.length; i++) {
            DistaceModel distaceModel = new DistaceModel();
            distaceModel.setLatitudeCurrent(latitude);
            distaceModel.setLongitudeCurrent(longitude);

            distaceModel.setId(i);
            distaceModel.setName(storeName[i]);
            distaceModel.setDrawable(drawable[i]);
            distaceModel.setLatitudeFixedOne(Double.parseDouble(storeLat[i]));
            distaceModel.setLongitudeFixedOne(Double.parseDouble(storeLong[i]));

            models.add(distaceModel);
            adapter = new Adapter(this, models);
            recyclerView.setAdapter(adapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter.notifyDataSetChanged();

        }
    }
}