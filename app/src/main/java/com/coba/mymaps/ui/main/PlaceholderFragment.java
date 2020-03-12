package com.coba.mymaps.ui.main;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;


import com.coba.mymaps.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;
    SupportMapFragment mapFragment;
    SearchView searchView;
    private GoogleMap nMap;
    private FragmentActivity myContext;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        if (getArguments().getInt(ARG_SECTION_NUMBER) == 1){
            View rootView = inflater.inflate(R.layout.activity_maps, container, false);

            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                    .findFragmentById(R.id.map);
            searchView = rootView.findViewById(R.id.sv_location);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    String location = searchView.getQuery().toString();
                    List<Address> addressList = null;
                    if(location!= null || !location.equals("")){
                        Geocoder geocoder = new Geocoder(getActivity());
                        try {
                            addressList = geocoder.getFromLocationName(location,1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        nMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        nMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            mapFragment.getMapAsync(this);
            return rootView;
        }
        else if(getArguments().getInt(ARG_SECTION_NUMBER) == 2){
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
        else {
            View rootView = inflater.inflate(R.layout.activity_maps, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            return rootView;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        nMap = googleMap;

        LatLng malang = new LatLng(-7.9666204, 112.63263210000002);
        LatLng pasuruan = new LatLng(-7.6453, 112.9075);
        LatLng batu = new LatLng(-7.867100, 112.523903);

        nMap.addMarker(new MarkerOptions().position(malang).title("Kota Malang"));
        nMap.addMarker(new MarkerOptions().position(pasuruan).title("Kota Pasuruan"));
        nMap.addMarker(new MarkerOptions().position(batu).title("Kota Batu"));

        nMap.setMyLocationEnabled(true);
//        nMap.animateCamera(CameraUpdateFactory.zoomTo(9.0f));

    }
        @Override
        public void onAttach(Context context) {
            myContext = (FragmentActivity) context;
            super.onAttach(context);
        }
}