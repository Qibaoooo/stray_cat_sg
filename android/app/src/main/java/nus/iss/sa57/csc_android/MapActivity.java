package nus.iss.sa57.csc_android;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nus.iss.sa57.csc_android.databinding.ActivityMapBinding;
import nus.iss.sa57.csc_android.model.CatSighting;
import nus.iss.sa57.csc_android.utils.MarkerInfoWindowAdapter;

public class MapActivity extends FragmentActivity
        implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ActivityMapBinding binding;
    private SharedPreferences listPref;
    private List<CatSighting> csList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listPref = getSharedPreferences("list_info", MODE_PRIVATE);
        if (listPref.getBoolean("isFetched", false)) {
            String responseData = listPref.getString("listData", null);
            try {
                Type listType = new TypeToken<List<CatSighting>>() {
                }.getType();
                Gson gson = new Gson();
                csList = gson.fromJson(responseData, listType);
            } catch (JsonSyntaxException e) {
                Log.e("MainActivity", "Error parsing JSON: " + e.getMessage());
            }
        } else {
            finish();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng singapore = new LatLng(1.3, 103.85);
        float zoomLevel = 12.0f;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(singapore, zoomLevel);

        mMap.moveCamera(cameraUpdate);
        setupMarkers();
    }

    private void setupMarkers() {
        mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(this, csList));
        for (CatSighting cs : csList) {
            LatLng csLatLng = new LatLng(cs.getLocationLat(), cs.getLocationLong());
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .position(csLatLng)
                            .title(cs.getSightingName()));
            marker.setTag(cs.getId());
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });
        mMap.setOnInfoWindowClickListener(this);
    }



    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        Intent intent = new Intent(this, DetailsActivity.class);
        Integer catId = (Integer) marker.getTag();
        intent.putExtra("catId", catId.intValue());
        startActivity(intent);
    }
}