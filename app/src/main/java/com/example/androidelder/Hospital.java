package com.example.androidelder;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
public class Hospital extends AppCompatActivity implements OnMapReadyCallback {
    Spinner spinner;
    RelativeLayout relativeLayout;
    RelativeLayout relativeLayout2;
    LinearLayout linearlayout;
    GoogleMap gMap;
    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.hospital_map);
        mapFragment.getMapAsync(this);

//        mapFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.path_map);
//        mapFrag.getMapAsync(this);

        relativeLayout = (RelativeLayout)findViewById(R.id.hos_all_layout);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.hos_list_layout);
        linearlayout = (LinearLayout)findViewById(R.id.hos_map_layout);

        spinner = (Spinner)findViewById(R.id.spin_hos);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.search_list, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        relativeLayout.setVisibility(View.VISIBLE);
        relativeLayout2.setVisibility(View.INVISIBLE);
        linearlayout.setVisibility(View.INVISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this,"선택된 아이템 : "+ s.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                if(i==0){
                    Toast.makeText(Hospital.this,"선택된 아이템 : "+ spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.VISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    linearlayout.setVisibility(View.INVISIBLE);
                } else if(i==1){
                    Toast.makeText(Hospital.this,"선택된 아이템 : "+ spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    relativeLayout2.setVisibility(View.VISIBLE);
                    linearlayout.setVisibility(View.INVISIBLE);
                } else if(i==2){
                    Toast.makeText(Hospital.this,"선택된 아이템 : "+ spinner.getItemAtPosition(i),Toast.LENGTH_SHORT).show();
                    relativeLayout.setVisibility(View.INVISIBLE);
                    relativeLayout2.setVisibility(View.INVISIBLE);
                    linearlayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                relativeLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onMapReady(GoogleMap map) {

//        gMap = map;
//        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256, 126.897240), 15));

        gMap = map;

        LatLng SEOUL = new LatLng(35.945273, 126.682142);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        gMap.addMarker(markerOptions);

        gMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        gMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }
}