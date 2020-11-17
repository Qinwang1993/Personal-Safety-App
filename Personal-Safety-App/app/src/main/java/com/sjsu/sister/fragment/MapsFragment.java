package com.sjsu.sister.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.sjsu.sister.R;
import com.sjsu.sister.activity.ShowListActivity;
import com.sjsu.sister.model.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class MapsFragment extends BaseFragment implements OnMapReadyCallback {
    private static final String TAG = MapsFragment.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;
    private SearchView searchView;
    private SupportMapFragment mapFragment;
    private View v;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    private Location mLastKnownLocation = null;
    private final LatLng mDefaultLocation = new LatLng(0, 0);
    private double currentLat = 0, currentLong = 0;
    private static final int DEFAULT_ZOOM = 12;
    private static final int LOCATION_REQUEST_CODE = 101;
    private ImageView nearCoffee;
    private ImageView nearHospital;
    private ImageView nearPolice;
    private int searchType = 0;

    public static MapsFragment newInstance() {
        MapsFragment fragment = new MapsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_maps, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = v.findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        nearCoffee = (ImageView) v.findViewById(R.id.coffee);
        nearHospital = (ImageView) v.findViewById(R.id.hospital);
        nearPolice = (ImageView) v.findViewById(R.id.police);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mMap.clear();
                String location = searchView.getQuery().toString();
                List<Address> addressList = null;
                if (!location.equals("")) {
                    searchType = 0;
                    new PlaceTask().execute(getUrl(location));
                }
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        nearCoffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = 1;
                new PlaceTask().execute(getUrl("cafe"));
            }
        });
        nearHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = 0;
                new PlaceTask().execute(getUrl("hospital"));
            }
        });
        nearPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchType = 0;
                new PlaceTask().execute(getUrl("police_station"));
            }
        });
        mapFragment.getMapAsync(this);
    }

    public String getUrl(String string) {
        String url = "";
        if (searchType == 0) {
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=" + currentLat + "," + currentLong +
                    "&radius=5000" +
                    "&name=" + string +
                    "&sensor=true" +
                    "&key=" + getResources().getString(R.string.google_maps_key);
        } else {
            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json" +
                    "?location=" + currentLat + "," + currentLong +
                    "&radius=5000" +
                    "&types=" + string +
                    "&sensor=true" +
                    "&key=" + getResources().getString(R.string.google_maps_key);
        }
        System.out.println(url);
        return url;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLocationPermission();
        updateLocationUI();
        getCurrentLocation();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                updateLocationUI();
                getCurrentLocation();
            } else {
                Snackbar.make(v, "Need permission to access location!", Snackbar.LENGTH_LONG)
                        .setAction("Allow", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                getLocationPermission();
                            }
                        }).show();
            }
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getCurrentLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = task.getResult();
                            if (mLastKnownLocation != null) {
                                HomeFragment homeFragment =
                                        (HomeFragment) getActivity()
                                                .getSupportFragmentManager()
                                                .findFragmentById(0);
                                HomeFragment.setmLocation(mLastKnownLocation);
                                currentLat = mLastKnownLocation.getLatitude();
                                currentLong = mLastKnownLocation.getLongitude();
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(currentLat, currentLong), DEFAULT_ZOOM));
                            }
                        } else {
                            mMap.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public class PlaceTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            new ParseTask().execute(s);
        }

        private String downloadUrl(String string) throws IOException {
            URL url = new URL(string);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder builder = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            String data = builder.toString();
            reader.close();
            return data;
        }
    }


    public class ParseTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {

            JsonParser jsonParser = new JsonParser();
            List<HashMap<String, String>> mapList = null;
            JSONObject object = null;
            try {
                object = new JSONObject(strings[0]);
                mapList = jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            mMap.clear();
            if (searchType == 0) {
                for (int i = 0; i < hashMaps.size(); i++) {
                    HashMap<String, String> hashMapList = hashMaps.get(i);
                    double lat = Double.parseDouble(hashMapList.get("lat"));
                    double lng = Double.parseDouble(hashMapList.get("lng"));
                    String name = hashMapList.get("name");
                    LatLng latLng = new LatLng(lat, lng);
                    MarkerOptions options = new MarkerOptions();
                    options.position(latLng);
                    options.title(name);
                    mMap.addMarker(options);
                }
            } else {
                showList(hashMaps);
            }
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    String markertitle = marker.getTitle();
                    Uri uri = Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + Uri.encode(markertitle));


                    Log.i("marker: %s", markertitle);

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return false;
                }
            });
        }

        public void showList(List<HashMap<String, String>> hashMaps) {
            Intent in = new Intent(getContext(), ShowListActivity.class);
            in.putExtra("data", (Serializable) hashMaps);
            startActivity(in);
        }
    }

}
