package com.example.termproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private MapView mapView;
    private NaverMap myNaver;
    private Marker marker = new Marker();
    private String cityName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.map_view);

        if (mapView == null) {
            throw new NullPointerException("MapView is null. Check if mapView is correctly initialized.");
        } else {
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
        }

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, AddFolderActivity.class);
                intent.putExtra("cityName", cityName);
                startActivity(intent);
            }
        });
        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, AddFolderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapView != null) {
            mapView.onStart();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapView != null) {
            mapView.onStop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mapView != null) {
            mapView.onLowMemory();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }

    @UiThread
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        // ...
        myNaver = naverMap;
        myNaver.setOnMapClickListener((point, coord) ->
                //Toast.makeText(this, coord.latitude + ", " + coord.longitude,
                //        Toast.LENGTH_SHORT).show());
                marking(coord.latitude, coord.longitude));
    }

    private void marking(Double latitude, Double longitude) {
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setMap(myNaver);

        getAddress(latitude, longitude);
    }

    private void getAddress(Double latitude, Double longitude) {
        Geocoder geocoder = new Geocoder(getBaseContext(), Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 10);

            for (Address address : addresses) {
                if(address != null) {
                    String city = address.getLocality();
                    //String district = address.getSubLocality();
                    if(city != null && !city.equals("")) {
                        cityName = city;
                        //cityName = city + " " + district;
                        Toast.makeText(this, cityName,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
