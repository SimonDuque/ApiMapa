package com.example.apimapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {

    private EditText txtLongitud, txtLatitud;
    private TextView txtadress;
    private GoogleMap gm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        txtLongitud = findViewById(R.id.txtLongitud);
        txtLatitud = findViewById(R.id.txtLatitud);
        txtadress = findViewById(R.id.txtdireccion);


        SupportMapFragment mp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mp.getMapAsync(this::onMapReady);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap maps){
        gm = maps;
        this.gm.setOnMapClickListener(this::onMapClick);
        this.gm.setOnMapClickListener(this::onMapLongClick);

        LatLng granada = new LatLng(37.1544735,-3.6115924);
        gm.addMarker(new MarkerOptions().position(granada).title("MEDAC"));
        gm.moveCamera(CameraUpdateFactory.newLatLng(granada));

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccion = null;
        try {
            direccion = geocoder.getFromLocation(37.1544735,-3.6115924, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = direccion.get(0).getAddressLine(0);
        txtadress.setText(String.valueOf(address));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng){
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));


        LatLng granada = new LatLng(latLng.latitude, latLng.longitude);
        gm.addMarker(new MarkerOptions().position(granada).title(""));
        gm.moveCamera(CameraUpdateFactory.newLatLng(granada));

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccion = null;
        try {
            direccion = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = direccion.get(0).getAddressLine(0);
        txtadress.setText(String.valueOf(address));
    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng){
        txtLatitud.setText(String.valueOf(latLng.latitude));
        txtLongitud.setText(String.valueOf(latLng.longitude));

        LatLng granada = new LatLng(latLng.latitude, latLng.longitude);
        gm.addMarker(new MarkerOptions().position(granada).title(""));
        gm.moveCamera(CameraUpdateFactory.newLatLng(granada));

       Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> direccion = null;
        try {
            direccion = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = direccion.get(0).getAddressLine(0);
        txtadress.setText(String.valueOf(address));
    }
}